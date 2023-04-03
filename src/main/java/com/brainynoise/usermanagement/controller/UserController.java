package com.brainynoise.usermanagement.controller;

import com.brainynoise.usermanagement.entity.Result;
import com.brainynoise.usermanagement.requests.EmailRequest;
import com.brainynoise.usermanagement.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class UserController {

    @Autowired
    private ResultService resultService;

    @PostMapping(value = "history")
    public ResponseEntity<List<Result>> search(@RequestBody EmailRequest request){
        return ResponseEntity.ok(resultService.getHistory(request.getEmail()));
    }
}