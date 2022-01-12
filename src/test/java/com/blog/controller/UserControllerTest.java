package com.blog.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import com.blog.Service.serviceImpl.UserServiceImpl;
import com.blog.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
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
}

