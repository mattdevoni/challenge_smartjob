package com.challenge.demo.controller.mapper;

import com.challenge.demo.controller.dto.PhoneDTO;
import com.challenge.demo.controller.dto.UserDTO;
import com.challenge.demo.controller.dto.UserResponse;
import com.challenge.demo.service.model.Phone;
import com.challenge.demo.service.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {
    public static User toUser(UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        if (userDTO.getPhones() != null) {
            List<Phone> phones = userDTO.getPhones().stream()
                    .map(phoneDTO -> new Phone(phoneDTO.getNumber(), phoneDTO.getCityCode(), phoneDTO.getCountryCode()))
                    .collect(Collectors.toList());
            user.setPhones(phones);
        }
        return user;
    }

    public static UserResponse toUsersResponse(User user) {
        UserResponse userResponse = new UserResponse();
        if (user.getId() != null) {
            userResponse.setId(user.getId().toString());
        }
        userResponse.setCreated(user.getCreatedAt());
        userResponse.setModified(user.getUpdatedAt());
        userResponse.setLastLogin(user.getLastLogin());
        userResponse.setToken(user.getToken());
        userResponse.setActive(user.isActive());
        if (user.getPhones() != null) {
            List<PhoneDTO> phoneDTOs = user.getPhones().stream()
                    .map(phone -> new PhoneDTO(phone.getNumber(), phone.getCityCode(), phone.getCountryCode()))
                    .collect(Collectors.toList());
            userResponse.setPhones(phoneDTOs);
        }
        return userResponse;
    }
}
