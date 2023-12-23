package com.sunflower.onlinetest.service.serviceImpl;

import com.sunflower.onlinetest.dao.QuestionDAO;
import com.sunflower.onlinetest.dao.TopicDAO;
import com.sunflower.onlinetest.dao.UserDAO;
import com.sunflower.onlinetest.entity.AnswerEntity;
import com.sunflower.onlinetest.entity.QuestionEntity;
import com.sunflower.onlinetest.entity.TopicEntity;
import com.sunflower.onlinetest.entity.UserEntity;
import com.sunflower.onlinetest.rest.mapper.QuestionMapper;
import com.sunflower.onlinetest.rest.request.AnswerRequest;
import com.sunflower.onlinetest.rest.request.QuestionRequest;
import com.sunflower.onlinetest.service.QuestionService;
import com.sunflower.onlinetest.util.CustomBase64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionDAO questionDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private TopicDAO topicDAO;

    @Autowired
    private QuestionMapper questionMapper;

    @Override
    public List<QuestionEntity> getAll() {
        return questionDAO.findAll();
    }

    @Override
    public QuestionEntity getByCode(String code) {
        try {
            Integer id = Integer.valueOf(CustomBase64.decode(code));
            return questionDAO.findById(id).get();
        } catch (Exception e) {
            throw new RuntimeException("Could not get question " + e.getMessage());
        }
    }


    @Override
    public QuestionEntity create(Integer userId, Integer topicId, QuestionRequest questionRequest) {
        try {
            UserEntity owner = userDAO.findById(userId).get();
            TopicEntity topic = topicDAO.findById(topicId).get();
            if (Objects.isNull(owner)) {
                throw new RuntimeException("Could not find owner with email = " + userId);
            }
            QuestionEntity questionEntity = QuestionEntity.builder()
                    .content(questionRequest.getContent())
                    .answers(convert(questionRequest.getAnswers()))
                    .topic(topic)
                    .build();
            return questionDAO.save(questionEntity);
        } catch (Exception e) {
            throw new RuntimeException("Could not create question " + e.getMessage());
        }
    }

    @Override
    public QuestionEntity update(String code, QuestionRequest questionRequest) {
        try {
            Integer id = Integer.valueOf(CustomBase64.decode(code));
            QuestionEntity foundQuestion = questionDAO.findById(id).get();
            if (Objects.isNull(foundQuestion)) {
                throw new RuntimeException("Question could not found");
            }
            updateQuestionEntity(foundQuestion, questionRequest);
            return questionDAO.save(foundQuestion);
        } catch (Exception e) {
            throw new RuntimeException("Could not update question: " + e.getMessage());
        }
    }

    @Override
    public QuestionEntity delete(String code) {
        try {
            Integer id = Integer.valueOf(CustomBase64.decode(code));
            QuestionEntity questionEntity = questionDAO.findById(id).get();
            questionDAO.deleteById(id);
            return questionEntity;
        } catch (Exception e) {
            throw new RuntimeException("Could not delete question " + e.getMessage());
        }
    }

    @Override
    public List<QuestionEntity> getAllByTopicId(Integer topicId) {
        return questionDAO.getAllByTopicId(topicId);
    }

    @Override
    public void checkTopicContainQuestion(Integer topicId, Integer questionId) {
        QuestionEntity foundQuestion = questionDAO.getQuestionByQuestionIdAndTopicId(topicId, questionId);
        if (Objects.isNull(foundQuestion)) {
            throw new RuntimeException(String.format("User do not own topic, topicId: %s and questionId: %s", topicId, questionId));
        }
    }

    private void updateQuestionEntity(QuestionEntity foundQuestion, QuestionRequest questionRequest) {
        // update content
        foundQuestion.setContent(questionRequest.getContent());
        // update answer list
        foundQuestion.getAnswers().clear();
        foundQuestion.getAnswers().addAll(convert(questionRequest.getAnswers()));
    }

    private List<AnswerEntity> convert(List<AnswerRequest> answerRequests) {
        return answerRequests.stream()
                .map(answerRequest -> AnswerEntity.builder()
                        .content(answerRequest.getContent())
                        .rightAnswer(answerRequest.isRightAnswer())
                        .build())
                .collect(Collectors.toList());
    }
}
