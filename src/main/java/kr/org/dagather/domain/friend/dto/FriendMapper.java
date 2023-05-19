package kr.org.dagather.domain.friend.dto;

import org.springframework.stereotype.Component;

import kr.org.dagather.common.filter.AuthFilter;
import kr.org.dagather.common.util.AuthUtil;
import kr.org.dagather.domain.friend.entity.Friend;
import kr.org.dagather.domain.profile.entity.Profile;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FriendMapper {

	public FriendResponseDto toResponseDto(Friend friend) {
		if (friend == null)
			throw new NullPointerException();

		FriendResponseDto.FriendResponseDtoBuilder builder = FriendResponseDto.builder();
		builder.friendId(friend.getId());
		builder.sender(friend.getSender());
		builder.receiver(friend.getReceiver());
		builder.areWeFriend(areWeFriend(friend));
		builder.chatroomId(friend.getChatroomId());

		return builder.build();
	}

	public FriendListResponseDto toResponseDto(Friend friend, Profile profile) {
		if (friend == null)
			throw new NullPointerException();

		FriendListResponseDto.FriendListResponseDtoBuilder builder = FriendListResponseDto.builder();
		builder.friendId(friend.getId());
		builder.memberId(profile.getMemberId());
		builder.name(profile.getName());
		builder.imageUrl(profile.getImageUrl());
		builder.areWeFriend(areWeFriend(friend));
		builder.chatroomId(friend.getChatroomId());

		return builder.build();
	}

	public static String areWeFriend(Friend friend) {
		String memberId = AuthUtil.getMemberId();
		// String memberId = AuthFilter.getCurrentMemberId();

		if (friend == null) return "NONE";
		else if (!friend.isAreWeFriend()) {
			if (friend.getSender().equals(memberId)) return "SEND";
			else if (friend.getReceiver().equals(memberId)) return "RECEIVE";
		} else {
			return "FRIEND";
		}
		return "NONE";
	}
}
