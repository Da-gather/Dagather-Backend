package kr.org.dagather.common.exception;

import kr.org.dagather.common.response.ErrorCode;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
	private final ErrorCode code;

	public CustomException(ErrorCode code) {
		super(code.getMessage());
		this.code = code;
	}
}
