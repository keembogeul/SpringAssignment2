package com.example.spring3.repository;

import com.example.spring3.dto.CommentResponseDto;
import com.example.spring3.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<CommentResponseDto> findAllByPostId(Long postId);
    Optional<Comment> findByCommentId(Long commentId);
}
