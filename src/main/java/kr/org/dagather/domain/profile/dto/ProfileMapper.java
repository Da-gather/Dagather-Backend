package kr.org.dagather.domain.profile.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import kr.org.dagather.domain.profile.entity.Profile;
import kr.org.dagather.domain.profile.entity.ProfileInterest;
import kr.org.dagather.domain.profile.entity.ProfilePurpose;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProfileMapper {

	public ProfileResponseDto toResponseDto(Profile profile, List<ProfilePurpose> profilePurposes, List<ProfileInterest> profileInterests) {
		if (profile == null || profilePurposes == null || profileInterests == null)
			throw new NullPointerException();

		ProfileResponseDto.ProfileResponseDtoBuilder builder = ProfileResponseDto.builder();

		builder.memberId(profile.getMemberId());
		builder.resident(profile.getResident());
		builder.name(profile.getName());
		builder.imageUrl(profile.getImageUrl());
		builder.gender(profile.isGender());
		builder.birth(String.valueOf(profile.getBirth()));
		builder.nationality(profile.getNationality());
		builder.rperiod(profile.getRperiod());
		builder.introduction(profile.getIntroduction());

		List<String> purposes = new ArrayList<>();
		profilePurposes.forEach(p -> { purposes.add(p.getPurpose()); });
		builder.purposes(purposes);

		List<String> interests = new ArrayList<>();
		profileInterests.forEach(i -> { interests.add(i.getInterest()); });
		builder.interests(interests);

		return builder.build();
	}

	public ProfileGetResponseDto toGetResponseDto(Profile profile, List<ProfilePurposeDto> profilePurposes, List<ProfileInterestDto> profileInterests, boolean areWeFriend) {
		if (profile == null || profilePurposes == null || profileInterests == null)
			throw new NullPointerException();

		ProfileGetResponseDto.ProfileGetResponseDtoBuilder builder = ProfileGetResponseDto.builder();

		builder.memberId(profile.getMemberId());
		builder.resident(profile.getResident());
		builder.name(profile.getName());
		builder.imageUrl(profile.getImageUrl());
		builder.gender(profile.isGender());
		builder.birth(String.valueOf(profile.getBirth()));
		builder.nationality(profile.getNationality());
		builder.rperiod(profile.getRperiod());
		builder.introduction(profile.getIntroduction());
		builder.purposes(profilePurposes);
		builder.interests(profileInterests);
		builder.areWeFriend(areWeFriend);

		return builder.build();
	}
}
