package com.challenge.demo.persistence.mapper;

import com.challenge.demo.persistence.entity.PhoneEntity;
import com.challenge.demo.persistence.entity.UserEntity;
import com.challenge.demo.service.model.Phone;
import com.challenge.demo.service.model.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserPersistenceMapper {
    public UserEntity mapUserToUserEntities(User user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(user.getId());
        userEntity.setName(user.getName());
        userEntity.setPassword(user.getPassword());
        userEntity.setEmail(user.getEmail());
        userEntity.setToken(user.getToken());
        userEntity.setCreatedAt(user.getCreatedAt());
        userEntity.setUpdatedAt(user.getUpdatedAt());
        userEntity.setLastLogin(user.getLastLogin());
        userEntity.setActive(user.isActive());

        List<PhoneEntity> phoneEntities = user.getPhones().stream().map(phone -> {
            PhoneEntity phoneEntity = new PhoneEntity();
            phoneEntity.setNumber(phone.getNumber());
            phoneEntity.setCityCode(phone.getCityCode());
            phoneEntity.setCountryCode(phone.getCountryCode());
            return phoneEntity;
        }).collect(Collectors.toList());

        userEntity.setPhones(phoneEntities);
        return userEntity;
    }

    public User mapUserEntityToUser(UserEntity userEntity) {
        User user = new User();
        user.setId(userEntity.getId());
        user.setName(userEntity.getName());
        user.setPassword(userEntity.getPassword());
        user.setEmail(userEntity.getEmail());
        user.setToken(userEntity.getToken());
        user.setCreatedAt(userEntity.getCreatedAt());
        user.setUpdatedAt(userEntity.getUpdatedAt());
        user.setLastLogin(userEntity.getLastLogin());
        user.setActive(userEntity.isActive());

        List<Phone> phones = userEntity.getPhones().stream().map(phoneEntity -> {
            Phone phone = new Phone();
            phone.setNumber(phoneEntity.getNumber());
            phone.setCityCode(phoneEntity.getCityCode());
            phone.setCountryCode(phoneEntity.getCountryCode());
            return phone;
        }).collect(Collectors.toList());

        user.setPhones(phones);
        return user;
    }
}
