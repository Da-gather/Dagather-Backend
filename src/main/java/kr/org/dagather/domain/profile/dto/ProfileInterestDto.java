package kr.org.dagather.domain.profile.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProfileInterestDto {
	private String interest;
	private boolean isSame;
}
