package kr.org.dagather.common.exception;

import kr.org.dagather.common.response.ErrorCode;
import lombok.Getter;

@Getter
public class DuplicateException extends RuntimeException {
	private final ErrorCode code;

	public DuplicateException(ErrorCode code) {
		super(code.getMessage());
		this.code = code;
	}
}
