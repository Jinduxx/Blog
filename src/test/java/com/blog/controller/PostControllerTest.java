package com.blog.controller;

import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;

import com.blog.Service.serviceImpl.PostServiceImpl;
import com.blog.model.Post;
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

@ContextConfiguration(classes = {PostController.class})
@ExtendWith(SpringExtension.class)
class PostControllerTest {
    @Autowired
    private PostController postController;

    @MockBean
    private PostServiceImpl postServiceImpl;

    @Test
    void testAddFavouritePost() throws Exception {
        when(this.postServiceImpl.getPostById(anyLong())).thenReturn(new Post());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/post/{postId}", 123L);
        MockMvcBuilders.standaloneSetup(this.postController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("redirect:/"));
    }
}

