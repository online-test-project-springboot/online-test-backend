package com.sunflower.onlinetest.service.serviceImpl;

import com.sunflower.onlinetest.dao.TopicDAO;
import com.sunflower.onlinetest.dao.UserDAO;
import com.sunflower.onlinetest.entity.TopicEntity;
import com.sunflower.onlinetest.entity.UserEntity;
import com.sunflower.onlinetest.rest.mapper.TopicMapper;
import com.sunflower.onlinetest.rest.request.TopicRequest;
import com.sunflower.onlinetest.service.TopicService;
import com.sunflower.onlinetest.util.CustomBase64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class TopicServiceImpl implements TopicService {

    @Autowired
    private TopicDAO topicDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private TopicMapper topicMapper;

    @Override
    public List<TopicEntity> getAll() {
        return topicDAO.findAll();
    }

    @Override
    public TopicEntity getByCode(String code) {
        try {
            Integer id = Integer.valueOf(CustomBase64.decode(code));
            return topicDAO.findById(id).get();
        } catch (Exception e) {
            throw new RuntimeException("Could not get topic " + e.getMessage());
        }
    }

    @Override
    public TopicEntity create(Integer userId, TopicRequest topicRequest) {
        try {
            UserEntity owner = userDAO.findById(userId).get();
            if (Objects.isNull(owner)) {
                throw new RuntimeException("Could not find owner with id = " + userId);
            }
            TopicEntity topicEntity = TopicEntity.builder()
                    .name(topicRequest.getName())
                    .description(topicRequest.getDescription())
                    .owner(owner)
                    .build();
            return topicDAO.save(topicEntity);
        } catch (Exception e) {
            throw new RuntimeException("Could not create topic " + e.getMessage());
        }
    }

    @Override
    public TopicEntity update(String code, TopicRequest topicRequest) {
        try {
            Integer topicId = CustomBase64.decodeAsInteger(code);
            TopicEntity foundTopic = topicDAO.findById(topicId).get();
            if (Objects.isNull(foundTopic)) {
                throw new RuntimeException("Topic could not found by Id: " + topicId);
            }
            foundTopic.setName(topicRequest.getName());
            foundTopic.setDescription(topicRequest.getDescription());
            return topicDAO.save(foundTopic);
        } catch (Exception e) {
            throw new RuntimeException("Could not update topic: " + e.getMessage());
        }
    }

    @Override
    public TopicEntity delete(String code) {
        try {
            Integer id = Integer.valueOf(CustomBase64.decode(code));
            TopicEntity topicEntity = topicDAO.findById(id).get();
            topicDAO.deleteById(id);
            return topicEntity;
        } catch (Exception e) {
            throw new RuntimeException("Could not delete topic " + e.getMessage());
        }
    }

    @Override
    public List<TopicEntity> getAllByUserId(Integer userId) {
        return topicDAO.findByOwnerId(userId);
    }

    @Override
    public void checkUserOwnTopic(Integer userId, Integer topicId) {
        TopicEntity foundTopic = topicDAO.findByOwnerIdAndId(userId, topicId);
        if (Objects.isNull(foundTopic)) {
            throw new RuntimeException(String.format("User do not own topic, userId: %s and topicId: %s", userId, topicId));
        }
    }
}
