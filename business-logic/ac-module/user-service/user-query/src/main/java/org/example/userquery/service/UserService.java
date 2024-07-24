package org.example.userquery.service;

import org.example.sharedlibrary.base_quo_poli.UserCreatedModel;
import org.example.sharedlibrary.base_request.BaseRequest;
import org.example.sharedlibrary.model.UserModel;
import org.example.userdomain.domain.UserEntity;

import java.util.List;

public interface UserService {

    String generateUserCode();

    boolean isExitSByUsername(String username);

    Boolean isExitsById(String userId);

    List<UserEntity> findAll(BaseRequest request);

    UserEntity findById(String userId);

    UserEntity getByUsername(String username);

    UserCreatedModel getUserModelById(String username);

    UserModel getUserModelByUsername(String username);

    boolean isHaveEmployeePermission(String username);

    boolean isHaveDirectorPermission(String username);
}
