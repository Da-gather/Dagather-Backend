package kr.org.dagather.domain.profile.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.org.dagather.common.response.ApiResponse;
import kr.org.dagather.common.response.SuccessCode;
import kr.org.dagather.domain.profile.dto.ProfileGetResponseDto;
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
	public ApiResponse<ProfileResponseDto> saveProfile(@Valid ProfileRequestDto requestDto) {
		ProfileResponseDto responseDto = profileService.saveProfile(requestDto);
		return ApiResponse.success(SuccessCode.PROFILE_SAVE_SUCCESS, responseDto);
	}

	@GetMapping("/{memberId}")
	public ApiResponse<ProfileGetResponseDto> getProfile(@PathVariable String memberId){
		ProfileGetResponseDto responseDto = profileService.getProfile(memberId);
		return ApiResponse.success(SuccessCode.GET_SUCCESS, responseDto);
	}

	// @GetMapping("/list")
	// public ApiResponse<List<ProfileResponseDto>> getProfileList(@RequestParam String filter) {
	// 	List<ProfileResponseDto> responseDtoList = profileService.getProfileList(filter);
	// 	return ApiResponse.success(SuccessCode.GET_SUCCESS, responseDtoList);
	// }

}
