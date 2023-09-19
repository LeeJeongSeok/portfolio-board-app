package com.jeongseok.portfolioboardapp.board.service;

import com.jeongseok.portfolioboardapp.board.domain.Board;
import com.jeongseok.portfolioboardapp.board.dto.BoardDetailResponseDto;
import com.jeongseok.portfolioboardapp.board.dto.BoardEditForm;
import com.jeongseok.portfolioboardapp.board.dto.BoardListResponseDto;
import com.jeongseok.portfolioboardapp.board.dto.BoardWriteForm;
import com.jeongseok.portfolioboardapp.board.repository.BoardRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService {

	private final BoardRepository boardRepository;


	public List<BoardListResponseDto> getBoardList() {
		return boardRepository.findAllByUseYnOrderByCreatedAtDesc("Y").stream()
			.map(BoardListResponseDto::fromEntity)
			.collect(Collectors.toList());
	}

	@Transactional
	public void writeBoard(BoardWriteForm boardWriteForm, String userId) {

		boardRepository.save(Board.builder()
			.title(boardWriteForm.getTitle())
			.content(boardWriteForm.getContent())
			.userId(userId)
			.useYn("Y")
			.build());
	}

	public BoardDetailResponseDto getBoard(long boardIndex) {
		Board board = boardRepository.findById(boardIndex)
			.orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));

		return BoardDetailResponseDto.fromEntity(board);
	}

	public void deleteBoard(long boardIndex, String userId) {
		Board board = boardRepository.findById(boardIndex).orElse(null);

		board.delete("N");
	}

	public void editBoard(long boardIndex, BoardEditForm boardWriteForm) {

		Board board = boardRepository.findById(boardIndex).orElse(null);

		board.update(boardWriteForm.getTitle(), boardWriteForm.getContent());

//		boardRepository.save(board);
	}
}
