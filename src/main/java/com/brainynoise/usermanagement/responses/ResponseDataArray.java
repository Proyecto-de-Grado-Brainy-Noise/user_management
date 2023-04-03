package com.brainynoise.usermanagement.responses;

import com.brainynoise.usermanagement.entity.User;
import java.util.List;

public class ResponseDataArray {
    private List<User> message;
    private int code;

    public ResponseDataArray(List<User> message, int code) {
        this.message = message;
        this.code = code;
    }

    public List<User> getMessage() {
        return message;
    }

    public void setMessage(List<User> message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
