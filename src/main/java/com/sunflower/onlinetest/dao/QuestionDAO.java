package com.sunflower.onlinetest.dao;

import com.sunflower.onlinetest.entity.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionDAO extends JpaRepository<QuestionEntity, Integer> {

    @Override
    List<QuestionEntity> findAll();

    @Query("from QuestionEntity question where question.topic.id = :topicId")
    List<QuestionEntity> getAllByTopicId(Integer topicId);

    @Query("from QuestionEntity question where question.id = :questionId and question.topic.id = :topicId")
    public QuestionEntity getQuestionByQuestionIdAndTopicId(Integer topicId, Integer questionId);
}
