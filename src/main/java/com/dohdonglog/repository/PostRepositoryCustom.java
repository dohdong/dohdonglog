package com.dohdonglog.repository;

import com.dohdonglog.domain.Post;
import java.util.List;

public interface PostRepositoryCustom {

    List<Post> getList(int page);

}
