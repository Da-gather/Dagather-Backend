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
public class FriendListResponseDto {
	private Long friendId;
	private String memberId;
	private String name;
	private String imageUrl;
	private String areWeFriend;
	private String chatroomId;
}
