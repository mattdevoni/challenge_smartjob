package com.challenge.demo.persistence.impl;

import com.challenge.demo.persistence.entity.PhoneEntity;
import com.challenge.demo.persistence.entity.UserEntity;
import com.challenge.demo.persistence.mapper.UserPersistenceMapper;
import com.challenge.demo.persistence.repository.UserRepository;
import com.challenge.demo.security.CustomerDetailsService;
import com.challenge.demo.security.jwt.JwtUtil;
import com.challenge.demo.service.model.Phone;
import com.challenge.demo.service.model.User;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserPersistenceImplTest {

    @Test
    void saveUser_ValidUser_SuccessfullySaved() {
        // Arrange
        UserRepository userRepository = mock(UserRepository.class);
        JwtUtil jwtUtil = mock(JwtUtil.class);
        CustomerDetailsService customerDetailsService = mock(CustomerDetailsService.class);
        UserPersistenceMapper userPersistenceMapper = mock(UserPersistenceMapper.class);

        UserPersistenceImpl userPersistence = new UserPersistenceImpl(userRepository, jwtUtil, customerDetailsService,
                userPersistenceMapper);

        PhoneEntity phoneEntity1 = new PhoneEntity(0L, "1234567", "1", "57");
        PhoneEntity phoneEntity2 = new PhoneEntity(1L,"1234567", "1", "59");
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("test@example.com");
        userEntity.setPassword("password123");
        userEntity.setPhones(Arrays.asList(phoneEntity1, phoneEntity2));

        when(customerDetailsService.getUserDetail()).thenReturn(userEntity);

        Phone phone1 = new Phone("1234567", "1", "57");
        Phone phone2 = new Phone("1234567", "1", "59");
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password123");
        user.setPhones(Arrays.asList(phone1, phone2));

        when(userPersistenceMapper.mapUserToUserEntities(any(User.class))).thenCallRealMethod();
        when(jwtUtil.generateToken(any(),any())).thenReturn("generatedToken");
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);
        when(userPersistenceMapper.mapUserEntityToUser(any(UserEntity.class))).thenCallRealMethod();

        User savedUser = userPersistence.saveUser(user);

        assertNotNull(savedUser);
        assertEquals("test@example.com", savedUser.getEmail());

        // verifying that the argument passed to saveRepository is the one we process
        ArgumentCaptor<UserEntity> userEntityCaptor = ArgumentCaptor.forClass(UserEntity.class);
        verify(userRepository).save(userEntityCaptor.capture());
        UserEntity capturedUserEntity = userEntityCaptor.getValue();
        assertEquals("test@example.com", capturedUserEntity.getEmail());
        assertEquals("password123", capturedUserEntity.getPassword());
    }

    @Test
    void saveUser_InvalidUser_FailedToSave() {
        // Arrange
        UserRepository userRepository = mock(UserRepository.class);
        JwtUtil jwtUtil = mock(JwtUtil.class);
        CustomerDetailsService customerDetailsService = mock(CustomerDetailsService.class);
        UserPersistenceMapper userPersistenceMapper = mock(UserPersistenceMapper.class);

        UserPersistenceImpl userPersistence = new UserPersistenceImpl(userRepository, jwtUtil, customerDetailsService,
                userPersistenceMapper);

        PhoneEntity phoneEntity = new PhoneEntity(0L,"1234567", "1", "57");
        PhoneEntity phoneEntity2 = new PhoneEntity(1L,"1234567", "1", "59");
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("test@example.com");
        userEntity.setPassword("password123");
        userEntity.setPhones(Arrays.asList(phoneEntity, phoneEntity2));

        when(customerDetailsService.getUserDetail()).thenReturn(userEntity);

        Phone phone = new Phone("1234567", "1", "57");
        Phone phone2 = new Phone("1234567", "1", "59");
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password123");
        user.setPhones(Arrays.asList(phone, phone2));

        when(userPersistenceMapper.mapUserToUserEntities(any(User.class))).thenCallRealMethod();
        when(jwtUtil.generateToken(anyString(), anyString())).thenReturn("generatedToken");
        when(userRepository.save(any(UserEntity.class))).thenThrow(new RuntimeException("Failed to save user"));

        // Action
        assertThrows(RuntimeException.class, () -> userPersistence.saveUser(user));
    }
}