package com.example.spring3.service;

import com.example.spring3.dto.CommentRequestDto;
import com.example.spring3.dto.CommentResponseDto;
import com.example.spring3.dto.ResponseDto;
import com.example.spring3.entity.Board;
import com.example.spring3.entity.Comment;
import com.example.spring3.repository.BoardRepository;
import com.example.spring3.repository.CommentRepository;
import com.example.spring3.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public ResponseDto<?> createComment(CommentRequestDto requestDto) {
        Optional<Board> optionalBoard = boardRepository.findById(requestDto.getPostId());

        if (!optionalBoard.isPresent()) {
            return ResponseDto.fail("NOT_FOUND", "게시글이 존재하지 않습니다.");
        }

        String author = SecurityUtil.getCurrentUsername().get();
        Comment comment = new Comment(requestDto, author);
        commentRepository.save(comment);

        return ResponseDto.success(comment);
    }

    @Transactional
    public ResponseDto<?> getComments(Long postId) {
        List<CommentResponseDto> commentList = commentRepository.findAllByPostId(postId);

        if (commentList.isEmpty()) {
            return ResponseDto.fail("NOT_FOUND", "게시글이 존재하지 않습니다.");
        }

        return ResponseDto.success(commentList);
    }

    @Transactional
    public ResponseDto<?> updateComment(Long commentId, CommentRequestDto requestDto) {
        Optional<Board> optionalBoard = boardRepository.findById(requestDto.getPostId());
        Optional<Comment> optionalComment = commentRepository.findByCommentId(commentId);
        String author = SecurityUtil.getCurrentUsername().get();

        if (!optionalBoard.isPresent()) {
            return ResponseDto.fail("NOT_FOUND", "게시글이 존재하지 않습니다.");
        }

        if (!optionalComment.get().getAuthor().equals(author)) {
            return ResponseDto.fail("NOT_FOUND", "작성자만 수정할 수 있습니다.");
        }

        if (!optionalComment.isPresent()) {
            return ResponseDto.fail("NOT_FOUND", "댓글이 존재하지 않습니다.");
        }

        Comment comment = optionalComment.get();
        comment.update(requestDto, author);

        return ResponseDto.success(comment);
    }

    public ResponseDto<?> deleteComment(Long commentId) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        String author = SecurityUtil.getCurrentUsername().get();

        if (!optionalComment.isPresent()) {
            return ResponseDto.fail("NOT_FOUND", "댓글이 존재하지 않습니다.");
        }

        if (!optionalComment.get().getAuthor().equals(author)) {
            return ResponseDto.fail("NOT_FOUND", "작성자만 삭제할 수 있습니다.");
        }

        Comment comment = optionalComment.get();

        commentRepository.delete(comment);

        return ResponseDto.success("success");
    }
}
