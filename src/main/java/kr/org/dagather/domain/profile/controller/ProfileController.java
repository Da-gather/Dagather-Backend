package kr.org.dagather.domain.profile.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import kr.org.dagather.common.response.ApiResponse;
import kr.org.dagather.common.response.SuccessCode;
import kr.org.dagather.common.util.AuthUtil;
import kr.org.dagather.domain.profile.dto.ProfileGetListResponseDto;
import kr.org.dagather.domain.profile.dto.ProfileGetResponseDto;
import kr.org.dagather.domain.profile.dto.ProfileImagePostRequestDto;
import kr.org.dagather.domain.profile.dto.ProfileImagePostResponseDto;
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
	public ApiResponse<ProfileResponseDto> saveProfile(@Valid @RequestBody ProfileRequestDto requestDto) {
		ProfileResponseDto responseDto = profileService.saveProfile(requestDto);
		return ApiResponse.success(SuccessCode.PROFILE_SAVE_SUCCESS, responseDto);
	}

	@GetMapping("/{memberId}")
	public ApiResponse<ProfileGetResponseDto> getProfile(@RequestHeader("Authorization") String loggedInId, @PathVariable String memberId){
		AuthUtil.setLoggedInId(loggedInId);
		ProfileGetResponseDto responseDto = profileService.getProfile(memberId);
		return ApiResponse.success(SuccessCode.GET_SUCCESS, responseDto);
	}

	@GetMapping("/list")
	public ApiResponse<List<ProfileGetListResponseDto>> getProfileList(@RequestHeader("Authorization") String loggedInId, @RequestParam String filter) {
		AuthUtil.setLoggedInId(loggedInId);
		List<ProfileGetListResponseDto> responseDtoList = profileService.getProfileList(filter);
		return ApiResponse.success(SuccessCode.GET_SUCCESS, responseDtoList);
	}

	@PostMapping("/image")
	public ApiResponse<ProfileImagePostResponseDto> postProfileImage(@Valid ProfileImagePostRequestDto requestDto) {
		ProfileImagePostResponseDto responseDto = profileService.postProfileImage(requestDto);
		return ApiResponse.success(SuccessCode.PROFILE_IMAGE_UPLOAD_SUCCESS, responseDto);
	}

}
