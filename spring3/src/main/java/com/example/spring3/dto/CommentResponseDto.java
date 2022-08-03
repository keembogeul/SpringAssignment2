package com.example.spring3.dto;

import com.example.spring3.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class CommentResponseDto<T> {
   private Long commentId;
   private String author;
   private String content;
   private LocalDateTime createdAt;
   private LocalDateTime modifiedAt;

    public static <T> CommentResponseDto<T> commentList(Long commentId, String author, String content, LocalDateTime createdAt, LocalDateTime modifiedAt) {
       return new CommentResponseDto<>(commentId, author, content, createdAt, modifiedAt);
   }
}
