package com.dohdonglog.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class PostCreate {

    @NotBlank
    private String title;
    @NotBlank
    private String content;


}
