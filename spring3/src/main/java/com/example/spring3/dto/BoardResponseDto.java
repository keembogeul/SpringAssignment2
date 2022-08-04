package com.example.spring3.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class BoardResponseDto<T> {
    private Long postId;
    private String author;
    private String title;
    private String contents;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    private List<CommentResponseDto> commentResponseDtoList;

    public static <T> BoardResponseDto<T> getDetailBoard(Long postId, String author, String title, String contents, LocalDateTime createdAt, LocalDateTime modifiedAt, List<CommentResponseDto> commentResponseDtoList) {
        return new BoardResponseDto<>(postId, author, title, contents, createdAt, modifiedAt, commentResponseDtoList);
    }

}
