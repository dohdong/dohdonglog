package com.dohdonglog.controller;

import com.dohdonglog.domain.Post;
import com.dohdonglog.request.PostCreate;
import com.dohdonglog.response.PostResponse;
import com.dohdonglog.service.PostService;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;



    @PostMapping("/posts")
    public void post(@RequestBody @Valid PostCreate request) {
        postService.write(request);

    }

    @GetMapping("/posts/{postId}")
    public PostResponse get(@PathVariable(name="postId") Long id) {
        PostResponse response = postService.get(id);
        // 응답 클래스 분리!
        return response;
    }





}
