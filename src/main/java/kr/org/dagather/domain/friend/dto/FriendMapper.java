package kr.org.dagather.domain.friend.dto;

import org.springframework.stereotype.Component;

import kr.org.dagather.domain.friend.entity.Friend;
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
		builder.areWeFriend(friend.isAreWeFriend());

		return builder.build();
	}

	public FriendRequestResponseDto toResponseDto(Long friendId, String memberId, String name, String imageUrl) {
		if (memberId == null || name == null || imageUrl == null)
			throw new NullPointerException();

		FriendRequestResponseDto.FriendRequestResponseDtoBuilder builder = FriendRequestResponseDto.builder();
		builder.friendId(friendId);
		builder.memberId(memberId);
		builder.name(name);
		builder.imageUrl(imageUrl);

		return builder.build();
	}
}
