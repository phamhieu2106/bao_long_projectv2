package org.example.userquery.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.sharedlibrary.base_constant.PageConstant;
import org.example.sharedlibrary.base_request.BaseRequest;
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
}
