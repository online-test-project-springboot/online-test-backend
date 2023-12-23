package com.sunflower.onlinetest.rest;

import com.sunflower.onlinetest.rest.mapper.ExamDetailMapper;
import com.sunflower.onlinetest.rest.mapper.ExamMapper;
import com.sunflower.onlinetest.rest.request.ExamRequest;
import com.sunflower.onlinetest.rest.response.ExamDTO;
import com.sunflower.onlinetest.rest.response.ExamDetailDTO;
import com.sunflower.onlinetest.rest.response.ResponseObject;
import com.sunflower.onlinetest.service.ExamService;
import com.sunflower.onlinetest.service.JWTAuthenticationService;
import com.sunflower.onlinetest.util.CustomBase64;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(
        value = "rest/exams",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class ExamREST {

    @Autowired
    private JWTAuthenticationService jwtAuthenticationService;

    @Autowired
    private ExamService examService;

    @Autowired
    private ExamMapper examMapper;

    @Autowired
    private ExamDetailMapper examDetailMapper;

    @GetMapping
    public ResponseEntity getAllByUserId(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
        try {
            jwtAuthenticationService.checkAuthorizedToken(authorization);
            List<ExamDTO> examDTOS = examMapper.entityToDTOs(examService.getAllByUserId(jwtAuthenticationService.getUserId(authorization)));
            ResponseObject responseObject = ResponseObject.builder()
                    .message("successfully")
                    .data(examDTOS)
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
            examService.checkUserOwnExam(jwtAuthenticationService.getUserId(authorization), CustomBase64.decodeAsInteger(code));
            ExamDetailDTO examDTO = examDetailMapper.entityToDTO(examService.getByCode(code));
            ResponseObject responseObject = ResponseObject.builder()
                    .message("successfully")
                    .data(examDTO)
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
    public ResponseEntity create(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization, @RequestBody ExamRequest examRequest) {
        try {
            jwtAuthenticationService.checkAuthorizedToken(authorization);
            ExamDTO examDTO = examMapper.entityToDTO(examService.create(jwtAuthenticationService.getUserId(authorization), examRequest));
            ResponseObject responseObject = ResponseObject.builder()
                    .message("successfully")
                    .data(examDTO)
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
    public ResponseEntity update(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization, @PathVariable("code") String code, @RequestBody ExamRequest examRequest) {
        try {
            jwtAuthenticationService.checkAuthorizedToken(authorization);
            examService.checkUserOwnExam(jwtAuthenticationService.getUserId(authorization), CustomBase64.decodeAsInteger(code));
            ExamDTO examDTO = examMapper.entityToDTO(examService.update(code, examRequest));
            ResponseObject responseObject = ResponseObject.builder()
                    .message("successfully")
                    .data(examDTO)
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
    public ResponseEntity delete(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization, @PathParam("code") String code) {
        try {
            jwtAuthenticationService.checkAuthorizedToken(authorization);
            examService.checkUserOwnExam(jwtAuthenticationService.getUserId(authorization), CustomBase64.decodeAsInteger(code));
            ExamDTO examDTO = examMapper.entityToDTO(examService.delete(code));
            ResponseObject responseObject = ResponseObject.builder()
                    .message("successfully")
                    .data(examDTO)
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
