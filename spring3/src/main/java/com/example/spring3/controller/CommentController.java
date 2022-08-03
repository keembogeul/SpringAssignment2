package com.example.spring3.controller;

import com.example.spring3.dto.CommentRequestDto;
import com.example.spring3.dto.ResponseDto;
import com.example.spring3.entity.Board;
import com.example.spring3.entity.Comment;
import com.example.spring3.repository.BoardRepository;
import com.example.spring3.repository.CommentRepository;
import com.example.spring3.service.BoardService;
import com.example.spring3.service.CommentService;
import com.example.spring3.service.UserDetailsImpl;
import com.example.spring3.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
        Optional<Comment> optionalComment = commentRepository.findById(id);
        String author = SecurityUtil.getCurrentUsername().get();

        if (!optionalComment.get().getAuthor().equals(author)) {
            return ResponseDto.fail("NOT_FOUND", "작성자만 수정할 수 있습니다.");
        }

        if (!optionalComment.isPresent()) {
            return ResponseDto.fail("NOT_FOUND", "댓글이 존재하지 않습니다.");
        }
        return commentService.updateComment(id, requestDto);
    }

    @DeleteMapping("/auth/comment/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseDto<?> deleteComment(@PathVariable Long id){
        Optional<Comment> optionalComment = commentRepository.findById(id);
        String author = SecurityUtil.getCurrentUsername().get();

        if (!optionalComment.get().getAuthor().equals(author)) {
            return ResponseDto.fail("NOT_FOUND", "작성자만 삭제할 수 있습니다.");
        }

        if (!optionalComment.isPresent()) {
            return ResponseDto.fail("NOT_FOUND", "댓글이 존재하지 않습니다.");
        }
        return commentService.deleteComment(id);
     }
}
