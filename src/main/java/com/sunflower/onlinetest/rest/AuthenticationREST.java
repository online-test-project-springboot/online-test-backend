package com.sunflower.onlinetest.rest;

import com.sunflower.onlinetest.entity.UserEntity;
import com.sunflower.onlinetest.rest.mapper.UserMapper;
import com.sunflower.onlinetest.rest.request.LoginRequest;
import com.sunflower.onlinetest.rest.request.SignupRequest;
import com.sunflower.onlinetest.rest.response.JwtDTO;
import com.sunflower.onlinetest.rest.response.ResponseObjectWithJWT;
import com.sunflower.onlinetest.rest.response.UserDTO;
import com.sunflower.onlinetest.service.AuthenticationService;
import com.sunflower.onlinetest.service.JWTAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
        value = "rest/auth",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class AuthenticationREST {

    public static final String SUCCESSFULLY = "successfully";

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserMapper userEntityMapper;

    @Autowired
    private JWTAuthenticationService jwtAuthenticationService;

    @PostMapping("login")
    public ResponseEntity<ResponseObjectWithJWT> login(@RequestBody LoginRequest loginRequest) {
        try {
            UserEntity userEntity = authenticationService.login(loginRequest);
            UserDTO userDTO = userEntityMapper.toUserResponse(userEntity);
            JwtDTO authorizedToken = jwtAuthenticationService.createAuthorizedToken(userEntity);
            ResponseObjectWithJWT responseObject = ResponseObjectWithJWT.builder()
                    .message(SUCCESSFULLY)
                    .data(userDTO)
                    .jwt(authorizedToken)
                    .build();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(responseObject);
        } catch (Exception e) {
            ResponseObjectWithJWT responseObject = ResponseObjectWithJWT.builder()
                    .message(e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(responseObject);
        }
    }

    @PostMapping("signup")
    public ResponseEntity<ResponseObjectWithJWT> signup(@RequestBody SignupRequest signupRequest) {
        try {
            UserEntity userEntity = authenticationService.signup(signupRequest);
            UserDTO userDTO = userEntityMapper.toUserResponse(userEntity);
            JwtDTO authorizedToken = jwtAuthenticationService.createAuthorizedToken(userEntity);
            ResponseObjectWithJWT responseObject = ResponseObjectWithJWT.builder()
                    .message(SUCCESSFULLY)
                    .data(userDTO)
                    .jwt(authorizedToken)
                    .build();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(responseObject);
        } catch (Exception e) {
            ResponseObjectWithJWT responseObject = ResponseObjectWithJWT.builder()
                    .message(e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(responseObject);
        }
    }
}
