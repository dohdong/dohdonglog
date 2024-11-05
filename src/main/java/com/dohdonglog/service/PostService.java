package com.dohdonglog.service;

import com.dohdonglog.domain.Post;
import com.dohdonglog.repository.PostRepository;
import com.dohdonglog.request.PostCreate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public void write(PostCreate postCreate) {

        Post post = new Post(postCreate.getTitle(), postCreate.getContent());

        postRepository.save(post);
    }

}
