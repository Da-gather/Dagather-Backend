package kr.org.dagather.domain.profile.dto;

import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileImagePostRequestDto {
	@NotNull(message = "이미지가 없습니다.")
	private MultipartFile image;
}
