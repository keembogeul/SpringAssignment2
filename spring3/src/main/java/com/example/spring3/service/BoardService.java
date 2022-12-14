package com.example.spring3.service;

import com.example.spring3.dto.BoardRequestDto;
import com.example.spring3.dto.BoardResponseDto;
import com.example.spring3.dto.ResponseDto;
import com.example.spring3.entity.Board;
import com.example.spring3.repository.BoardRepository;
import com.example.spring3.repository.CommentRepository;
import com.example.spring3.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public ResponseDto<?> createBoard(BoardRequestDto requestDto) {
        String author = SecurityUtil.getCurrentUsername().get();
        Board board = new Board(requestDto, author);
        boardRepository.save(board);

        return ResponseDto.success(board);
    }

    @Transactional
    public ResponseDto<?> getBoard(Long id) {

        Optional<Board> optionalBoard = boardRepository.findById(id);

        if (!optionalBoard.isPresent()) {
            return ResponseDto.fail("NOT_FOUND", "게시글이 존재하지 않습니다.");
        }

        return ResponseDto.success(BoardResponseDto.getDetailBoard(
                optionalBoard.get().getPostId(),
                optionalBoard.get().getAuthor(),
                optionalBoard.get().getTitle(),
                optionalBoard.get().getContents(),
                optionalBoard.get().getCreatedAt(),
                optionalBoard.get().getModifiedAt(),
                commentRepository.findAllByPostId(id)
        ));
    }

    @Transactional
    public ResponseDto<?> getAllBoard() {
        return ResponseDto.success(boardRepository.findAllByOrderByModifiedAtDesc());
    }

    @Transactional
    public ResponseDto<Board> updateBoard(Long id, BoardRequestDto requestDto) {
        Optional<Board> optionalBoard = boardRepository.findById(id);
        String author = SecurityUtil.getCurrentUsername().get();

        if (!optionalBoard.get().getAuthor().equals(author)) {
            return ResponseDto.fail("NOT_FOUND", "작성자만 수정할 수 있습니다.");
        }

        if (!optionalBoard.isPresent()) {
            return ResponseDto.fail("NOT_FOUND", "게시글이 존재하지 않습니다.");
        }

        Board board = optionalBoard.get();
        board.update(requestDto, author);

        return ResponseDto.success(board);
    }

    @Transactional
    public ResponseDto<?> deleteBoard(Long id) {
        Optional<Board> optionalBoard = boardRepository.findById(id);
        String author = SecurityUtil.getCurrentUsername().get();
        if (!optionalBoard.get().getAuthor().equals(author)) {
            return ResponseDto.fail("NOT_FOUND", "작성자만 삭제할 수 있습니다.");
        }

        if (!optionalBoard.isPresent()) {
            return ResponseDto.fail("NOT_FOUND", "게시글이 존재하지 않습니다.");
        }
        Board board = optionalBoard.get();
        boardRepository.delete(board);
        commentRepository.deleteByPostId(id);

        return ResponseDto.success("delete success");
    }

}