package kr.org.dagather.common.exception;

import kr.org.dagather.common.response.ErrorCode;

public class NumberFormatException extends RuntimeException {
	private final ErrorCode code;

	public NumberFormatException(ErrorCode code) {
		super(code.getMessage());
		this.code = code;
	}
}