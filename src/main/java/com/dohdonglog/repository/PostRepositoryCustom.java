package com.dohdonglog.repository;

import com.dohdonglog.domain.Post;
import com.dohdonglog.request.PostSearch;
import java.util.List;

public interface PostRepositoryCustom {

    List<Post> getList(PostSearch postSearch);

}
