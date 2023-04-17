package com.brainynoise.usermanagement.controller;

import com.brainynoise.usermanagement.requests.AuthenticationRequest;
import com.brainynoise.usermanagement.requests.RegisterRequest;
import com.brainynoise.usermanagement.requests.ResetPasswordRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AuthenticationControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @BeforeAll
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser("spring")
    @Order(1)
    void registerOkTest() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest("j.naizaque@javeriana.edu.co", "A", "Jessica", "", "Naizaque", "", 1, "22222222", "2001-01-01", 2, "General", "Medicina", "OK", "2023-04-07", "");
        MvcResult responseEntity = mvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(registerRequest)))
                .andExpect(status().isOk()).andReturn();
        System.out.println(responseEntity.getResponse().getContentAsString());
    }

    @Test
    @WithMockUser("spring")
    @Order(2)
    void registerExistEmailTest() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest("j.naizaque@javeriana.edu.co", "A", "Jessica", "", "Naizaque", "", 1, "335665", "2001-01-01", 64332, "Jobbb", "Areaaa", "OK", "2023-04-07", "");
        MvcResult responseEntity = mvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(registerRequest)))
                .andExpect(status().isBadRequest()).andReturn();
        System.out.println(responseEntity.getResponse().getContentAsString());
    }
    @Test
    @WithMockUser("spring")
    @Order(3)
    void registerExistIdTest() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest("jess@gmail.com", "A", "Jessica", "Tatiana", "Naizaque", "", 1, "55522233", "2001-01-01", 2, "Jobbb", "Areaaa", "OK", "2023-04-07", "");
        MvcResult responseEntity = mvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(registerRequest)))
                .andExpect(status().isBadRequest()).andReturn();
        System.out.println(responseEntity.getResponse().getContentAsString());
    }

    @Test
    @WithMockUser("spring")
    @Order(4)
    void registerExistDocTest() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest("j_nzq@gmail.com", "A", "Jessica", "Tatiana", "Naizaque", "", 1, "22222222", "2001-01-01", 5652322, "Jobbb", "Areaaa", "OK", "2023-04-07", "");
        MvcResult responseEntity = mvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(registerRequest)))
                .andExpect(status().isBadRequest()).andReturn();
        System.out.println(responseEntity.getResponse().getContentAsString());
    }

    @Test
    @Order(5)
    void resetPasswordOkTest() throws Exception {
        ResetPasswordRequest request = new ResetPasswordRequest("j.naizaque@javeriana.edu.co", "password123");
        MvcResult responseEntity = mvc.perform(post("/api/respwd")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk()).andReturn();
        System.out.println(responseEntity.getResponse().getContentAsString());
    }

    @Test
    @Order(6)
    void resetPasswordBadTest() throws Exception {
        ResetPasswordRequest request = new ResetPasswordRequest("jnaiz@javeriana.edu.co", "password123");
        MvcResult responseEntity = mvc.perform(post("/api/respwd")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isBadRequest()).andReturn();
        System.out.println(responseEntity.getResponse().getContentAsString());
    }

    @Test
    @Order(7)
    void authenticateOkTest() throws Exception {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest("j.naizaque@javeriana.edu.co", "password123");
        MvcResult responseEntity = mvc.perform(post("/api/auth/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(authenticationRequest)))
                .andExpect(status().isOk()).andReturn();
        System.out.println(responseEntity.getResponse().getContentAsString());
    }

    @Test
    @Order(6)
    void authenticateInvalidEmailTest() throws Exception {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest("j.nzq@javeriana.edu.co", "password123");
        MvcResult responseEntity = mvc.perform(post("/api/auth/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(authenticationRequest)))
                .andExpect(status().isForbidden()).andReturn();
        System.out.println(responseEntity.getResponse().getContentAsString());
    }

    @Test
    @Order(7)
    void authenticateInvalidPwdTest() throws Exception {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest("j.naizaque@javeriana.edu.co", "BADPWD");
        MvcResult responseEntity = mvc.perform(post("/api/auth/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(authenticationRequest)))
                .andExpect(status().isForbidden()).andReturn();
        System.out.println(responseEntity.getResponse().getContentAsString());
    }

}