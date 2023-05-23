package com.brainynoise.usermanagement.controller;

import com.brainynoise.usermanagement.entity.User;
import com.brainynoise.usermanagement.requests.AuthenticationRequest;
import com.brainynoise.usermanagement.requests.RegisterRequest;
import com.brainynoise.usermanagement.responses.AuthenticationResponse;
import com.brainynoise.usermanagement.service.AuthenticationService;
import com.brainynoise.usermanagement.service.JwtService;
import com.brainynoise.usermanagement.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Source: https://github.com/ali-bouali/spring-boot-3-jwt-security
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserService userService;

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
        //Check if email exists
        User temp = userService.getUsersByEmail(request.getEmail());
        if (temp != null) {
            AuthenticationResponse response = AuthenticationResponse.builder()
                    .token("El usuario con email " + request.getEmail() + " ya existe")
                    .build();
            return ResponseEntity.badRequest().body(response);
        }

        //Check if employee_id exists
        temp = userService.getUsersByEmployeeId(request.getIdEmployee());
        if (temp != null) {
            AuthenticationResponse response = AuthenticationResponse.builder()
                    .token("El usuario con id de empleado " + request.getIdEmployee() + " ya existe")
                    .build();
            return ResponseEntity.badRequest().body(response);
        }

        //Check if document exists
        List<User> temp2 = userService.getUsersByDocument(request.getDocument());
        boolean flag = false;
        for (User u : temp2) {
            if (u.getDoctype() == request.getDoctype()) {
                flag = true;
            }
        }
        if (flag) {
            AuthenticationResponse response = AuthenticationResponse.builder()
                    .token("El usuario con el documento ingresado ya existe")
                    .build();
            return ResponseEntity.badRequest().body(response);
        };

        //Register user
        String password = this.generatePassword(8);
        request.setPassword(password);
        AuthenticationResponse response = service.register(request);
        EmailController emailController = new EmailController();
        emailController.emailSender(request.getEmail(), request.getName(), password, "newUser");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @GetMapping("/renovate-token")
    public ResponseEntity<AuthenticationResponse> renovate(HttpServletRequest request) {
        String token =  request.getHeader("Authorization").substring(7);
        String email = jwtService.extractUsername(token);
        return ResponseEntity.ok(service.refreshToken(email));
    }
}
