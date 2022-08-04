package com.example.spring3.entity;

import com.example.spring3.dto.BoardRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Board extends Timestamped {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "postId")
    private Long postId;

    @Column
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String contents;


    public Board(BoardRequestDto requestDto, String author) {
        this.author = author;
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
    }

    public void update(BoardRequestDto requestDto, String author) {
        this.author = author;
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
    }


}