package com.brainynoise.usermanagement.service;

import com.brainynoise.usermanagement.entity.Result;
import com.brainynoise.usermanagement.repository.ResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResultService {
    @Autowired
    private ResultRepository repository;

    public Result saveResult(Result result) {
        return repository.save(result);
    }

    public List<Result> getHistory(String email) {
        return repository.findAllByEmailSearcher(email);
    }
}
