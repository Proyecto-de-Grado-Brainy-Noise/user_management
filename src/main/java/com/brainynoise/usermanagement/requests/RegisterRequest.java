package com.brainynoise.usermanagement.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String email;
    private String role;
    private String name;
    private String name2;
    private String lastname;
    private String lastname2;
    private int doctype;
    private String document;
    private String birthdate;
    private int idEmployee;
    private String jobtitle;
    private String area;
    private String observations;
    private String creationDate;
    private String password;
}
