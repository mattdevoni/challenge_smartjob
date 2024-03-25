package com.challenge.demo.persistence;

import com.challenge.demo.service.model.User;

import java.util.List;

public interface UserPersistence {

    User saveUser (User user);

    List<User> listUsers();

    User findOneByEmail(String email);
}
