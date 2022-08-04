package com.example.spring3.service;

import java.util.Collections;
import java.util.Optional;

import com.example.spring3.dto.LoginDto;
import com.example.spring3.dto.UserDto;
import com.example.spring3.entity.Authority;
import com.example.spring3.entity.User;
import com.example.spring3.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User signup(UserDto userDto) {

        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();

        User user = User.builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .authorities(Collections.singleton(authority))
                .activated(true)
                .build();

        return userRepository.save(user);
    }

    @Transactional
    public boolean login(LoginDto requestDto) {
        Optional<User> user = userRepository.findByUsername(requestDto.getUsername());
        if(!user.isPresent()){
            return false;
        } else {
            if(!passwordEncoder.matches(requestDto.getPassword(), user.get().getPassword())){
                return false;
            } else {
                return true;
            }
        }
    }

//    @Transactional(readOnly = true)
//    public Optional<User> getUserWithAuthorities(String username) {
//        return userRepository.findOneWithAuthoritiesByUsername(username);
//    }
//
//    @Transactional(readOnly = true)
//    public Optional<User>  getMyUserWithAuthorities() {
//        return SecurityUtil.getCurrentUsername().flatMap(userRepository::findOneWithAuthoritiesByUsername);
//    }
}