package kr.org.dagather.domain.friend.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FriendChatroomRequestDto {

	@NotNull(message = "friendId가 없습니다.")
	private Long friendId;

	@NotBlank(message = "chatroomId가 없습니다.")
	private String chatroomId;
}
