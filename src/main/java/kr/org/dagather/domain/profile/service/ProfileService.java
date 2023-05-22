package kr.org.dagather.domain.profile.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.org.dagather.common.exception.CustomException;
import kr.org.dagather.common.response.ErrorCode;
import kr.org.dagather.common.util.AuthUtil;
import kr.org.dagather.common.util.S3Util;
import kr.org.dagather.domain.friend.entity.Friend;
import kr.org.dagather.domain.friend.repository.FriendRepository;
import kr.org.dagather.domain.profile.dto.ProfileGetListResponseDto;
import kr.org.dagather.domain.profile.dto.ProfileGetResponseDto;
import kr.org.dagather.domain.profile.dto.ProfileInterestDto;
import kr.org.dagather.domain.profile.dto.ProfileMapper;
import kr.org.dagather.domain.profile.dto.ProfilePurposeDto;
import kr.org.dagather.domain.profile.dto.ProfileRequestDto;
import kr.org.dagather.domain.profile.dto.ProfileResponseDto;
import kr.org.dagather.domain.profile.entity.Location;
import kr.org.dagather.domain.profile.entity.Profile;
import kr.org.dagather.domain.profile.entity.ProfileInterest;
import kr.org.dagather.domain.profile.entity.ProfilePurpose;
import kr.org.dagather.domain.profile.repository.LocationRepository;
import kr.org.dagather.domain.profile.repository.ProfileInterestRepository;
import kr.org.dagather.domain.profile.repository.ProfilePurposeRepository;
import kr.org.dagather.domain.profile.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfileService {

	private final ProfileRepository profileRepository;
	private final ProfilePurposeRepository profilePurposeRepository;
	private final ProfileInterestRepository profileInterestRepository;
	private final FriendRepository friendRepository;
	private final LocationRepository locationRepository;
	private final ProfileMapper profileMapper;
	private final S3Util s3Util;

	@Transactional
	public ProfileResponseDto saveProfile(ProfileRequestDto requestDto) {

		Profile profile = profileRepository.findProfileByMemberId(requestDto.getMemberId())
			.orElse(new Profile());

		int rperiod;
		boolean gender;
		float longitude, latitude;
		try {
			rperiod = Integer.parseInt(requestDto.getRperiod());
			gender = Boolean.parseBoolean(requestDto.getGender());
			longitude = Float.parseFloat(requestDto.getLongitude());
			latitude = Float.parseFloat(requestDto.getLatitude());
		} catch (Exception e) {
			throw new CustomException(ErrorCode.BAD_PARAMETER_TYPE);
		}


		profile.setMemberId(requestDto.getMemberId());
		profile.setResident(requestDto.getResident());
		profile.setName(requestDto.getName());
		profile.setGender(gender);
		profile.setBirth(LocalDate.parse(requestDto.getBirth()));
		profile.setNationality(requestDto.getNationality());
		profile.setRperiod(rperiod);
		profile.setIntroduction(requestDto.getIntroduction());

		String imageUrl = s3Util.profileImageUpload(requestDto.getImage());
		profile.setImageUrl(imageUrl);

		profileRepository.save(profile);

		profilePurposeRepository.deleteAllByProfile(profile);
		requestDto.getPurposes().forEach(p -> {
			profilePurposeRepository.save(ProfilePurpose.builder().profile(profile).purpose(p).build());
		});
		List<ProfilePurpose> profilePurposes = profilePurposeRepository.findAllByProfile(profile);

		profileInterestRepository.deleteAllByProfile(profile);
		requestDto.getInterests().forEach(i -> {
			profileInterestRepository.save(ProfileInterest.builder().profile(profile).interest(i).build());
		});
		List<ProfileInterest> profileInterests = profileInterestRepository.findAllByProfile(profile);

		locationRepository.deleteAllByProfile(profile);
		locationRepository.save(Location.builder()
			.profile(profile)
			.longitude(longitude)
			.latitude(latitude)
			.build());

		return profileMapper.toResponseDto(profile, profilePurposes, profileInterests);
	}

	@Transactional
	public ProfileGetResponseDto getProfile(String memberId) {

		// get current user info
		String currentMemberId = AuthUtil.getLoggedInId();
		System.out.println("profile service currentMemberId: " + currentMemberId);
		if (currentMemberId == null || currentMemberId.isEmpty()) throw new CustomException(ErrorCode.NO_ID);

		Profile myProfile = profileRepository.findProfileByMemberId(currentMemberId).orElse(null);
		if (myProfile == null) throw new CustomException(ErrorCode.PROFILE_NOT_FOUND);

		List<String> myPurposes = new ArrayList<>();
		profilePurposeRepository.findAllByProfile(myProfile).forEach(p -> { myPurposes.add(p.getPurpose()); });

		List<String> myInterests = new ArrayList<>();
		profileInterestRepository.findAllByProfile(myProfile).forEach(i -> { myInterests.add(i.getInterest()); });

		// get target user info
		Profile profile = profileRepository.findProfileByMemberId(memberId).orElse(null);
		if (profile == null) throw new CustomException(ErrorCode.PROFILE_NOT_FOUND);

		List<ProfilePurpose> profilePurposes = profilePurposeRepository.findAllByProfile(profile);
		List<ProfileInterest> profileInterests = profileInterestRepository.findAllByProfile(profile);

		// compare purposes and interests
		List<ProfilePurposeDto> purposes = new ArrayList<>();
		profilePurposes.forEach(p -> { purposes.add(new ProfilePurposeDto(p.getPurpose(), myPurposes.contains(p.getPurpose()))); });

		List<ProfileInterestDto> interests = new ArrayList<>();
		profileInterests.forEach(i -> { interests.add(new ProfileInterestDto(i.getInterest(), myInterests.contains(i.getInterest()))); });

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

		List<String> myPurposes = new ArrayList<>();
		profilePurposeRepository.findAllByProfile(myProfile).forEach(p -> { myPurposes.add(p.getPurpose()); });

		List<String> myInterests = new ArrayList<>();
		profileInterestRepository.findAllByProfile(myProfile).forEach(i -> { myInterests.add(i.getInterest()); });


		// TODO: 추천시스템으로 요청 보내고 리스트 받기
		List<Profile> profileList = profileRepository.findAll();
		profileList.remove(myProfile);

		List<ProfileGetListResponseDto> results = new ArrayList<>();

		profileList.forEach(profile -> {
			// get target user info
			List<ProfilePurpose> profilePurposes = profilePurposeRepository.findAllByProfile(profile);
			List<ProfileInterest> profileInterests = profileInterestRepository.findAllByProfile(profile);

			// compare purposes and interests
			List<ProfilePurposeDto> purposes = new ArrayList<>();
			profilePurposes.forEach(p -> { purposes.add(new ProfilePurposeDto(p.getPurpose(), myPurposes.contains(p.getPurpose()))); });

			List<ProfileInterestDto> interests = new ArrayList<>();
			profileInterests.forEach(i -> { interests.add(new ProfileInterestDto(i.getInterest(), myInterests.contains(i.getInterest()))); });

			results.add(profileMapper.toGetResponseDto(profile, purposes, interests));
		});

		//TODO: filtering
		if (filter != null && filter.equals("nation")) {

		} else if (filter != null && filter.equals("nearby")) {

		}

		return results;
	}

}
