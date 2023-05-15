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
}
