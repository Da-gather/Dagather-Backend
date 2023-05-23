package kr.org.dagather.common.response;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SuccessCode {

	/* 200 OK */
	GET_SUCCESS(OK, "조회 성공"),

	// 인증
	SIGNUP_SUCCESS(OK, "회원가입 성공"),
	LOGIN_SUCCESS(OK, "로그인 성공"),
	LOGOUT_SUCCESS(OK, "로그아웃 성공"),
	ACCOUNT_READ_SUCCESS(OK, "계정 조회 성공"),
	TOKEN_REISSUE_SUCCESS(OK, "토큰 재발급 성공"),

	// 프로필
	PROFILE_SAVE_SUCCESS(OK, "프로필 저장 성공"),
	PROFILE_IMAGE_UPLOAD_SUCCESS(OK, "프로필 이미지 업로드 성공"),

	// 미션
	MISSION_SAVE_SUCCESS(OK, "미션 추가 성공"),
	MISSION_CREATE_SUCCESS(OK, "미션 생성 성공"),
	MISSION_READ_SUCCESS(OK, "완료 미션 조회 성공"),
	MISSION_COMPLETE_SUCCESS(OK, "미션 완료 여부 수정 성공"),

	// 게시판
	BOARD_POST_SUCCESS(CREATED, "글 작성 성공"),
	ARTICLE_DELETE_SUCCESS(OK, "게시물 삭제 성공"),
	COMMENT_DELETE_SUCCESS(OK, "댓글 삭제 성공"),

	// 친구
	ACCEPT_FRIEND_SUCCESS(OK, "친구 수락 성공"),
	REJECT_FRIEND_SUCCESS(OK, "친구 삭제 성공"),
	SET_CHATROOM_SUCCESS(OK, "채팅방 설정 성공"),

	/* 201 CREATED */
	FRIEND_CREATE_SUCCESS(CREATED, "친구 신청 성공");

	private final HttpStatus status;
	private final String message;
}
