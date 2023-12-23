package com.sunflower.onlinetest.rest;

import com.sunflower.onlinetest.rest.mapper.QuestionMapper;
import com.sunflower.onlinetest.rest.mapper.TopicQuestionMapper;
import com.sunflower.onlinetest.rest.request.QuestionRequest;
import com.sunflower.onlinetest.rest.response.QuestionDTO;
import com.sunflower.onlinetest.rest.response.ResponseObject;
import com.sunflower.onlinetest.rest.response.TopicQuestionDTO;
import com.sunflower.onlinetest.service.JWTAuthenticationService;
import com.sunflower.onlinetest.service.QuestionService;
import com.sunflower.onlinetest.service.TopicService;
import com.sunflower.onlinetest.util.CustomBase64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(
        value = "rest/topics/{topicCode}/questions",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class QuestionREST {

    @Autowired
    private JWTAuthenticationService jwtAuthenticationService;

    @Autowired
    private TopicService topicService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private TopicQuestionMapper topicQuestionMapper;

    @GetMapping
    public ResponseEntity getAllByTopicId(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization, @PathVariable("topicCode") String topicCode) {
        try {
            Integer topicId = CustomBase64.decodeAsInteger(topicCode);
            jwtAuthenticationService.checkAuthorizedToken(authorization);
            topicService.checkUserOwnTopic(jwtAuthenticationService.getUserId(authorization), topicId);
            TopicQuestionDTO topicQuestionDTO = topicQuestionMapper.entityToDTO(topicService.getByCode(topicCode));
            ResponseObject responseObject = ResponseObject.builder()
                    .message("successfully")
                    .data(topicQuestionDTO)
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
    public ResponseEntity getByCode(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization, @PathVariable("topicCode") String topicCode, @PathVariable("code") String code) {
        try {
            Integer topicId = CustomBase64.decodeAsInteger(topicCode);
            jwtAuthenticationService.checkAuthorizedToken(authorization);
            topicService.checkUserOwnTopic(jwtAuthenticationService.getUserId(authorization), topicId);
            questionService.checkTopicContainQuestion(topicId, CustomBase64.decodeAsInteger(code));
            QuestionDTO questionDTO = questionMapper.entityToDTO(questionService.getByCode(code));
            ResponseObject responseObject = ResponseObject.builder()
                    .message("successfully")
                    .data(questionDTO)
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
    public ResponseEntity create(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization, @PathVariable("topicCode") String topicCode, @RequestBody QuestionRequest questionRequest) {
        try {
            Integer topicId = CustomBase64.decodeAsInteger(topicCode);
            jwtAuthenticationService.checkAuthorizedToken(authorization);
            topicService.checkUserOwnTopic(jwtAuthenticationService.getUserId(authorization), topicId);
            QuestionDTO questionDTO = questionMapper.entityToDTO(questionService.create(jwtAuthenticationService.getUserId(authorization), topicId, questionRequest));
            ResponseObject responseObject = ResponseObject.builder()
                    .message("successfully")
                    .data(questionDTO)
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
    public ResponseEntity update(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization, @PathVariable("topicCode") String topicCode, @PathVariable("code") String code, @RequestBody QuestionRequest questionRequest) {
        try {
            Integer topicId = CustomBase64.decodeAsInteger(topicCode);
            jwtAuthenticationService.checkAuthorizedToken(authorization);
            topicService.checkUserOwnTopic(jwtAuthenticationService.getUserId(authorization), topicId);
            questionService.checkTopicContainQuestion(topicId, CustomBase64.decodeAsInteger(code));
            QuestionDTO questionDTO = questionMapper.entityToDTO(questionService.update(code, questionRequest));
            ResponseObject responseObject = ResponseObject.builder()
                    .message("successfully")
                    .data(questionDTO)
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
    public ResponseEntity update(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization, @PathVariable("topicCode") String topicCode, @PathVariable("code") String code) {
        try {
            Integer topicId = CustomBase64.decodeAsInteger(topicCode);
            jwtAuthenticationService.checkAuthorizedToken(authorization);
            topicService.checkUserOwnTopic(jwtAuthenticationService.getUserId(authorization), topicId);
            questionService.checkTopicContainQuestion(topicId, CustomBase64.decodeAsInteger(code));
            QuestionDTO questionDTO = questionMapper.entityToDTO(questionService.delete(code));
            ResponseObject responseObject = ResponseObject.builder()
                    .message("successfully")
                    .data(questionDTO)
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
