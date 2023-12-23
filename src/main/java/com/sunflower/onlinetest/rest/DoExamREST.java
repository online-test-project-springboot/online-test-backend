package com.sunflower.onlinetest.rest;

import com.sunflower.onlinetest.entity.ExamEntity;
import com.sunflower.onlinetest.entity.ResultEntity;
import com.sunflower.onlinetest.rest.mapper.DoExamMapper;
import com.sunflower.onlinetest.rest.mapper.ResultMapper;
import com.sunflower.onlinetest.rest.request.DoExamRequest;
import com.sunflower.onlinetest.rest.response.DoExamDTO;
import com.sunflower.onlinetest.rest.response.ResponseObject;
import com.sunflower.onlinetest.rest.response.ResultDTO;
import com.sunflower.onlinetest.service.ExamService;
import com.sunflower.onlinetest.service.JWTAuthenticationService;
import com.sunflower.onlinetest.service.ResultService;
import com.sunflower.onlinetest.util.CustomBase64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(
        value = "rest/do-exam",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class DoExamREST {

    @Autowired
    private JWTAuthenticationService jwtAuthenticationService;

    @Autowired
    private ExamService examService;

    @Autowired
    private DoExamMapper doExamMapper;

    @Autowired
    private ResultService resultService;

    @Autowired
    private ResultMapper resultMapper;

    @GetMapping("{code}")
    public ResponseEntity<ResponseObject> takeExam(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization, @PathVariable("code") String examCode) {
        try {
            jwtAuthenticationService.checkAuthorizedToken(authorization);
            ExamEntity examEntity = examService.getByCode(examCode);
            ResultEntity resultEntity = resultService.create(jwtAuthenticationService.getUserId(authorization), examEntity);
            DoExamDTO doExamDTO = doExamMapper.entityToDTO(examEntity);
            doExamDTO.setStartTime(resultEntity.getStartTime());
            ResponseObject responseObject = ResponseObject.builder()
                    .message("successfully")
                    .data(doExamDTO)
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

    @PostMapping("submit/{code}")
    public ResponseEntity<ResponseObject> submitExam(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization, @PathVariable("code") String examCode, @RequestBody DoExamRequest doExamRequest) {
        try {
            Integer examId = CustomBase64.decodeAsInteger(examCode);
            jwtAuthenticationService.checkAuthorizedToken(authorization);
            Integer userId = jwtAuthenticationService.getUserId(authorization);
            resultService.checkSubmitPerson(userId, examId);
            ResultDTO resultDTO = resultMapper.entityToDTO(resultService.update(userId, examId, doExamRequest));
            ResponseObject responseObject = ResponseObject.builder()
                    .message("successfully")
                    .data(resultDTO)
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
