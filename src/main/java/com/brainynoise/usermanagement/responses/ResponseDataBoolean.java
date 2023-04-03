package com.brainynoise.usermanagement.responses;

public class ResponseDataBoolean {
    private Boolean message;
    private int code;

    public ResponseDataBoolean(Boolean message, int code) {
        this.message = message;
        this.code = code;
    }

    public Boolean getMessage() {
        return message;
    }

    public void setMessage(Boolean message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
