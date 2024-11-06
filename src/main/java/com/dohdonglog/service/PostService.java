package com.dohdonglog.service;

import com.dohdonglog.domain.Post;
import com.dohdonglog.repository.PostRepository;
import com.dohdonglog.request.PostCreate;
import com.dohdonglog.response.PostResponse;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public void write(PostCreate postCreate) {

        Post post = Post.builder()
                .title(postCreate.getTitle())
                .content(postCreate.getContent())
                .build();

        postRepository.save(post);
    }

    public PostResponse get(Long id){
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));

        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();

    }

    public List<PostResponse> getList(int page) {
        Pageable pageable = PageRequest.of(page, 5);
        return postRepository.findAll(pageable).stream()
                .map(post -> new PostResponse(post))
                .collect(Collectors.toList());
    }


    // 글이 너무 많은 경우 -> 비용이 많이 든다.
    // db글 모두 조회하는 경우 -> db가 뻗을수도 있다.



}
