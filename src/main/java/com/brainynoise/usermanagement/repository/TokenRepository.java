package com.brainynoise.usermanagement.repository;

import com.brainynoise.usermanagement.entity.Token;
import com.brainynoise.usermanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {
    @Query("select t from Token t inner join User u where u.email = :email")
    List<Token> findAllValidTokenByUser(String email);

    Optional<Token> findByToken(String token);
}
