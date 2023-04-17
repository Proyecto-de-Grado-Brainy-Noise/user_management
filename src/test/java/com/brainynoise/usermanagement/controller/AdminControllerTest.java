package com.brainynoise.usermanagement.controller;

import com.brainynoise.usermanagement.repository.TokenRepository;
import com.brainynoise.usermanagement.requests.UserRequest;
import com.brainynoise.usermanagement.service.AuthenticationService;
import com.brainynoise.usermanagement.service.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@WithMockUser("spring")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AdminControllerTest {
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
    @Order(1)
    void allUsersTest() throws Exception {
        MvcResult responseEntity = mvc.perform(get("/api/admin/listUsers").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        System.out.println(responseEntity.getResponse().getContentAsString());
    }

    @Test
    @Order(2)
    void searchUserByIdOkTest() throws Exception {
        RequestSearchUser requestSearchUser = new RequestSearchUser();
        requestSearchUser.setIdEmployee(1);
        MvcResult responseEntity = mvc.perform(post("/api/admin/searchUser").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(requestSearchUser)))
                .andExpect(status().isOk()).andReturn();
        System.out.println(responseEntity.getResponse().getContentAsString());
    }

    @Test
    @Order(3)
    void searchUserByDocOkTest() throws Exception {
        RequestSearchUser requestSearchUser = new RequestSearchUser();
        requestSearchUser.setDoctype(1);
        requestSearchUser.setDocument("11111111");
        MvcResult responseEntity = mvc.perform(post("/api/admin/searchUser").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(requestSearchUser)))
                .andExpect(status().isOk()).andReturn();
        System.out.println(responseEntity.getResponse().getContentAsString());
    }

    @Test
    @Order(4)
    void searchUserByIdBadTest() throws Exception {
        RequestSearchUser requestSearchUser = new RequestSearchUser();
        requestSearchUser.setIdEmployee(354444);
        MvcResult responseEntity = mvc.perform(post("/api/admin/searchUser").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(requestSearchUser)))
                .andExpect(status().isOk()).andReturn();
        System.out.println(responseEntity.getResponse().getContentAsString());
    }

    @Test
    @Order(5)
    void searchUserByDocBadTest() throws Exception {
        RequestSearchUser requestSearchUser = new RequestSearchUser();
        requestSearchUser.setDoctype(1);
        requestSearchUser.setDocument("87348734");
        MvcResult responseEntity = mvc.perform(post("/api/admin/searchUser").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(requestSearchUser)))
                .andExpect(status().isOk()).andReturn();
        System.out.println(responseEntity.getResponse().getContentAsString());
    }
    @Test
    @Order(6)
    void updateUserTest() throws Exception {
        UserRequest user = new UserRequest("j.naizaque@javeriana.edu.co", "A", "Jane", "", "Doe", "", 1, "22222222", "2001-01-01", 2, "Enfermero", "Endocrino", "OK", "2023-04-07");
        MvcResult responseEntity = mvc.perform(post("/api/admin/updateUser").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isOk()).andReturn();
        System.out.println(responseEntity.getResponse().getContentAsString());
    }

    @Test
    @Order(7)
    void updateUserEmailBadTest() throws Exception {
        UserRequest user = new UserRequest("jess@gmail.com", "A", "Jessica", "Tatiana", "Naizaque", "", 1, "87348734", "2001-01-01", 12345678, "Jobbb", "Areaaa", "OK", "2023-04-07");
        MvcResult responseEntity = mvc.perform(post("/api/admin/updateUser").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isBadRequest()).andReturn();
        System.out.println(responseEntity.getResponse().getContentAsString());
    }

    @Test
    @Order(8)
    void deleteUserOkTest() throws Exception {
        UserRequest user = new UserRequest("test@mail.com", "U", "Test", "", "Test", "", 1, "178772", "2001-01-01", 112112, "Test", "Test", "Test", "");
        MvcResult responseEntity = mvc.perform(post("/api/admin/deleteUser").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isOk()).andReturn();
        System.out.println(responseEntity.getResponse().getContentAsString());
    }

    @Test
    @Order(9)
    void deleteUserBadTest() throws Exception {
        UserRequest user = new UserRequest("test@mail.com", "U", "Test", "", "Test", "", 1, "178772", "2001-01-01", 112112, "Test", "Test", "Test", "");
        MvcResult responseEntity = mvc.perform(post("/api/admin/deleteUser").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isBadRequest()).andReturn();
        System.out.println(responseEntity.getResponse().getContentAsString());
    }
}