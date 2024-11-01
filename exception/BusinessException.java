
package com.report.ro.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    public String getErrorCode() {
		return errorCode;
	}

	private final String errorCode;

    public BusinessException(String message) {
        super(message);
        this.errorCode = "BUSINESS_ERROR";
    }

    public BusinessException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
