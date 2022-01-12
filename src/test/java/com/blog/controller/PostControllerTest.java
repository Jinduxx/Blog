package com.blog.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;

import com.blog.Service.serviceImpl.PostServiceImpl;
import com.blog.model.Post;
import com.blog.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

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

    @Test
    void testDeletePost() throws Exception {
        when(this.postServiceImpl.deletePost(anyLong())).thenReturn("Delete Post");
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/post/{id}", 123L);
        MockMvcBuilders.standaloneSetup(this.postController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Delete Post"));
    }

    @Test
    void testMakePost() throws Exception {
        Post post = new Post();
        post.setBody("Not all who wander are lost");
        post.setId(123L);
        post.setName("Name");
        post.setTitle("Dr");
        post.setUser(new User());
        post.setUserId(123L);
        String content = (new ObjectMapper()).writeValueAsString(post);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(this.postController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("redirect:/"));
    }

    @Test
    void testShowAllFavouritePost() throws Exception {
        when(this.postServiceImpl.getAllFavouritePost((com.blog.model.User) any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/favouritePost");
        MockMvcBuilders.standaloneSetup(this.postController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testShowAllFavouritePost2() throws Exception {
        when(this.postServiceImpl.getAllFavouritePost((com.blog.model.User) any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/favouritePost");
        getResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(this.postController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testShowAllPost() throws Exception {
        when(this.postServiceImpl.getAllPost((com.blog.model.User) any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/allPost");
        MockMvcBuilders.standaloneSetup(this.postController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testShowAllPost2() throws Exception {
        when(this.postServiceImpl.getAllPost((com.blog.model.User) any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/allPost");
        getResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(this.postController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testUpdatePost() throws Exception {
        Post post = new Post();
        post.setBody("Not all who wander are lost");
        post.setId(123L);
        post.setName("Name");
        post.setTitle("Dr");
        post.setUser(new User());
        post.setUserId(123L);
        String content = (new ObjectMapper()).writeValueAsString(post);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(this.postController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("redirect:/"));
    }
}

