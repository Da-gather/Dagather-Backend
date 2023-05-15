package kr.org.dagather.common.response;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

	/* 400 BAD REQUEST */
	// general
	NO_ID(BAD_REQUEST, "존재하지 않는 id 입니다"),
	BAD_PARAMETER(BAD_REQUEST, "요청 파라미터가 잘못되었습니다."),
	BAD_PARAMETER_TYPE(BAD_REQUEST, "지원하지 않는 파라미터 형식입니다."),

	// friend
	DUPLICATED_FRIEND(BAD_REQUEST, "이미 존재하는 친구입니다."),

	// 파일
	BAD_FILE_NAME(BAD_REQUEST, "파일 이름이 올바르지 않습니다."),
	BAD_FILE_EXTENSION(BAD_REQUEST, "지원하지 않는 파일 형식입니다."),

	/* 401 UNAUTHORIZED: 인증 실패 */
	UNAUTHORIZED_USER(UNAUTHORIZED, "만료되었거나 잘못된 토큰입니다. 토큰을 확인해주세요."),

	/* 404 NOT_FOUND: 리소스를 찾을 수 없음 */
	DATA_NOT_FOUND(NOT_FOUND, "해당 데이터를 찾을 수 없습니다."),
	USER_NOT_FOUND(NOT_FOUND, "유저를 찾을 수 없습니다."),
	TOKEN_NOT_FOUND(NOT_FOUND, "다시 로그인해주세요."),
	PROFILE_NOT_FOUND(NOT_FOUND, "유저의 프로필 정보가 존재하지 않습니다."),
	PROFILE_PURPOSE_NOT_FOUND(NOT_FOUND, "가입목적 정보가 존재하지 않습니다"),
	FRIEND_NOT_FOUND(NOT_FOUND, "친구 정보가 존재하지 않습니다."),

	/* 500 INTERNAL_SERVER_ERROR : 서버 오류 */
	FILE_UPLOAD_FAIL(INTERNAL_SERVER_ERROR, "파일 업로드에 실패했습니다."),
	SERVER_ERROR(INTERNAL_SERVER_ERROR, "서버 내부 오류로 인해 응답을 제공할 수 없습니다.");



	private final HttpStatus status;
	private final String message;
}
