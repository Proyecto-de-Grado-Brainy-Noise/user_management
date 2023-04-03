package com.brainynoise.usermanagement.controller;

import com.brainynoise.usermanagement.entity.User;
import com.brainynoise.usermanagement.requests.EmailRequest;
import com.brainynoise.usermanagement.requests.ResetPasswordRequest;
import com.brainynoise.usermanagement.responses.ResponseDataString;
import com.brainynoise.usermanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class ResetPasswordController {
    @Autowired
    private UserService userService;

    @PostMapping(value = "reset-password")
    public ResponseEntity<ResponseDataString> sendEmailResPwd(@RequestBody EmailRequest request){

        User user;
        user = userService.getUsersByEmail(request.getEmail());
        if (user == null){
            return new ResponseEntity<>(new ResponseDataString("El usuario con email " + request.getEmail() + " no existe", 400), HttpStatus.BAD_REQUEST);
        } else {
            EmailController emailController = new EmailController();
            emailController.emailSender(request.getEmail(), user.getName(), null, "resetPassword");
            return new ResponseEntity<>(new ResponseDataString("Correo enviado para el cambio de contraseña", 200), HttpStatus.OK);
        }
    }

    /*@PostMapping(value = "respwd")
    public ResponseEntity<ResponseDataString> resetPassword(@RequestBody ResetPasswordRequest request){
        User user;
        user = userService.getUsersByEmail(request.getEmail());
        if (user == null){
            return new ResponseEntity<>(new ResponseDataString("El usuario con email " + request.getEmail() + " no existe", 400), HttpStatus.BAD_REQUEST);
        } else {
            PasswordController passwordController = new PasswordController(credentialService);
            passwordController.savePassword(request.getEmail(), request.getPassword());
            return new ResponseEntity<>(new ResponseDataString("Contraseña actualizada", 200), HttpStatus.OK);
        }
    }*/

}
