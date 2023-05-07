package kr.org.dagather.common.exception;

import kr.co.preq.global.common.util.response.ErrorCode;
import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {
	private final ErrorCode code;

	public NotFoundException(ErrorCode code) {
		super(code.getMessage());
		this.code = code;
	}
}
