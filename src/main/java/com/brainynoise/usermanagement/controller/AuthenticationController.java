package com.brainynoise.usermanagement.controller;

import com.brainynoise.usermanagement.requests.AuthenticationRequest;
import com.brainynoise.usermanagement.requests.RegisterRequest;
import com.brainynoise.usermanagement.responses.AuthenticationResponse;
import com.brainynoise.usermanagement.service.AuthenticationService;
import lombok.*;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;

    private String generatePassword(int n) {
        //Choose a random character from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz" + "!@#$%^&*()_+";
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            //Select a random character from the string and append it to the string builder
            int index = (int)(AlphaNumericString.length() * Math.random());
            sb.append(AlphaNumericString.charAt(index));
        }
        return sb.toString();
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        try {
            String passsword = this.generatePassword(8);
            System.out.println(passsword);
            request.setPassword(passsword);
            return ResponseEntity.ok(service.register(request));
        } catch (DuplicateKeyException e) {
            AuthenticationResponse response = AuthenticationResponse.builder()
                    .token("El usuario con email " + request.getEmail() + " ya existe")
                    .build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        System.out.println("holllaaa");
        return ResponseEntity.ok(service.authenticate(request));
    }
}
