package com.sunflower.onlinetest.rest.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TopicRequest {
    @NotNull
    private String name;

    private String description;
}
