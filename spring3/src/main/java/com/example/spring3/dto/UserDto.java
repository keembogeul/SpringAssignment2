package com.example.spring3.dto;

import com.example.spring3.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.*;
import java.util.Set;
import java.util.stream.Collectors;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @NotBlank(message = "아이디를 입력해주세요")
    @Pattern(regexp="^[a-zA-Z0-9]{3,12}$", message="아이디를 3~12자로 입력해주세요.(특수문자x)")
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Pattern(regexp="^[a-z0-9]{4,32}$", message="비밀번호를 4~32자로 입력해주세요.(특수문자x)")
    @NotBlank(message = "비밀번호를 입력해주세요")
    private String password;

    @NotBlank(message = "비밀번호 확인을 입력해주세요")
    private String passwordConfirm;

    private Set<AuthorityDto> authorityDtoSet;

    public static UserDto from(User user) {
        if(user == null) return null;

        return UserDto.builder()
                .username(user.getUsername())
                .authorityDtoSet(user.getAuthorities().stream()
                        .map(authority -> AuthorityDto.builder().authorityName(authority.getAuthorityName()).build())
                        .collect(Collectors.toSet()))
                .build();
    }
}
