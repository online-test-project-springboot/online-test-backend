package com.sunflower.onlinetest.dao;

import com.sunflower.onlinetest.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserDAO extends JpaRepository<UserEntity, Integer> {

    @Query("from UserEntity user where user.email = :email")
    UserEntity findByEmail(String email);
}
