package com.brainynoise.usermanagement.controller;

import com.brainynoise.usermanagement.entity.User;
import com.brainynoise.usermanagement.responses.ResponseDataArray;
import com.brainynoise.usermanagement.responses.ResponseDataBoolean;
import com.brainynoise.usermanagement.responses.ResponseDataString;
import com.brainynoise.usermanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    private UserService userService;

    @PostMapping(value = "/updateUser")
    public ResponseEntity<ResponseDataString> updateUser(@RequestBody User user) {
        User us = userService.getUsersByEmail(user.getEmail());
        if (us == null) {
            return new ResponseEntity<>(new ResponseDataString("El usuario con email " + user.getEmail() + " no existe", 400), HttpStatus.BAD_REQUEST);
        }
        userService.updateUser(user);
        return new ResponseEntity<>(new ResponseDataString("Usuario actualizado exitosamente", 200), HttpStatus.OK);
    }

    @PostMapping(value = "/searchUser")
    public ResponseEntity<ResponseDataArray> searchUser(@RequestBody RequestSearchUser reqSearchUser) {
        List<User> user = new ArrayList<>();
        if (reqSearchUser.getIdEmployee() != null) {
            User addedUser = userService.getUsersByEmployeeId(reqSearchUser.getIdEmployee());
            user.add(addedUser);
            return new ResponseEntity<>(new ResponseDataArray(user, 200), HttpStatus.OK);
        }
        boolean flag = false;
        if (reqSearchUser.getDoctype() != 0 && reqSearchUser.getDocument() != null) {
            user = userService.getUsersByDocument(reqSearchUser.getDocument());
            if (user == null) {
                return new ResponseEntity<>(new ResponseDataArray(null, 200), HttpStatus.OK);
            }
            User found = null;
            for (User u : user) {
                if (u.getDoctype() == reqSearchUser.getDoctype()) {
                    found = u;
                    flag = true;
                }
            }
            if (!flag) {
                user.removeAll(user);
                return new ResponseEntity<>(new ResponseDataArray(user, 200), HttpStatus.OK);
            } else {
                user.removeAll(user);
                user.add(found);
                return new ResponseEntity<ResponseDataArray>(new ResponseDataArray(user, 200), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(new ResponseDataArray(user, 200), HttpStatus.OK);
    }

    @GetMapping(value = "/listUsers")
    public ResponseEntity<ResponseDataArray> allUsers() {
        return new ResponseEntity<>(new ResponseDataArray(userService.getUsers(), 200), HttpStatus.OK);
    }

    @PostMapping(value = "/deleteUser")
    public ResponseEntity<ResponseDataBoolean> deleteUser(@RequestBody User user) {
        User us = userService.getUsersByEmail(user.getEmail());
        if (us == null) {
            return new ResponseEntity<>(new ResponseDataBoolean(false, 400), HttpStatus.BAD_REQUEST);
        }
        userService.deleteUser(user.getEmail());
        return new ResponseEntity<>(new ResponseDataBoolean(true, 200), HttpStatus.OK);
    }
}
    class RequestSearchUser {
        private int doctype;
        private String document;
        private Integer idEmployee;

        public int getDoctype() {
            return doctype;
        }

        public void setDoctype(int doctype) {
            this.doctype = doctype;
        }

        public String getDocument() {
            return document;
        }

        public void setDocument(String document) {
            this.document = document;
        }

        public Integer getIdEmployee() {
            return idEmployee;
        }

        public void setIdEmployee(Integer idEmployee) {
            this.idEmployee = idEmployee;
        }
    }
