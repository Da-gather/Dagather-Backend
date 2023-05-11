package kr.org.dagather.domain.profile.controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.org.dagather.common.response.ApiResponse;
import kr.org.dagather.common.response.SuccessCode;
import kr.org.dagather.domain.profile.dto.ProfileRequestDto;
import kr.org.dagather.domain.profile.dto.ProfileResponseDto;
import kr.org.dagather.domain.profile.service.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("api/v1/profile")
@RequiredArgsConstructor
public class ProfileController {

	private final ProfileService profileService;

	@PutMapping("")
	public ApiResponse<ProfileResponseDto> save(@Valid ProfileRequestDto requestDto) {
		ProfileResponseDto responseDto = profileService.save(requestDto);
		return ApiResponse.success(SuccessCode.PROFILE_SAVE_SUCCESS, responseDto);
	}
}
