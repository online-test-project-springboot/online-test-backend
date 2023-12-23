package com.sunflower.onlinetest.rest;

import com.sunflower.onlinetest.rest.mapper.ResultMapper;
import com.sunflower.onlinetest.service.JWTAuthenticationService;
import com.sunflower.onlinetest.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
        value = "rest/results",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class ResultREST {

    @Autowired
    private JWTAuthenticationService jwtAuthenticationService;

    @Autowired
    private ResultService resultService;

    @Autowired
    private ResultMapper resultMapper;

    // TODO implement CRUD for admin
}
