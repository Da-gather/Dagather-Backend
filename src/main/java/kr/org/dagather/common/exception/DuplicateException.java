package kr.org.dagather.common.exception;

import kr.co.preq.global.common.util.response.ErrorCode;
import lombok.Getter;

@Getter
public class DuplicateException extends RuntimeException {
	private final ErrorCode code;

	public DuplicateException(ErrorCode code) {
		super(code.getMessage());
		this.code = code;
	}
}
