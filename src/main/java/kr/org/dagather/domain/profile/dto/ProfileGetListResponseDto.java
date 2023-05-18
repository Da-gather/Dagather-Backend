package kr.org.dagather.domain.profile.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProfileGetListResponseDto {
	private String memberId;
	private String resident;
	private String name;
	private String imageUrl;
	private boolean gender;
	private String birth;
	private String nationality;
	private String rperiod;
	private String introduction;
	private List<ProfilePurposeDto> purposes;
	private List<ProfileInterestDto> interests;
}
