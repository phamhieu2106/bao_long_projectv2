package org.example.userquery.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.sharedlibrary.base_constant.PageConstant;
import org.example.sharedlibrary.base_quo_poli.UserCreatedModel;
import org.example.sharedlibrary.base_request.BaseRequest;
import org.example.sharedlibrary.enumeration.ac.Permission;
import org.example.sharedlibrary.model.UserModel;
import org.example.userdomain.domain.UserEntity;
import org.example.userquery.repository.UserEntityRepository;
import org.example.userquery.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserEntityRepository userEntityRepository;

    @Override
    public String generateUserCode() {
        String code;
        long count = userEntityRepository.count();
        do {
            code = String.format("U%03d", ++count);
            if (userEntityRepository.existsByUserCode(code)) {
                code = String.format("U%03d", ++count);
            }
        } while (userEntityRepository.existsByUserCode(code));
        return code;
    }

    @Override
    public UserCreatedModel getUserModelById(String username) {
        Optional<UserEntity> userEntity = userEntityRepository.findByUsername(username);
        if (userEntity.isEmpty()) {
            return null;
        }
        UserEntity user = userEntity.get();
        return new UserCreatedModel(
                user.getUsername(),
                user.getOffice(),
                user.getDepartment().name()
        );
    }

    @Override
    public UserModel getUserModelByUsername(String username) {
        Optional<UserEntity> userEntity = userEntityRepository.findByUsername(username);
        if (userEntity.isEmpty()) {
            return null;
        }
        UserEntity user = userEntity.get();
        return new UserModel(
                user.getUsername(),
                user.getRole(),
                user.getPermissions()
        );
    }

    @Override
    public boolean isExitSByUsername(String username) {
        return userEntityRepository.existsByUsername(username);
    }

    @Override
    public Boolean isExitsById(String userId) {
        return userEntityRepository.existsById(userId);
    }

    @Override
    public List<UserEntity> findAll(BaseRequest request) {
        Page<UserEntity> list;
        try {
            Pageable pageable = PageRequest.of(request.getPageNumber(), request.getPageSize()
                    , PageConstant.getSortBys(request.getSortBys(), request.getSortOrder()));
            list = userEntityRepository.findAll(pageable);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list.getContent();
    }

    @Override
    public UserEntity findById(String userId) {
        Optional<UserEntity> optional = userEntityRepository.findById(userId);
        return optional.orElse(null);
    }

    @Override
    public UserEntity getByUsername(String username) {
        Optional<UserEntity> optional = userEntityRepository.findByUsername(username);
        return optional.orElse(null);
    }

    @Override
    public boolean isHaveEmployeePermission(String username) {
        Optional<UserEntity> optional = userEntityRepository.findByUsername(username);
        return optional.map(userEntity -> userEntity.getPermissions().contains(Permission.SUBMIT)).orElse(false);
    }


    @Override
    public boolean isHaveDirectorPermission(String username) {
        Optional<UserEntity> optional = userEntityRepository.findByUsername(username);
        return optional.map(userEntity -> userEntity.getPermissions().contains(Permission.APPROVE)).orElse(false);
    }

    @Override
    public boolean isFirstUsernameHavePermissionEqualsOrGatherThanSecondUsername(String username, String modifiedBy) {
        Optional<UserEntity> optionalUser = userEntityRepository.findByUsername(username);
        if (optionalUser.isEmpty()) return false;
        Optional<UserEntity> optionalModified = userEntityRepository.findByUsername(modifiedBy);
        return optionalModified.filter(userEntity -> optionalUser.get().getRole().getValue() >= userEntity.getRole().getValue()).isPresent();
    }
}
