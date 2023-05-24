package kr.org.dagather.domain.profile.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileRecommendResponseDto {
	private int status;
	private String message;
	private dataObject data;

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public class dataObject {
		private List<Long> sortedIdList;
	}
}
