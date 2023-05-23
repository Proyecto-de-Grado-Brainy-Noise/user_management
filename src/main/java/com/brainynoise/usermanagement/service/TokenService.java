package com.brainynoise.usermanagement.service;

import com.brainynoise.usermanagement.entity.Token;
import com.brainynoise.usermanagement.entity.User;
import com.brainynoise.usermanagement.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//Source: https://github.com/ali-bouali/spring-boot-3-jwt-security
@Service
public class TokenService {
    @Autowired
    private TokenRepository repository;

    public void deleteTokens (User user) {
        List<Token> tokens = repository.findAllValidTokenByUser(user.getEmail());
        for(Token t : tokens){
            repository.deleteById(t.getId());
        }
    }
}
