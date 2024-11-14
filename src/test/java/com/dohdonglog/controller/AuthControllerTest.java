package com.dohdonglog.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.dohdonglog.domain.User;
import com.dohdonglog.repository.SessionRepository;
import com.dohdonglog.repository.UserRepository;
import com.dohdonglog.request.Login;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @BeforeEach
    void clean() {
        userRepository.deleteAll();
    }


    @Test
    @DisplayName("로그인 성공 ")
    void test1() throws Exception {
        // given
        userRepository.save(User.builder()
                .name("성빈")
                .email("dohdong@gmail.com")
                .password("1234").build());

        Login login = Login.builder()
                .email("dohdong@gmail.com")
                .password("1234").build();

        // expected
        mockMvc.perform(post("/auth/login")
                        .content(objectMapper.writeValueAsString(login))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Transactional
    @DisplayName("로그인 성공 후 세션 1개 성공")
    void test2() throws Exception {
        // given
        User user = userRepository.save(User.builder()
                .name("성빈")
                .email("dohdong@gmail.com")
                .password("1234").build());

        Login login = Login.builder()
                .email("dohdong@gmail.com")
                .password("1234").build();

        // expected
        mockMvc.perform(post("/auth/login")
                        .content(objectMapper.writeValueAsString(login))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

        User loggedInUser = userRepository.findById(user.getId())
                        .orElseThrow(RuntimeException::new);

        Assertions.assertEquals(1L, loggedInUser.getSessions().size());
    }

    @Test
    @Transactional
    @DisplayName("로그인 성공 후 세션 응답")
    void test3() throws Exception {
        // given
        User user = userRepository.save(User.builder()
                .name("성빈")
                .email("dohdong@gmail.com")
                .password("1234").build());

        Login login = Login.builder()
                .email("dohdong@gmail.com")
                .password("1234").build();

        // expected
        mockMvc.perform(post("/auth/login")
                        .content(objectMapper.writeValueAsString(login))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken", Matchers.notNullValue()))
                .andDo(print());



    }



}