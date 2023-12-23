package com.sunflower.onlinetest.dao;

import com.sunflower.onlinetest.entity.TopicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicDAO extends JpaRepository<TopicEntity, Integer> {

    List<TopicEntity> findByOwnerId(@NonNull Integer ownerId);

    TopicEntity findByOwnerIdAndId(@NonNull Integer ownerId, @NonNull Integer topicId);
}
