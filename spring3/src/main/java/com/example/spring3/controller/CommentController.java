package com.example.spring3.controller;

import com.example.spring3.dto.CommentRequestDto;
import com.example.spring3.dto.ResponseDto;
import com.example.spring3.repository.BoardRepository;
import com.example.spring3.repository.CommentRepository;
import com.example.spring3.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {

    private final CommentRepository commentRepository;
    private final CommentService commentService;
    private final BoardRepository boardRepository;

    @GetMapping("/comment/{postId}")
    public ResponseDto<?> getComments(@PathVariable Long postId) {
        return commentService.getComments(postId);
    }

    @PostMapping("/auth/comment")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseDto<?> createComment(@RequestBody CommentRequestDto requestDto) {
        return commentService.createComment(requestDto);
    }

    @PutMapping("/auth/comment/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseDto<?> updateComment(@PathVariable Long id, @RequestBody CommentRequestDto requestDto) {
        return commentService.updateComment(id, requestDto);
    }

    @DeleteMapping("/auth/comment/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseDto<?> deleteComment(@PathVariable Long id){
        return commentService.deleteComment(id);
     }
}
