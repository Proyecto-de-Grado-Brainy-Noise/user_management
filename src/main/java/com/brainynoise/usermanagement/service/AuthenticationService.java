package com.brainynoise.usermanagement.service;

import com.brainynoise.usermanagement.responses.AuthenticationResponse;
import com.brainynoise.usermanagement.requests.RegisterRequest;
import com.brainynoise.usermanagement.entity.Token;
import com.brainynoise.usermanagement.entity.TokenType;
import com.brainynoise.usermanagement.entity.User;
import com.brainynoise.usermanagement.repository.TokenRepository;
import com.brainynoise.usermanagement.repository.UserRepository;
import com.brainynoise.usermanagement.requests.AuthenticationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .name(request.getName())
                .name2(request.getName2())
                .role(request.getRole())
                .lastname(request.getLastname())
                .lastname2(request.getLastname2())
                .doctype(request.getDoctype())
                .document(request.getDocument())
                .birthdate(request.getBirthdate())
                .email(request.getEmail())
                .idEmployee(request.getIdEmployee())
                .jobtitle(request.getJobtitle())
                .area(request.getArea())
                .observations(request.getObservations())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        var savedUser = repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        saveUserToken(savedUser, jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        System.out.println(request.getEmail() + " " + request.getPassword());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        System.out.println("Use authentication manager");
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        System.out.println(jwtToken);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        System.out.println("Antes del return");
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getEmail());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }
}