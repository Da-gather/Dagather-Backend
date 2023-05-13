package kr.org.dagather.domain.profile.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileRequestDto {

	@NotBlank(message = "멤버 아이디가 없습니다.")
	private String memberId;

	@NotBlank(message = "거주지가 없습니다.")
	private String resident;

	@NotBlank(message = "이름이 없습니다.")
	private String name;

	@NotNull(message = "이미지가 없습니다.")
	private MultipartFile image;

	@NotNull(message = "성별이 없습니다.")
	private boolean gender;

	@NotBlank(message = "생년월일이 없습니다.")
	private String birth;

	@NotBlank(message = "국적이 없습니다.")
	private String nationality;

	@NotNull(message = "거주기간이 없습니다.")
	private int rperiod;

	@NotBlank(message = "자기소개가 없습니다.")
	private String introduction;

	@Size(min = 1, message = "가입목적은 1개 이상 등록되어야 합니다")
	private List<String> purposes;

	@Size(min = 1, message = "관심사는 1개 이상 등록되어야 합니다")
	private List<String> interests;

	@NotNull(message = "경도가 없습니다.")
	private float longitude;

	@NotNull(message = "위도가 없습니다.")
	private float latitude;

}
