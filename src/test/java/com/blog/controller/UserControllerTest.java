package com.blog.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import com.blog.Service.serviceImpl.UserServiceImpl;
import com.blog.model.User;
import com.blog.pojo.LoginDto;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.Timestamp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {UserController.class})
@ExtendWith(SpringExtension.class)
class UserControllerTest {
    @Autowired
    private UserController userController;

    @MockBean
    private UserServiceImpl userServiceImpl;

    @Test
    void testGetUser() throws Exception {
        when(this.userServiceImpl.getUserById((Long) any())).thenReturn(new User());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/user/{id}", 123L);
        MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":0,\"userName\":null,\"firstName\":null,\"lastName\":null,\"email\":null,\"password\":null,\"createTime\""
                                        + ":null,\"contact\":null}"));
    }

    @Test
    void testUpdateUser() throws Exception {
        when(this.userServiceImpl.updateUser((User) any())).thenReturn(new User());

        User user = new User();
        user.setContact("Contact");
        user.setCreateTime(new Timestamp(10L));
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(123L);
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        user.setUserName("janedoe");
        String content = (new ObjectMapper()).writeValueAsString(user);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":0,\"userName\":null,\"firstName\":null,\"lastName\":null,\"email\":null,\"password\":null,\"createTime\""
                                        + ":null,\"contact\":null}"));
    }

    @Test
    void testLoginProcess() throws Exception {
        when(this.userServiceImpl.getUser((String) any(), (String) any())).thenReturn(new User());

        LoginDto loginDto = new LoginDto();
        loginDto.setEmail("jane.doe@example.org");
        loginDto.setPassword("iloveyou");
        String content = (new ObjectMapper()).writeValueAsString(loginDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "successfully logged in User(id=0, userName=null, firstName=null, lastName=null, email=null, password=null,"
                                        + " createTime=null, contact=null)"));
    }

    @Test
    void testLoginProcess2() throws Exception {
        when(this.userServiceImpl.getUser((String) any(), (String) any())).thenReturn(null);

        LoginDto loginDto = new LoginDto();
        loginDto.setEmail("jane.doe@example.org");
        loginDto.setPassword("iloveyou");
        String content = (new ObjectMapper()).writeValueAsString(loginDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Email or Password is wrong!!!"));
    }

    @Test
    void testLogout() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/Logout");
        MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("successfully logged out"));
    }

    @Test
    void testLogout2() throws Exception {
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/Logout");
        getResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("successfully logged out"));
    }
}

