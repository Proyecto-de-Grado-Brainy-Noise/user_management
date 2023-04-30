package com.brainynoise.usermanagement.controller;

import com.brainynoise.usermanagement.requests.EmailRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ResetPasswordControllerTest {

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
    void sendEmailResPwdOkTest() throws Exception {
        EmailRequest request = new EmailRequest("j.naizaque@javeriana.edu.co", "j.naizaque@javeriana.edu.co");
        MvcResult responseEntity = mvc.perform(post("/api/reset-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk()).andReturn();
        System.out.println(responseEntity.getResponse().getContentAsString());
    }

    @Test
    void sendEmailResPwdBadTest() throws Exception {
        EmailRequest request = new EmailRequest("j_nzq@javeriana.edu.co", "j_nzq@javeriana.edu.co");
        MvcResult responseEntity = mvc.perform(post("/api/reset-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isBadRequest()).andReturn();
        System.out.println(responseEntity.getResponse().getContentAsString());
    }
}