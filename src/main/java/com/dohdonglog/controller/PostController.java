package com.dohdonglog.controller;

import com.dohdonglog.request.PostCreate;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class PostController {

    //SSR -> jsp, thymeleaf,mustache
    //SPA -> vue , react

    //HTTP Method

    @PostMapping("/posts")
    public String post(PostCreate params) {
        log.info("params={}", params.toString());
        return "Hello World";
    }

}
