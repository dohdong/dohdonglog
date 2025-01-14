package com.dohdonglog.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.dohdonglog.domain.Post;
import com.dohdonglog.repository.PostRepository;
import com.dohdonglog.request.PostCreate;
import com.dohdonglog.request.PostEdit;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.regex.Matcher;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


@SpringBootTest
@AutoConfigureMockMvc
class PostControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void clean(){
        postRepository.deleteAll();
    }


    @Test
    @DisplayName("글 작성 요청시 title값은 필수다.")
    void test2() throws Exception {

        //given
        PostCreate request = PostCreate.builder()
                .content("내용입니다.")
                .build();

        String json = objectMapper.writeValueAsString(request);

        //expected
        mockMvc.perform(post("/posts")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.validation.title").value("타이틀을 입력하세요."))
                .andDo(print());
    }

    @Test
    @DisplayName("글 작성 요청시 DB에 값이 저장됨.")
    void test3() throws Exception {
        // before 삭제 는 너무 지저분한 방법.

        //given
        PostCreate request = PostCreate.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        String json = objectMapper.writeValueAsString(request);


        //when
        mockMvc.perform(post("/posts")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(print());
        // then
        assertEquals(1L,postRepository.count());

        Post post = postRepository.findAll().get(0);
        assertEquals("제목입니다.", post.getTitle());
        assertEquals("내용입니다.", post.getContent());
    }

    @Test
    @DisplayName("글 1개 조회")
    void test4() throws Exception {

        // given
        Post post = Post.builder()
                .title("123456789012345")
                .content("bar")
                .build();
        postRepository.save(post);

        // 클라이언트 요구사항
        // json 응답에서 title값 길이를 최대 10글자로 해주세요.


        // expected
        mockMvc.perform(get("/posts/{postId}", post.getId())
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(post.getId()))
                .andExpect(jsonPath("$.title").value("1234567890"))
                .andExpect(jsonPath("$.content").value("bar"))
                .andDo(print());

    }

    @Test
    @DisplayName("글 여러개 조회")
    void test5() throws Exception {

        // given
        List<Post> requestPosts = IntStream.range(1,31)
                .mapToObj(i -> {
                    return Post.builder()
                            .title("호돌맨 제목 " + i)
                            .content("반포자이 " + i)
                            .build();
                })
                .collect(Collectors.toList());
        postRepository.saveAll(requestPosts);


        // expected
        mockMvc.perform(get("/posts?page=1&size=10")
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()",Matchers.is(10)))
                .andExpect(jsonPath("$[0].title").value("호돌맨 제목 30"))
                .andExpect(jsonPath("$[0].content").value("반포자이 30"))
                .andDo(print());

    }

    @Test
    @DisplayName("페이지를 0으로 요청하면 첫 페이지를 가져온다.")
    void test6() throws Exception {

        // given
        List<Post> requestPosts = IntStream.range(1,31)
                .mapToObj(i -> {
                    return Post.builder()
                            .title("호돌맨 제목 " + i)
                            .content("반포자이 " + i)
                            .build();
                })
                .collect(Collectors.toList());
        postRepository.saveAll(requestPosts);


        // expected
        mockMvc.perform(get("/posts?page=0&size=10")
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()",Matchers.is(10)))
                .andExpect(jsonPath("$[0].title").value("호돌맨 제목 30"))
                .andExpect(jsonPath("$[0].content").value("반포자이 30"))
                .andDo(print());

    }

    @Test
    @DisplayName("글 제목 수정")
    void test7() throws Exception {

        // given
        Post post = Post.builder()
                .title("호돌맨")
                .content("반포자이")
                .build();

        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title("호돌걸")
                .content("반포자이")
                .build();


        // expected
        mockMvc.perform(patch("/posts/{postId}", post.getId() )
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postEdit))
                )
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    @DisplayName("게시글 삭제")
    void test8() throws Exception {

        // given
        Post post = Post.builder()
                .title("호돌맨")
                .content("반포자이")
                .build();

        postRepository.save(post);


        // expected
        mockMvc.perform(delete("/posts/{postId}", post.getId() )
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    @DisplayName("존재하지 않는 게시글 조회")
    void test9() throws Exception {
        // expected
        mockMvc.perform(delete("/posts/{postId}", 1L )
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isNotFound())
                .andDo(print());


    }

    @Test
    @DisplayName("존재하지 않는 게시글 수정")
    void test10() throws Exception {


        PostEdit postEdit = PostEdit.builder()
                .title("호돌걸")
                .content("반포자이")
                .build();


        // expected
        mockMvc.perform(patch("/posts/{postId}", 1L )
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postEdit))
                )
                .andExpect(status().isNotFound())
                .andDo(print());



    }


    @Test
    @DisplayName("게시글 작성시 제목에 '바보'는 포함될 수 없다.")
    void test11() throws Exception {

        //given
        PostCreate request = PostCreate.builder()
                .title("나는 바보입니다.")
                .content("반포자이.")
                .build();

        String json = objectMapper.writeValueAsString(request);


        //when
        mockMvc.perform(post("/posts")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isBadRequest())
                .andDo(print());

      }


}



// API 문서 생성

// GET /posts/{postId} -> 단건조회
// POST /posts -> 게시글 등록

// 클라이언트 입장에서는 어떤 API 있는지 모름

// Spring RestDocs
// - 운영코드에 영향x
// - 코드수정 -> 문서를 수정x -> 코드랑 문서 다른 문제있음.
// - Test 케이스 실행 -> 문서를 생성해준다.


