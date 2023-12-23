package com.sunflower.onlinetest.rest;

import com.sunflower.onlinetest.rest.mapper.TopicMapper;
import com.sunflower.onlinetest.rest.request.TopicRequest;
import com.sunflower.onlinetest.rest.response.ResponseObject;
import com.sunflower.onlinetest.rest.response.TopicDTO;
import com.sunflower.onlinetest.service.JWTAuthenticationService;
import com.sunflower.onlinetest.service.TopicService;
import com.sunflower.onlinetest.util.CustomBase64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(
        value = "rest/topics",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class TopicREST {

    @Autowired
    private JWTAuthenticationService jwtAuthenticationService;

    @Autowired
    private TopicService topicService;

    @Autowired
    private TopicMapper topicMapper;

    @GetMapping
    public ResponseEntity getAllByUserId(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
        try {
            jwtAuthenticationService.checkAuthorizedToken(authorization);
            List<TopicDTO> topicDTOS = topicMapper.entityToDTOs(topicService.getAllByUserId(jwtAuthenticationService.getUserId(authorization)));
            ResponseObject responseObject = ResponseObject.builder()
                    .message("successfully")
                    .data(topicDTOS)
                    .build();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(responseObject);
        } catch (Exception e) {
            ResponseObject responseObject = ResponseObject.builder()
                    .message(e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
                    .body(responseObject);
        }
    }

    @GetMapping("{code}")
    public ResponseEntity getByCode(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization, @PathVariable("code") String code) {
        try {
            jwtAuthenticationService.checkAuthorizedToken(authorization);
            topicService.checkUserOwnTopic(jwtAuthenticationService.getUserId(authorization), CustomBase64.decodeAsInteger(code));
            TopicDTO topicDTO = topicMapper.entityToDTO(topicService.getByCode(code));
            ResponseObject responseObject = ResponseObject.builder()
                    .message("successfully")
                    .data(topicDTO)
                    .build();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(responseObject);
        } catch (Exception e) {
            ResponseObject responseObject = ResponseObject.builder()
                    .message(e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
                    .body(responseObject);
        }
    }

    @PutMapping("create")
    public ResponseEntity create(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization, @RequestBody TopicRequest topicRequest) {
        try {
            jwtAuthenticationService.checkAuthorizedToken(authorization);
            TopicDTO topicDTO = topicMapper.entityToDTO(topicService.create(jwtAuthenticationService.getUserId(authorization), topicRequest));
            ResponseObject responseObject = ResponseObject.builder()
                    .message("successfully")
                    .data(topicDTO)
                    .build();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(responseObject);
        } catch (Exception e) {
            ResponseObject responseObject = ResponseObject.builder()
                    .message(e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
                    .body(responseObject);
        }
    }

    @PostMapping("update/{code}")
    public ResponseEntity update(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization, @PathVariable("code") String code, @RequestBody TopicRequest topicRequest) {
        try {
            jwtAuthenticationService.checkAuthorizedToken(authorization);
            topicService.checkUserOwnTopic(jwtAuthenticationService.getUserId(authorization), CustomBase64.decodeAsInteger(code));
            TopicDTO topicDTO = topicMapper.entityToDTO(topicService.update(code, topicRequest));
            ResponseObject responseObject = ResponseObject.builder()
                    .message("successfully")
                    .data(topicDTO)
                    .build();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(responseObject);
        } catch (Exception e) {
            ResponseObject responseObject = ResponseObject.builder()
                    .message(e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
                    .body(responseObject);
        }
    }

    @DeleteMapping("delete/{code}")
    public ResponseEntity update(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization, @PathVariable("code") String code) {
        try {
            jwtAuthenticationService.checkAuthorizedToken(authorization);
            topicService.checkUserOwnTopic(jwtAuthenticationService.getUserId(authorization), CustomBase64.decodeAsInteger(code));
            TopicDTO topicDTO = topicMapper.entityToDTO(topicService.delete(code));
            ResponseObject responseObject = ResponseObject.builder()
                    .message("successfully")
                    .data(topicDTO)
                    .build();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(responseObject);
        } catch (Exception e) {
            ResponseObject responseObject = ResponseObject.builder()
                    .message(e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
                    .body(responseObject);
        }
    }
}