package com.example.spring3.controller;


import com.example.spring3.dto.LoginDto;
import com.example.spring3.dto.ResponseDto;
import com.example.spring3.dto.TokenDto;
import com.example.spring3.entity.User;
import com.example.spring3.jwt.TokenProvider;
import com.example.spring3.repository.AuthorityRepository;
import com.example.spring3.repository.UserRepository;
import com.example.spring3.service.UserService;
import jdk.nashorn.internal.parser.DateParser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserRepository userRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/member/login")
    public ResponseEntity<?> authorize(@Valid @RequestBody LoginDto loginDto) {
        if (userService.login(loginDto)) {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            TokenDto tokenDto = tokenProvider.createToken(authentication);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("AccessToken", tokenDto.getAccessToken());
            httpHeaders.add("RefreshToken", tokenDto.getRefreshToken());

            return ResponseEntity.ok()
                    .headers(httpHeaders)
                    .body(ResponseDto.success(userRepository.findOneWithAuthoritiesByUsername(loginDto.getUsername())));

        } else if (!userService.login(loginDto)) {
            return ResponseEntity.ok()
                    .body(ResponseDto.fail("NOT_FOUND", "사용자를 찾을 수 없습니다."));
        }
        return null;
    }

    @PostMapping("/member/authenticate")
    public ResponseEntity<TokenDto> getToken(@Valid @RequestBody LoginDto loginDto) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        TokenDto tokenDto = tokenProvider.createToken(authentication);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("AccessToken", tokenDto.getAccessToken());
        httpHeaders.add("RefreshToken", tokenDto.getRefreshToken());

        return new ResponseEntity<>(new TokenDto(tokenDto.getAccessToken(), tokenDto.getRefreshToken()), httpHeaders, HttpStatus.OK);
    }

}
