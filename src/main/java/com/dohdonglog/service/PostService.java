package com.dohdonglog.service;

import com.dohdonglog.domain.Post;
import com.dohdonglog.domain.PostEditor;
import com.dohdonglog.repository.PostRepository;
import com.dohdonglog.request.PostCreate;
import com.dohdonglog.request.PostEdit;
import com.dohdonglog.response.PostResponse;
import com.dohdonglog.request.PostSearch;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public List<PostResponse> getList(PostSearch postSearch) {
        return postRepository.getList(postSearch).stream()
                .map(post -> new PostResponse(post))
                .collect(Collectors.toList());
    }


    // 글이 너무 많은 경우 -> 비용이 많이 든다.
    // db글 모두 조회하는 경우 -> db가 뻗을수도 있다.


    @Transactional
    public PostResponse edit(Long id, PostEdit postEdit){
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));

//        post.change(postEdit.getTitle(),postEdit.getContent());

        PostEditor.PostEditorBuilder editorBuilder = post.toEditor();

        PostEditor postEditor = editorBuilder.title(postEdit.getTitle())
                .content(postEdit.getContent())
                .build();

//        if (postEdit.getTitle() != null){
//            editorBuilder.title(postEdit.getTitle());
//        }

//        if(postEdit.getContent() != null){
//            editorBuilder.content(postEdit.getContent());
//        }

        post.edit(postEditor);

        return new PostResponse(post);

    }

}
