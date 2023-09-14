package com.jeongseok.portfolioboardapp.board.service;

import com.jeongseok.portfolioboardapp.board.dto.BoardListResponseDto;
import com.jeongseok.portfolioboardapp.board.repository.BoardRepository;
import java.util.List;
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
}
