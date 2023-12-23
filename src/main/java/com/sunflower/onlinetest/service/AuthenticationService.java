package com.sunflower.onlinetest.service;

import com.sunflower.onlinetest.entity.UserEntity;
import com.sunflower.onlinetest.rest.request.LoginRequest;
import com.sunflower.onlinetest.rest.request.SignupRequest;

public interface AuthenticationService {

    UserEntity login(LoginRequest loginRequest);

    UserEntity signup(SignupRequest signupRequest);
}
