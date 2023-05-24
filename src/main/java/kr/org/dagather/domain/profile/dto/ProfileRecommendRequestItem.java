package kr.org.dagather.domain.profile.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProfileRecommendRequestItem {
	private Long id;
	private List<String> purpose;
	private List<String> interest;
	private int rperiod;
	private double latitude;
	private double longitude;
}
