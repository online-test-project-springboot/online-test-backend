package com.sunflower.onlinetest.dao;

import com.sunflower.onlinetest.entity.ExamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExamDAO extends JpaRepository<ExamEntity, Integer> {

    Optional<ExamEntity> findById(Integer id);

    @Query("from ExamEntity exam where exam.owner.id = :userId")
    List<ExamEntity> getAllByUserId(Integer userId);

    @Query("from ExamEntity exam where exam.id = :examId and exam.owner.id = :userId")
    ExamEntity getExamByExamIdAndOwnerId(Integer userId, Integer examId);
}
