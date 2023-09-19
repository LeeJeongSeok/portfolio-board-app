package com.jeongseok.portfolioboardapp.board.service;

import com.jeongseok.portfolioboardapp.board.domain.Board;
import com.jeongseok.portfolioboardapp.board.dto.BoardDetailResponseDto;
import com.jeongseok.portfolioboardapp.board.dto.BoardEditForm;
import com.jeongseok.portfolioboardapp.board.dto.BoardListResponseDto;
import com.jeongseok.portfolioboardapp.board.dto.BoardWriteForm;
import com.jeongseok.portfolioboardapp.board.repository.BoardRepository;
import com.jeongseok.portfolioboardapp.type.UseType;
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
			.useYn(UseType.Y)
			.build());
	}

	public BoardDetailResponseDto getBoard(long boardIndex) {
		Board board = boardRepository.findById(boardIndex)
			.orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));

		return BoardDetailResponseDto.fromEntity(board);
	}

	@Transactional
	public void deleteBoard(long boardIndex, String userId) {

		Board board = boardRepository.findById(boardIndex)
			.orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));

		if (!board.getUserId().equals(userId)) {
			throw new IllegalArgumentException("로그인한 유저와 작성한 유저 정보가 다릅니다.");
		}

		board.delete(UseType.N);
	}

	@Transactional
	public void editBoard(long boardIndex, String userId, BoardEditForm boardWriteForm) {

		Board board = boardRepository.findById(boardIndex)
			.orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));

		if (!board.getUserId().equals(userId)) {
			throw new IllegalArgumentException("로그인한 유저와 작성한 유저 정보가 다릅니다.");
		}

		board.update(boardWriteForm.getTitle(), boardWriteForm.getContent());

	}
}
