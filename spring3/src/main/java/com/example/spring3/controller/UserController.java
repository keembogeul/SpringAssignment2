package com.example.spring3.controller;

import com.example.spring3.dto.ResponseDto;
import com.example.spring3.dto.UserDto;
import com.example.spring3.entity.User;
import com.example.spring3.repository.UserRepository;
import com.example.spring3.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @PostMapping("/member/signup")
    public ResponseDto<?> signup(@Valid @RequestBody UserDto userDto) {
        if (userRepository.findOneWithAuthoritiesByUsername(userDto.getUsername()).orElse(null) != null) {
            return ResponseDto.fail("DUPLICATED_ID", "중복된 아이디가 존재합니다.");
        }

        if (!userDto.getPassword().equals(userDto.getPasswordConfirm())) {
            return ResponseDto.fail("NOT_CONFIRM", "비밀번호 확인이 일치하지 않습니다.");
        }
        return ResponseDto.success(userService.signup(userDto));
    }

    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<User> getMyUserInfo(HttpServletRequest request) {
        return ResponseEntity.ok(userService.getMyUserWithAuthorities().get());
    }

    @GetMapping("/user/{username}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<User> getUserInfo(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserWithAuthorities(username).get());
    }
}