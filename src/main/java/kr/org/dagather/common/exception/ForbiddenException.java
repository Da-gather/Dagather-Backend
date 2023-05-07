package kr.org.dagather.common.exception;

import kr.org.dagather.common.response.ErrorCode;
import lombok.Getter;

@Getter
public class ForbiddenException extends RuntimeException {
	private final ErrorCode code;

	public ForbiddenException(ErrorCode code) {
		super(code.getMessage());
		this.code = code;
	}
}
