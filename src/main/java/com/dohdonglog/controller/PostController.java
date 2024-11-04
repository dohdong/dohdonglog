package com.dohdonglog.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController {

    //SSR -> jsp, thymeleaf,mustache
    //SPA -> vue , react

    @GetMapping("/posts")
    public String get() {
        return "Hello World";
    }

}
