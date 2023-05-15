package kr.org.dagather.domain.friend.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FriendRequestDto {

	@NotBlank(message = "sender가 없습니다.")
	private String sender;

	@NotBlank(message = "receiver가 없습니다.")
	private String receiver;
}
