package com.example.spring3.entity;

import com.example.spring3.dto.CommentRequestDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Comment extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long postId;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String content;

    public Comment(CommentRequestDto requestDto, String author) {
        this.postId = requestDto.getPostId();
        this.author = author;
        this.content = requestDto.getContent();
    }

    public void update(CommentRequestDto requestDto, String author) {
        this.postId = requestDto.getPostId();
        this.author = author;
        this.content = requestDto.getContent();
    }
}
