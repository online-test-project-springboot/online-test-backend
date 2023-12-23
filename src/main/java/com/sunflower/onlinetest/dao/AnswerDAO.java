package com.sunflower.onlinetest.dao;

import com.sunflower.onlinetest.entity.AnswerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerDAO extends JpaRepository<AnswerEntity, Integer> {
}
