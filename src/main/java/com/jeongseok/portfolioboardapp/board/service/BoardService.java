package com.jeongseok.portfolioboardapp.board.service;

import com.jeongseok.portfolioboardapp.board.domain.Board;
import com.jeongseok.portfolioboardapp.board.dto.BoardDetailResponseDto;
import com.jeongseok.portfolioboardapp.board.dto.BoardEditForm;
import com.jeongseok.portfolioboardapp.board.dto.BoardListResponseDto;
import com.jeongseok.portfolioboardapp.board.dto.BoardWriteForm;
import com.jeongseok.portfolioboardapp.board.repository.BoardRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {

	private final BoardRepository boardRepository;


	public List<BoardListResponseDto> getBoardList() {
		return boardRepository.findAll().stream()
			.map(BoardListResponseDto::fromEntity)
			.collect(Collectors.toList());
	}

	public void writeBoard(BoardWriteForm boardWriteForm) {
		boardRepository.save(boardWriteForm.toEntity());
	}

	public BoardDetailResponseDto getBoard(long boardIndex) {
		Optional<Board> board = boardRepository.findById(boardIndex);

		return board.map(BoardDetailResponseDto::fromEntity).orElse(null);
	}

	public void deleteBoard(long boardIndex) {
		Board board = boardRepository.findById(boardIndex).orElse(null);
		boardRepository.delete(board);
	}

	public void editBoard(long boardIndex, BoardEditForm boardWriteForm) {

		Board board = boardRepository.findById(boardIndex).orElse(null);

		board.update(boardWriteForm.getTitle(), boardWriteForm.getContent());

		boardRepository.save(board);
	}
}
