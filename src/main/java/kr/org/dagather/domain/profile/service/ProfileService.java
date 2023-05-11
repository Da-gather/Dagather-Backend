package kr.org.dagather.domain.profile.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.org.dagather.common.exception.CustomException;
import kr.org.dagather.common.response.ErrorCode;
import kr.org.dagather.common.util.S3Util;
import kr.org.dagather.domain.profile.dto.ProfileMapper;
import kr.org.dagather.domain.profile.dto.ProfileRequestDto;
import kr.org.dagather.domain.profile.dto.ProfileResponseDto;
import kr.org.dagather.domain.profile.entity.Profile;
import kr.org.dagather.domain.profile.entity.ProfileInterest;
import kr.org.dagather.domain.profile.entity.ProfilePurpose;
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
	private final ProfileMapper profileMapper;
	private final S3Util s3Util;

	@Transactional
	public ProfileResponseDto save(ProfileRequestDto requestDto) {

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

		requestDto.getPurposes().forEach(p -> {
			if (!profilePurposeRepository.existsByProfileAndPurpose(profile, p)) {
				profilePurposeRepository.save(ProfilePurpose.builder().profile(profile).purpose(p).build());
			}
		});
		List<ProfilePurpose> profilePurposes = profilePurposeRepository.findAllByProfile(profile);

		requestDto.getInterests().forEach(i -> {
			if (!profileInterestRepository.existsByProfileAndInterest(profile, i)) {
				profileInterestRepository.save(ProfileInterest.builder().profile(profile).interest(i).build());
			}
		});
		List<ProfileInterest> profileInterests = profileInterestRepository.findAllByProfile(profile);

		return profileMapper.toResponseDto(profile, profilePurposes, profileInterests);
	}
}
