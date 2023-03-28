package com.brainynoise.usermanagement.controller;

import com.brainynoise.usermanagement.entity.Credential;
import com.brainynoise.usermanagement.entity.User;
import com.brainynoise.usermanagement.service.CredentialService;
import com.brainynoise.usermanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    private UserService userService;

    @Autowired
    private CredentialService credentialService;

    public String generatePassword(int n) {
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
    @PostMapping(value = "/saveUser")
    public ResponseEntity<ResponseDataString> saveUser(@RequestBody User user) {
        User temp = userService.getUsersByEmail(user.getEmail());
        if (temp != null) {
            return new ResponseEntity<>(new ResponseDataString("El usuario con email " + user.getEmail() + " ya existe", 400), HttpStatus.BAD_REQUEST);
        }
        temp = userService.getUsersByEmployeeId(user.getIdEmployee());
        if (temp != null) {
            return new ResponseEntity<>(new ResponseDataString("El usuario con id de empleado " + user.getIdEmployee() + " ya existe", 400), HttpStatus.BAD_REQUEST);
        }
        List<User> temp2 = userService.getUsersByDocument(user.getDocument());
        boolean flag = false;
        for (User u : temp2) {
            if (u.getDoctype() == user.getDoctype()) {
                flag = true;
            }
        }
        if (flag) {
            return new ResponseEntity<>(new ResponseDataString("El usuario con el documento ingresado ya existe", 400), HttpStatus.BAD_REQUEST);
        }
        userService.saveUser(user);
        String password = generatePassword(8);
        PasswordController passwordController = new PasswordController(credentialService);
        passwordController.savePassword(user.getEmail(), password);
        EmailController emailController = new EmailController();
        emailController.emailSender(user.getEmail(), user.getName(), password, "newUser");
        return new ResponseEntity<>(new ResponseDataString("Usuario creado exitosamente", 200), HttpStatus.OK);
    }

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
        List<User> user = null;
        if (reqSearchUser.getIdEmployee() != null) {
            user.add(userService.getUsersByEmployeeId(reqSearchUser.getIdEmployee()));
            return new ResponseEntity<>(new ResponseDataArray(user, 200), HttpStatus.OK);
        }
        boolean flag = false;
        if (reqSearchUser.getDoctype() != 0 && reqSearchUser.getDocument() != null) {
            user = userService.getUsersByDocument(reqSearchUser.getDocument());
            if (user == null) {
                return new ResponseEntity<>(new ResponseDataArray(user, 200), HttpStatus.OK);
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
                return new ResponseEntity<>(new ResponseDataArray(user, 200), HttpStatus.OK);
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
        private BigInteger idEmployee;

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

        public BigInteger getIdEmployee() {
            return idEmployee;
        }

        public void setIdEmployee(BigInteger idEmployee) {
            this.idEmployee = idEmployee;
        }
    }
