package org.example.userquery.service;

import org.example.sharedlibrary.base_request.BaseRequest;
import org.example.userdomain.domain.UserEntity;

import java.util.List;

public interface UserService {

    String generateUserCode();

    boolean isExitSByUsername(String username);

    Boolean isExitsById(String userId);

    List<UserEntity> findAll(BaseRequest request);

    UserEntity findById(String userId);

    UserEntity getByUsername(String username);
}
