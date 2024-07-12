package org.example.userflow.service.impl;

import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.sharedlibrary.base_class.BaseEvent;
import org.example.userdomain.domain.UserEntity;
import org.example.userdomain.event.UserCreateEvent;
import org.example.userdomain.event.UserDeleteEvent;
import org.example.userdomain.event.UserUpdateEvent;
import org.example.userflow.repository.UserConsumerRepository;
import org.example.userflow.service.UserConsumerService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserConsumerServiceImpl implements UserConsumerService {

    private final UserConsumerRepository userConsumerRepository;

    @Override
    @KafkaListener(topics = "user_service", groupId = "user_group")
    public void subscribe(BaseEvent event) {
        if (event instanceof UserCreateEvent) {
            handleCreateEvent((UserCreateEvent) event);
        } else if (event instanceof UserUpdateEvent) {
            handleUpdateEvent((UserUpdateEvent) event);
        } else if (event instanceof UserDeleteEvent) {
            handleDeleteEvent((UserDeleteEvent) event);
        }
    }

    @Override
    public void handleCreateEvent(UserCreateEvent event) {
        UserEntity entity = new UserEntity();
        entity.setId(event.getUserId());
        entity.setUserCode(event.getUserCode());
        entity.setUsername(event.getUsername());
        entity.setPassword(event.getPassword());
        entity.setRole(event.getRole());
        entity.setCreatedAt(event.getTimestamp());
        entity.setDepartment(event.getDepartment());
        entity.setOffice(event.getOffice());
        entity.setPermissions(event.getPermissions());
        entity.setIsDeleted(false);
        entity.setCreatedBy(event.getCreatedBy());
        userConsumerRepository.save(entity);
    }

    @Override
    public void handleUpdateEvent(UserUpdateEvent event) {
        Optional<UserEntity> optionalUserEntity = userConsumerRepository.findById(event.getUserId());
        if (optionalUserEntity.isEmpty()) {
            throw new NotFoundException("Not Found User");
        }
        UserEntity entity = optionalUserEntity.get();
        entity.setPassword(event.getPassword());
        entity.setRole(event.getRole());
        entity.setDepartment(event.getDepartment());
        entity.setOffice(event.getOffice());
        entity.setPermissions(entity.getPermissions());
        userConsumerRepository.save(entity);
    }

    @Override
    public void handleDeleteEvent(UserDeleteEvent event) {
        Optional<UserEntity> optionalUserEntity = userConsumerRepository.findById(event.getUserId());
        if (optionalUserEntity.isEmpty()) {
            throw new NotFoundException("Not Found User");
        }
        UserEntity entity = optionalUserEntity.get();
        entity.setIsDeleted(true);
        userConsumerRepository.save(entity);
    }
}
