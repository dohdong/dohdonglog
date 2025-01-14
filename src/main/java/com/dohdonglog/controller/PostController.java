package com.dohdonglog.controller;

import com.dohdonglog.config.data.UserSession;
import com.dohdonglog.exception.InvalidRequest;
import com.dohdonglog.request.PostCreate;
import com.dohdonglog.request.PostEdit;
import com.dohdonglog.response.PostResponse;
import com.dohdonglog.request.PostSearch;
import com.dohdonglog.service.PostService;
import com.sun.jdi.request.InvalidRequestStateException;
import jakarta.persistence.PostUpdate;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;


    @GetMapping("/foo")
    public Long foo(UserSession userSession){
        log.info(">>> {}", userSession.id);
        return userSession.id;
    }

    @GetMapping("/bar")
    public String bar(){ // UserSession 이 있으면 인증이 필요한 것.
        return "인증이 필요없는 페이지";
    }


    @PostMapping("/posts")
    public void post(@RequestBody @Valid PostCreate request) {
        request.validate();
        postService.write(request);



    }

    @GetMapping("/posts/{postId}")
    public PostResponse get(@PathVariable(name="postId") Long postId) {
        return postService.get(postId);

    }


    @GetMapping("/posts")
    public List<PostResponse> getList(@ModelAttribute PostSearch postSearch){
        return postService.getList(postSearch);
    }

    @PatchMapping("/posts/{postId}")
    public void edit(@PathVariable Long postId, @RequestBody @Valid PostEdit request) {
        postService.edit(postId, request);
    }

    @DeleteMapping("/posts/{postId}")
    public void delete(@PathVariable Long postId) {
        postService.delete(postId);
    }





}
