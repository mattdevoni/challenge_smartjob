package com.challenge.demo.service;

import com.challenge.demo.service.model.User;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface UserService {
    ResponseEntity<String> login(Map<String, String> requestMap);

    User createUser(User user);

    List<User> listUser();

}
