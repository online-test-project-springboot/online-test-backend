package com.sunflower.onlinetest.rest.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    @Email
    private String email;

    @NotNull
    private String password;
}
