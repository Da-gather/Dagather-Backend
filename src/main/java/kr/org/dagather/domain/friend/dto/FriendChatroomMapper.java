package kr.org.dagather.domain.friend.dto;

import org.springframework.stereotype.Component;

import kr.org.dagather.domain.friend.entity.Friend;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FriendChatroomMapper {

	public FriendChatroomResponseDto toResponseDto(Friend friend) {
		if (friend == null)
			throw new NullPointerException();

		FriendChatroomResponseDto.FriendChatroomResponseDtoBuilder builder = FriendChatroomResponseDto.builder();
		builder.friendId(friend.getId());
		builder.sender(friend.getSender());
		builder.receiver(friend.getReceiver());
		builder.areWeFriend(friend.isAreWeFriend());
		builder.chatroomId(friend.getChatroomId());

		return builder.build();
	}
}
