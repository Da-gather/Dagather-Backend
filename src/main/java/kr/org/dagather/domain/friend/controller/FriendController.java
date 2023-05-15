package kr.org.dagather.domain.friend.controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.org.dagather.common.response.ApiResponse;
import kr.org.dagather.common.response.SuccessCode;
import kr.org.dagather.domain.friend.dto.FriendRequestDto;
import kr.org.dagather.domain.friend.dto.FriendResponseDto;
import kr.org.dagather.domain.friend.service.FriendService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("api/v1/friend")
@RequiredArgsConstructor
public class FriendController {

	private final FriendService friendService;

	@PostMapping("")
	public ApiResponse<FriendResponseDto> requestFriend(@Valid @RequestBody FriendRequestDto requestDto) {
		FriendResponseDto responseDto = friendService.requestFriend(requestDto);
		return ApiResponse.success(SuccessCode.FRIEND_CREATE_SUCCESS, responseDto);
	}
}
