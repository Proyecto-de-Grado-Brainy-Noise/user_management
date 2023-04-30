package com.brainynoise.usermanagement.requests;

public class EmailRequest {
    private String email;
    private String confirmEmail;

    public EmailRequest(String email, String confirmEmail) {
        this.email = email;
        this.confirmEmail = confirmEmail;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getConfirmEmail() {
        return confirmEmail;
    }

    public void setConfirmEmail(String confirmEmail) {
        this.confirmEmail = confirmEmail;
    }
}

