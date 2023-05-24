package kr.org.dagather.domain.profile.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProfileRecommendRequestDto {
	private Long id;
	private List<ProfileRecommendRequestItem> profiles;
}
