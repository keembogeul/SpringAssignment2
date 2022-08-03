package com.example.spring3.controller;

import com.example.spring3.dto.ResponseDto;
import com.example.spring3.entity.Board;
import com.example.spring3.repository.BoardRepository;
import com.example.spring3.dto.BoardRequestDto;
import com.example.spring3.service.BoardService;
import com.example.spring3.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
        Optional<Board> optionalBoard = boardRepository.findById(id);
        String author = SecurityUtil.getCurrentUsername().get();

        if (!optionalBoard.get().getAuthor().equals(author)) {
            return ResponseDto.fail("NOT_FOUND", "작성자만 수정할 수 있습니다.");
        }

        if (!optionalBoard.isPresent()) {
            return ResponseDto.fail("NOT_FOUND", "게시글이 존재하지 않습니다.");
        }
        return boardService.updateBoard(id, requestDto);
    }

    @DeleteMapping("/api/auth/post/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseDto<?> deleteBoard(@PathVariable Long id) {
        Optional<Board> optionalBoard = boardRepository.findById(id);
        String author = SecurityUtil.getCurrentUsername().get();

        if (!optionalBoard.get().getAuthor().equals(author)) {
            return ResponseDto.fail("NOT_FOUND", "작성자만 삭제할 수 있습니다.");
        }

        if (!optionalBoard.isPresent()) {
            return ResponseDto.fail("NOT_FOUND", "게시글이 존재하지 않습니다.");
        }

        return boardService.deleteBoard(id);
    }

}