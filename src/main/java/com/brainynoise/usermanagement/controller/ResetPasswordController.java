package com.brainynoise.usermanagement.controller;

import com.brainynoise.usermanagement.entity.User;
import com.brainynoise.usermanagement.requests.CodeRequest;
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
@RequestMapping("/api")
public class ResetPasswordController {
    @Autowired
    private UserService userService;

    @PostMapping(value = "respwd")
    public ResponseEntity<ResponseDataString> resetPassword(@RequestBody ResetPasswordRequest request){
        User us = userService.getUsersByEmail(request.getEmail());
        if (us == null) {
            return new ResponseEntity<>(new ResponseDataString("El usuario con email " + request.getEmail() + " no existe", 400), HttpStatus.BAD_REQUEST);
        }
        us = userService.updatePassword(request, us);
        if (us == null) {
            return new ResponseEntity<>(new ResponseDataString("Hubo un error al actualizar la contraseña", 400), HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(new ResponseDataString("Su contraseña se ha actualizado exitosamente", 200), HttpStatus.OK);
        }
    }

    public String generateCode(String email) {
        String code = "";
        for (int i = 0; i < 6; i++) {
            code += (int) (Math.random() * 9);
        }
        userService.updateCode(email, code);
        return code;
    }

    @PostMapping(value = "sendcode")
    public ResponseEntity<ResponseDataString> sendCodeEmail(@RequestBody EmailRequest request){
        User user = userService.getUsersByEmail(request.getEmail());
        if (user == null){
            return new ResponseEntity<>(new ResponseDataString("El usuario con email " + request.getEmail() + " no existe", 400), HttpStatus.BAD_REQUEST);
        } else {
            EmailController emailController = new EmailController();
            String code = generateCode(user.getEmail());
            emailController.emailSender(request.getEmail(), user.getName(), code, "codeResetPwd");
            return new ResponseEntity<>(new ResponseDataString("Se envió un correo con un código de verificación para el cambio de contraseña", 200), HttpStatus.OK);
        }
    }

    @PostMapping(value = "checkcode")
    public ResponseEntity<ResponseDataString> checkCode(@RequestBody CodeRequest request){
        User user = userService.getUsersByEmail(request.getEmail());
        if (user == null){
            return new ResponseEntity<>(new ResponseDataString("El usuario con email " + request.getEmail() + " no existe", 400), HttpStatus.BAD_REQUEST);
        } else {
            if (user.getCode().equals(request.getCode())) {
                return new ResponseEntity<>(new ResponseDataString("El código es correcto", 200), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ResponseDataString("El código es incorrecto", 400), HttpStatus.BAD_REQUEST);
            }
        }
    }
}
