package com.ssafy.withssafy.errorcode;

public enum ErrorCode {
    INVALID_REQUEST("E0001"),
    FAILED_TO_SAVE_FILE("E0002"),
    ;

    private final String errorCode;

    ErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getCode() {
        return String.format("error.%s", errorCode);
    }
}
