package kr.org.dagather.domain.friend.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.org.dagather.common.response.ApiResponse;
import kr.org.dagather.common.response.SuccessCode;
import kr.org.dagather.common.util.AuthUtil;
import kr.org.dagather.domain.friend.dto.FriendChatroomRequestDto;
import kr.org.dagather.domain.friend.dto.FriendRequestDto;
import kr.org.dagather.domain.friend.dto.FriendListResponseDto;
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
	public ApiResponse<FriendResponseDto> requestFriend(@RequestHeader("Authorization") String loggedInId, @Valid @RequestBody FriendRequestDto requestDto) {
		AuthUtil.setLoggedInId(loggedInId);
		FriendResponseDto responseDto = friendService.requestFriend(requestDto);
		return ApiResponse.success(SuccessCode.FRIEND_CREATE_SUCCESS, responseDto);
	}

	@PatchMapping("/{friendId}")
	public ApiResponse<FriendResponseDto> acceptFriend(@PathVariable String friendId) {
		FriendResponseDto responseDto = friendService.acceptFriend(friendId);
		return ApiResponse.success(SuccessCode.ACCEPT_FRIEND_SUCCESS, responseDto);
	}

	@PatchMapping("/chatroom")
	public ApiResponse<FriendResponseDto> setChatroom(@Valid @RequestBody FriendChatroomRequestDto requestDto) {
		FriendResponseDto responseDto = friendService.setChatroom(requestDto);
		return ApiResponse.success(SuccessCode.SET_CHATROOM_SUCCESS, responseDto);
	}

	@DeleteMapping("/{friendId}")
	public ApiResponse<Long> rejectFriend(@RequestHeader("Authorization") String loggedInId, @PathVariable String friendId) {
		AuthUtil.setLoggedInId(loggedInId);
		Long deletedId = friendService.rejectFriend(friendId);
		return ApiResponse.success(SuccessCode.REJECT_FRIEND_SUCCESS, deletedId);
	}

	@GetMapping("/list/{requestBy}")
	public ApiResponse<List<FriendListResponseDto>> getRequestList(@RequestHeader("Authorization") String loggedInId, @PathVariable String requestBy) {
		AuthUtil.setLoggedInId(loggedInId);
		List<FriendListResponseDto> responseDtoList = friendService.getRequestList(requestBy);
		return ApiResponse.success(SuccessCode.GET_SUCCESS, responseDtoList);
	}

	@GetMapping("/list")
	public ApiResponse<List<FriendListResponseDto>> getFriendList() {
		List<FriendListResponseDto> responseDtoList = friendService.getFriendList();
		return ApiResponse.success(SuccessCode.GET_SUCCESS, responseDtoList);
	}
}
