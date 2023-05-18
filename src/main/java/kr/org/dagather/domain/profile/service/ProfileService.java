package kr.org.dagather.domain.profile.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.org.dagather.common.exception.CustomException;
import kr.org.dagather.common.filter.AuthFilter;
import kr.org.dagather.common.response.ErrorCode;
import kr.org.dagather.common.util.S3Util;
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

		profile.setMemberId(requestDto.getMemberId());
		profile.setResident(requestDto.getResident());
		profile.setName(requestDto.getName());
		profile.setGender(requestDto.isGender());
		profile.setBirth(LocalDate.parse(requestDto.getBirth()));
		profile.setNationality(requestDto.getNationality());
		profile.setRperiod(requestDto.getRperiod());
		profile.setIntroduction(requestDto.getIntroduction());

		String imageUrl = s3Util.profileImageUpload(requestDto.getImage());
		profile.setImageUrl(imageUrl);

		profileRepository.save(profile);

		profilePurposeRepository.deleteAllByProfile(profile);
		requestDto.getPurposes().forEach(p -> {
			if (!profilePurposeRepository.existsByProfileAndPurpose(profile, p)) {
				profilePurposeRepository.save(ProfilePurpose.builder().profile(profile).purpose(p).build());
			}
		});
		List<ProfilePurpose> profilePurposes = profilePurposeRepository.findAllByProfile(profile);

		profileInterestRepository.deleteAllByProfile(profile);
		requestDto.getInterests().forEach(i -> {
			if (!profileInterestRepository.existsByProfileAndInterest(profile, i)) {
				profileInterestRepository.save(ProfileInterest.builder().profile(profile).interest(i).build());
			}
		});
		List<ProfileInterest> profileInterests = profileInterestRepository.findAllByProfile(profile);

		locationRepository.save(Location.builder()
			.memberId(requestDto.getMemberId())
			.longitude(requestDto.getLongitude())
			.latitude(requestDto.getLatitude())
			.build());

		return profileMapper.toResponseDto(profile, profilePurposes, profileInterests);
	}

	@Transactional
	public ProfileGetResponseDto getProfile(String memberId) {

		// get current user info
		String currentMemberId = AuthFilter.getCurrentMemberId();
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
		boolean areWeFriend = friendRepository.areWeFriend(currentMemberId, memberId).orElse(false);

		return profileMapper.toGetResponseDto(profile, purposes, interests, areWeFriend);
	}

	@Transactional
	public List<ProfileGetListResponseDto> getProfileList(String filter) {

		// get current user info
		String currentMemberId = AuthFilter.getCurrentMemberId();
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
