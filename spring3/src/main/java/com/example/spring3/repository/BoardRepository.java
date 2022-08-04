package com.example.spring3.repository;

import com.example.spring3.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Optional<Board> findById(Long id);
    List<Board> findAllByOrderByModifiedAtDesc();
}