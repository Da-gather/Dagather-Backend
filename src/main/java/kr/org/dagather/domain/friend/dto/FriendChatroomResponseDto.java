package kr.org.dagather.domain.friend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FriendChatroomResponseDto {
	private Long friendId;
	private String sender;
	private String receiver;
	private String areWeFriend;
	private String chatroomId;
}
