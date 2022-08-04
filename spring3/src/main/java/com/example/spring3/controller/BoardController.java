package com.example.spring3.controller;

import com.example.spring3.dto.ResponseDto;
import com.example.spring3.repository.BoardRepository;
import com.example.spring3.dto.BoardRequestDto;
import com.example.spring3.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class BoardController {
    private final BoardRepository boardRepository;
    private final BoardService boardService;

    @GetMapping("/api/post")
    public ResponseDto<?> getAllBoard() {
        return boardService.getAllBoard();
    }

    @GetMapping("/api/post/{id}")
    public ResponseDto<?> getBoard(@PathVariable Long id) {
        return boardService.getBoard(id);
    }

    @PostMapping("/api/auth/post")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseDto<?> createBoard(@RequestBody BoardRequestDto requestDto) {
        return boardService.createBoard(requestDto);
    }

    @PutMapping("/api/auth/post/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseDto<?> updateBoard(@PathVariable Long id, @RequestBody BoardRequestDto requestDto) {
        return boardService.updateBoard(id, requestDto);
    }

    @DeleteMapping("/api/auth/post/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseDto<?> deleteBoard(@PathVariable Long id) {
        return boardService.deleteBoard(id);
    }

}