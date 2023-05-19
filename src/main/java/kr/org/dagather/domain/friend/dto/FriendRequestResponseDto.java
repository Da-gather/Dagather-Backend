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
public class FriendRequestResponseDto {
	private Long friendId;
	private String memberId;
	private String name;
	private String imageUrl;
}
