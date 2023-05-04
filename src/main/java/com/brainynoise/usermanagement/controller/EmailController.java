package com.brainynoise.usermanagement.controller;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class EmailController {
    public void emailSender(String destinyEmail, String nameUser, String passwordUser, String template){
        Properties props = new Properties();
        InputStream input = EmailController.class.getClassLoader().getResourceAsStream("email.properties");

        try {
            props.load(input);

            String host = "smtp.office365.com";
            String username = props.getProperty("email.username");
            String password = props.getProperty("email.password");

            String to = destinyEmail;
            String subject = "Test";
            if(template.equals("newUser")){
                subject = "Bienvenido a BrainyNoise";
            } else if(template.equals("resetPassword")){
                subject = "Reestablecimiento de contraseña";
            }

            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.port", "587");

            Authenticator auth = new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            };

            Session session = Session.getInstance(props, auth);
            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(username));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
                message.setSubject(subject);


                InputStream inputStream = EmailController.class.getClassLoader().getResourceAsStream("emails/" + template + ".html");
                String html = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
                html = html.replace("[[name]]", nameUser);
                if(template.equals("resetPassword")){
                    html = html.replace("[[url]]", "http://127.0.0.1:4200/new-password/" + destinyEmail);
                }
                if (passwordUser != null) {
                    html = html.replace("[[password]]", passwordUser);
                }

                message.setContent(html, "text/html; charset=UTF-8");
                Transport.send(message);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
