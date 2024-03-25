package com.challenge.demo.persistence.impl;

import com.challenge.demo.persistence.mapper.UserPersistenceMapper;
import com.challenge.demo.security.CustomerDetailsService;
import com.challenge.demo.security.jwt.JwtUtil;
import com.challenge.demo.service.model.User;
import com.challenge.demo.persistence.UserPersistence;
import com.challenge.demo.persistence.entity.UserEntity;
import com.challenge.demo.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserPersistenceImpl implements UserPersistence {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final CustomerDetailsService customerDetailsService;
    private final UserPersistenceMapper userPersistenceMapper;

    @Override
    public User saveUser(User user) {
        UserEntity userEntity = userPersistenceMapper.mapUserToUserEntities(user);

        String newToken = jwtUtil.generateToken(customerDetailsService.getUserDetail().getEmail(),
                customerDetailsService.getUserDetail().getName());
        userEntity.setToken(newToken);

        UserEntity userSaved = userRepository.save(userEntity);
        log.info("USER SAVED {}",userSaved);
        return userPersistenceMapper.mapUserEntityToUser(userSaved);
    }

    @Override
    public List<User> listUsers() {
        Iterable<UserEntity> userEntities = userRepository.findAll();
        List<User> users = new ArrayList<>();
        for (UserEntity userEntity : userEntities) {
            users.add(userPersistenceMapper.mapUserEntityToUser(userEntity));
        }
        return users;
    }

    @Override
    @Transactional
    public User findOneByEmail(String email) {
        var userFounded = userRepository.findOneByEmail(email);
        if (userFounded.isPresent()) {
            var userEntity = userFounded.get();
            return userPersistenceMapper.mapUserEntityToUser(userEntity);
        } else {
            return null;
        }
    }
}
