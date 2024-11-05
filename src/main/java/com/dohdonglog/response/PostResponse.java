package com.dohdonglog.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PostResponse {

    private final Long id;
    private final String title;
    private final String content;

}
