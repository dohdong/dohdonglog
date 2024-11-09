package com.dohdonglog.service;

import static org.junit.jupiter.api.Assertions.*;

import com.dohdonglog.domain.Post;
import com.dohdonglog.exception.PostNotFound;
import com.dohdonglog.repository.PostRepository;
import com.dohdonglog.request.PostCreate;
import com.dohdonglog.request.PostEdit;
import com.dohdonglog.response.PostResponse;
import com.dohdonglog.request.PostSearch;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void clean(){
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("글 작성")
    void test1(){
        // given
        PostCreate postCreate = PostCreate.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        // when
        postService.write(postCreate);

        // then
        assertEquals(1L,postRepository.count());

        Post post = postRepository.findAll().get(0);
        assertEquals("제목입니다.", post.getTitle());
        assertEquals("내용입니다.", post.getContent());

    }

    @Test
    @DisplayName("글 1개 조회")
    void test2(){

        // given
        Post requestPost = Post.builder()
                .title("foo")
                .content("bar")
                .build();
        postRepository.save(requestPost);

//        Long postId = 1L;

        // when
        PostResponse response = postService.get(requestPost.getId());

        // then
        assertNotNull(response);
        assertEquals(1L,postRepository.count());
        assertEquals("foo", response.getTitle());
        assertEquals("bar", response.getContent());


    }

    @Test
    @DisplayName("글 1페이지 조회")
    void test3(){

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

//        Pageable pageable = PageRequest.of(0,5, Direction.DESC, "id");

        PostSearch postSearch = PostSearch.builder()
                .page(1)
                .build();

        // when
        List<PostResponse> posts = postService.getList(postSearch);

        // then
        assertEquals(10L,posts.size());
        assertEquals("호돌맨 제목 30",posts.get(0).getTitle());


    }

    @Test
    @DisplayName("글 제목 수정")
    void test4(){

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

        // when
        postService.edit(post.getId(),postEdit);

        // then
        Post changePost = postRepository.findById(post.getId())
                .orElseThrow(() -> new RuntimeException("글이 존재하지 않습니다. id =" + post.getId()));

        assertEquals("호돌걸", changePost.getTitle());
        assertEquals("반포자이", changePost.getContent());

    }

    @Test
    @DisplayName("글 내용 수정")
    void test5(){

        // given
        Post post = Post.builder()
                .title("호돌맨")
                .content("반포자이")
                .build();

        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title(null)
                .content("초가집")
                .build();

        // when
        postService.edit(post.getId(),postEdit);

        // then
        Post changePost = postRepository.findById(post.getId())
                .orElseThrow(() -> new RuntimeException("글이 존재하지 않습니다. id =" + post.getId()));
        assertEquals("호돌맨", changePost.getTitle());
        assertEquals("초가집", changePost.getContent());

    }

    @Test
    @DisplayName("게시글 삭제")
    void test6(){

        // given
        Post post = Post.builder()
                .title("호돌맨")
                .content("반포자이")
                .build();

        postRepository.save(post);




        // when
        postService.delete(post.getId());

        // then
        assertEquals(0,postRepository.count());




    }


    @Test
    @DisplayName("글 1개 조회-존재하지 않는 글")
    void test7 (){

        // given
        Post post = Post.builder()
                .title("호돌맨")
                .content("반포자이")
                .build();
        postRepository.save(post);

//        Long postId = 1L;

        // when
        assertThrows(PostNotFound.class, ()-> {
            postService.get(post.getId()+1L);
        });

//        Assertions.assertEquals("존재하지 않는 글입니다.", e.getMessage());
        // then

    }

    @Test
    @DisplayName("게시글 삭제- 존재하지 않는글")
    void test8(){

        // given
        Post post = Post.builder()
                .title("호돌맨")
                .content("반포자이")
                .build();

        postRepository.save(post);


        // then
        assertThrows(PostNotFound.class, ()-> {
            postService.delete(post.getId()+1L);
        });




    }


    @Test
    @DisplayName("글 내용 수정 - 존재하지 않는 글")
    void test9(){

        // given
        Post post = Post.builder()
                .title("호돌맨")
                .content("반포자이")
                .build();

        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title("호돌걸")
                .content("초가집")
                .build();


        // expected
        assertThrows(PostNotFound.class, ()-> {
            postService.edit(post.getId()+1L, postEdit);
        });
    }

}