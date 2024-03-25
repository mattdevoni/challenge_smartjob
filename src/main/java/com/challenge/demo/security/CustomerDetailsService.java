package com.challenge.demo.security;

import com.challenge.demo.persistence.entity.UserEntity;
import com.challenge.demo.persistence.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Slf4j
@Service
public class CustomerDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    public CustomerDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private UserEntity userDetail;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("CustomerDetailService -> loadUserByUsername {}", email);
        userDetail = userRepository.findOneByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("The user with email " + email + " not exist."));

        return new org.springframework.security.core.userdetails
                .User(userDetail.getEmail(), userDetail.getPassword(), new ArrayList<>());
    }

    public UserEntity getUserDetail() {
        return userDetail;
    }
}
