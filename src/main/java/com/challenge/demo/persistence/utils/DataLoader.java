package com.challenge.demo.persistence.utils;

import com.challenge.demo.persistence.entity.UserEntity;
import com.challenge.demo.persistence.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DataLoader implements CommandLineRunner {
    private final UserRepository userRepository;

    @Value("${initial.user.name}")
    private String userName;

    @Value("${initial.user.pass}")
    private String userPass;

    @Value("${initial.user.email}")
    private String userEmail;

    public DataLoader(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) {
        if(userRepository.count() == 0) {
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            log.info("Attempt of load");
            UserEntity initialUser =
                new UserEntity(userName, bCryptPasswordEncoder.encode(userPass), userEmail);

            UserEntity savedUser = userRepository.save(initialUser);
            log.info("user created :{}", savedUser);
        }
    }
}
