package com.brainynoise.usermanagement.repository;

import com.brainynoise.usermanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    User findByIdEmployee(Integer idEmployee);
    List<User> findAllByDocument(String document);
    Optional<User> findByEmail(String email);
}
