package com.brainynoise.usermanagement.repository;

import com.brainynoise.usermanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {
    User findByIdEmployee(BigInteger idEmployee);
    List<User> findAllByDocument(String document);

}
