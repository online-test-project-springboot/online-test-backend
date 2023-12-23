package com.sunflower.onlinetest.dao;

import com.sunflower.onlinetest.entity.ResultEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultDAO extends JpaRepository<ResultEntity, Integer> {

    @Query("select result from ResultEntity result join result.exam exam join result.examPerson examPerson where exam.id = :examId and examPerson.id = :userId")
    ResultEntity findByExamPersonIdAndExamId(Integer userId, Integer examId);
}
