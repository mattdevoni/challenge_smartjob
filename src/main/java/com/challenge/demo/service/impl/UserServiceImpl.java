package com.challenge.demo.service.impl;

import com.challenge.demo.exceptions.ExistUserException;
import com.challenge.demo.persistence.UserPersistence;
import com.challenge.demo.security.CustomerDetailsService;
import com.challenge.demo.service.UserService;
import com.challenge.demo.service.model.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Slf4j
@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserPersistence userPersistence;
    private final AuthenticationManager authenticationManager;
    private final CustomerDetailsService customerDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        log.info("Inside login method");
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(requestMap.get("email"), requestMap.get("password"))
            );

            if (authentication.isAuthenticated()) {
                if (customerDetailsService.getUserDetail().isActive()) {
                    User user = userPersistence.findOneByEmail(requestMap.get("email"));

                    user.setLastLogin(LocalDateTime.now());
                    user = userPersistence.saveUser(user);
                    log.info("The user data has been updated by authentication. {}", user);

                    return new ResponseEntity<>("{\"token\":\"" + user.getToken() + "\"}",
                            HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("{\"message\":\"" + "wait for administrator approval" + "\"}",
                            HttpStatus.BAD_REQUEST);
                }
            }

        } catch (Exception error) {
            log.error("Login Err: {}", error);
        }
        return new ResponseEntity<>("{\"message\":\"" + "Bad Credentials" + "\"}", HttpStatus.BAD_REQUEST);
    }

    public User createUser(User user) {
        User userObtained = userPersistence.findOneByEmail(user.getEmail());
        if (userObtained != null) {
            throw new ExistUserException();
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        return userPersistence.saveUser(user);
    }

    @Override
    public List<User> listUser() {
        return userPersistence.listUsers();
    }
}
