package kr.org.dagather.domain.profile.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import kr.org.dagather.common.exception.CustomException;
import kr.org.dagather.common.response.ErrorCode;
import kr.org.dagather.common.util.AuthUtil;
import kr.org.dagather.common.util.S3Util;
import kr.org.dagather.domain.friend.entity.Friend;
import kr.org.dagather.domain.friend.repository.FriendRepository;
import kr.org.dagather.domain.profile.dto.ProfileGetListResponseDto;
import kr.org.dagather.domain.profile.dto.ProfileGetResponseDto;
import kr.org.dagather.domain.profile.dto.ProfileImagePostRequestDto;
import kr.org.dagather.domain.profile.dto.ProfileImagePostResponseDto;
import kr.org.dagather.domain.profile.dto.ProfileInterestDto;
import kr.org.dagather.domain.profile.dto.ProfileMapper;
import kr.org.dagather.domain.profile.dto.ProfilePurposeDto;
import kr.org.dagather.domain.profile.dto.ProfileRecommendRequestDto;
import kr.org.dagather.domain.profile.dto.ProfileRecommendRequestItem;
import kr.org.dagather.domain.profile.dto.ProfileRecommendResponseDto;
import kr.org.dagather.domain.profile.dto.ProfileRequestDto;
import kr.org.dagather.domain.profile.dto.ProfileResponseDto;
import kr.org.dagather.domain.profile.entity.Location;
import kr.org.dagather.domain.profile.entity.Profile;
import kr.org.dagather.domain.profile.repository.LocationRepository;
import kr.org.dagather.domain.profile.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfileService {

	private final ProfileRepository profileRepository;
	private final FriendRepository friendRepository;
	private final LocationRepository locationRepository;
	private final ProfileMapper profileMapper;
	private final S3Util s3Util;


	@Transactional
	public ProfileImagePostResponseDto postProfileImage(ProfileImagePostRequestDto requestDto) {
		String imageUrl = s3Util.profileImageUpload(requestDto.getImage());
		return new ProfileImagePostResponseDto(imageUrl);
	}

	@Transactional
	public ProfileResponseDto saveProfile(ProfileRequestDto requestDto) {

		Profile profile = profileRepository.findProfileByMemberId(requestDto.getMemberId())
			.orElse(new Profile());

		profile.setMemberId(requestDto.getMemberId());
		profile.setResident(requestDto.getResident());
		profile.setName(requestDto.getName());
		profile.setGender(requestDto.isGender());
		profile.setBirth(LocalDate.parse(requestDto.getBirth()));
		profile.setNationality(requestDto.getNationality());
		profile.setRperiod(requestDto.getRperiod());
		profile.setIntroduction(requestDto.getIntroduction());
		profile.setImageUrl(requestDto.getImageUrl());
		profile.setPurpose(requestDto.getPurposes());
		profile.setInterest(requestDto.getInterests());
		profileRepository.save(profile);

		locationRepository.deleteAllByProfile(profile);
		locationRepository.save(Location.builder()
			.profile(profile)
			.longitude(requestDto.getLongitude())
			.latitude(requestDto.getLatitude())
			.build());

		return profileMapper.toResponseDto(profile);
	}

	@Transactional
	public ProfileGetResponseDto getProfile(String memberId) {

		// get current user info
		String currentMemberId = AuthUtil.getLoggedInId();
		if (currentMemberId == null || currentMemberId.isEmpty()) throw new CustomException(ErrorCode.NO_ID);

		Profile myProfile = profileRepository.findProfileByMemberId(currentMemberId).orElse(null);
		if (myProfile == null) throw new CustomException(ErrorCode.PROFILE_NOT_FOUND);

		// get target user info
		Profile profile = profileRepository.findProfileByMemberId(memberId).orElse(null);
		if (profile == null) throw new CustomException(ErrorCode.PROFILE_NOT_FOUND);

		// compare purposes and interests
		List<ProfilePurposeDto> purposes = new ArrayList<>();
		profile.getPurpose().forEach(p -> { purposes.add(new ProfilePurposeDto(p, myProfile.getPurpose().contains(p))); });

		List<ProfileInterestDto> interests = new ArrayList<>();
		profile.getInterest().forEach(i -> { interests.add(new ProfileInterestDto(i, myProfile.getInterest().contains(i))); });

		// add are we friend
		Friend friend = friendRepository.findFriendByMembers(currentMemberId, memberId);

		return profileMapper.toGetResponseDto(profile, purposes, interests, friend);
	}

	@Transactional
	public List<ProfileGetListResponseDto> getProfileList(String filter) {

		// get current user info
		String currentMemberId = AuthUtil.getLoggedInId();
		if (currentMemberId == null || currentMemberId.isEmpty()) throw new CustomException(ErrorCode.NO_ID);

		Profile myProfile = profileRepository.findProfileByMemberId(currentMemberId).orElse(null);
		if (myProfile == null) throw new CustomException(ErrorCode.PROFILE_NOT_FOUND);

		// filtering
		List<Profile> profileList;
		if (filter != null && filter.equals("nation")) {
			profileList = profileRepository.findAllByNationality(myProfile.getNationality());
		} else {
			profileList = profileRepository.findAll();
		}

		// remove friends from profile list
		friendRepository.findFriendsByMemberId(currentMemberId).forEach(friend -> {
			if (friend.getSender().equals(currentMemberId)) {
				profileList.remove(profileRepository.findByMemberId(friend.getReceiver()));
			} else {
				profileList.remove(profileRepository.findByMemberId(friend.getSender()));
			}
		});

		List<ProfileRecommendRequestItem> recommendRequestItems = new ArrayList<>();
		profileList.forEach(profile -> {
			recommendRequestItems.add(profileMapper.toRequestItem(profile, locationRepository.findByProfile(profile)));
		});

		// send request to flask
		ProfileRecommendResponseDto responseDto = sendRequestToFlask(ProfileRecommendRequestDto.builder()
			.id(myProfile.getId())
			.profiles(recommendRequestItems)
			.build());


		// make response
		List<ProfileGetListResponseDto> results = new ArrayList<>();

		responseDto.getData().getSortedIdList().forEach(id -> {
			Profile profile = profileRepository.findProfileById(id);

			// compare purposes and interests
			List<ProfilePurposeDto> purposes = new ArrayList<>();
			profile.getPurpose().forEach(p -> { purposes.add(new ProfilePurposeDto(p, myProfile.getPurpose().contains(p))); });

			List<ProfileInterestDto> interests = new ArrayList<>();
			profile.getInterest().forEach(i -> { interests.add(new ProfileInterestDto(i, myProfile.getInterest().contains(i))); });

			results.add(profileMapper.toGetResponseDto(profile, purposes, interests));
		});

		return results;
	}

	private ProfileRecommendResponseDto sendRequestToFlask(ProfileRecommendRequestDto requestDto) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-type", "application/json; charset=UTF-8");

			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<ProfileRecommendResponseDto> responseEntity = restTemplate.postForEntity(
				"http://localhost:8000/api/v1/profile/recommend",
				new HttpEntity<>(requestDto, headers),
				ProfileRecommendResponseDto.class
			);

			return responseEntity.getBody();
		} catch (HttpClientErrorException e) {
			throw new RuntimeException(e);
		}
	}
}
