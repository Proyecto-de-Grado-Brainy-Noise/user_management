package com.brainynoise.usermanagement.repository;

import com.brainynoise.usermanagement.entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResultRepository extends JpaRepository<Result, String> {
    List<Result> findAllByEmailSearcher(String email);
}
