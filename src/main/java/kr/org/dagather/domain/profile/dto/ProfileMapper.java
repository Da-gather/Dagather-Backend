package kr.org.dagather.domain.profile.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import kr.org.dagather.domain.friend.dto.FriendMapper;
import kr.org.dagather.domain.friend.entity.Friend;
import kr.org.dagather.domain.profile.entity.Location;
import kr.org.dagather.domain.profile.entity.Profile;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProfileMapper {

	public ProfileResponseDto toResponseDto(Profile profile) {
		if (profile == null)
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
		builder.purposes(profile.getPurpose());
		builder.interests(profile.getInterest());

		return builder.build();
	}

	public ProfileGetResponseDto toGetResponseDto(Profile profile, List<ProfilePurposeDto> profilePurposes, List<ProfileInterestDto> profileInterests, Friend friend) {
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
		builder.areWeFriend(FriendMapper.areWeFriend(friend));

		return builder.build();
	}

	public ProfileGetListResponseDto toGetResponseDto(Profile profile, List<ProfilePurposeDto> profilePurposes, List<ProfileInterestDto> profileInterests) {

		ProfileGetListResponseDto.ProfileGetListResponseDtoBuilder builder = ProfileGetListResponseDto.builder();

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

		return builder.build();
	}

	public ProfileRecommendRequestItem toRequestItem(Profile profile, Location location) {

		ProfileRecommendRequestItem.ProfileRecommendRequestItemBuilder builder = ProfileRecommendRequestItem.builder();

		builder.id(profile.getId());
		builder.purpose(profile.getPurpose());
		builder.interest(profile.getInterest());
		builder.rperiod(profile.getRperiod());
		builder.latitude(location.getLatitude());
		builder.longitude(location.getLongitude());

		return builder.build();
	}
}
