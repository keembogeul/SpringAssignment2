package com.example.spring3.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CommentResponseDto<T> {
   private Long commentId;
   private String author;
   private String content;
   private LocalDateTime createdAt;
   private LocalDateTime modifiedAt;

}
