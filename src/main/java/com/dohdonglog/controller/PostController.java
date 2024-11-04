package com.dohdonglog.controller;

import com.dohdonglog.request.PostCreate;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class PostController {

    //SSR -> jsp, thymeleaf,mustache
    //SPA -> vue , react

    //HTTP Method

    @PostMapping("/posts")
    public String post(@RequestBody PostCreate params) {
        log.info("params={}", params.toString());

        String title = params.getTitle();
        if(title == null || title.equals("")) {
            try {
                throw new Exception("title is empty");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        String content = params.getContent();
        if(content == null || content.equals("")) {}

        return "Hello World";
    }

}
