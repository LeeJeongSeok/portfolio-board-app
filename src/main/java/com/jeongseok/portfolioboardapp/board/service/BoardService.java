package com.jeongseok.portfolioboardapp.board.service;

import com.jeongseok.portfolioboardapp.board.domain.Board;
import com.jeongseok.portfolioboardapp.board.dto.BoardDetailResponseDto;
import com.jeongseok.portfolioboardapp.board.dto.BoardEditForm;
import com.jeongseok.portfolioboardapp.board.dto.BoardListResponseDto;
import com.jeongseok.portfolioboardapp.board.dto.BoardWriteForm;
import com.jeongseok.portfolioboardapp.board.repository.BoardRepository;
import com.jeongseok.portfolioboardapp.type.UseType;
import com.jeongseok.portfolioboardapp.user.domain.User;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService {

	private final BoardRepository boardRepository;


	public List<BoardListResponseDto> getBoardList() {
		return boardRepository.findAllByUseYnOrderByCreatedAtAsc(UseType.Y).stream()
			.map(BoardListResponseDto::fromEntity)
			.collect(Collectors.toList());
	}

	@Transactional
	public void writeBoard(BoardWriteForm boardWriteForm, User user) {

		boardRepository.save(Board.builder()
			.title(boardWriteForm.getTitle())
			.content(boardWriteForm.getContent())
			.user(user)
			.useYn(UseType.Y)
			.build());
	}

	public BoardDetailResponseDto getBoard(long boardIndex) {
		Board board = boardRepository.findById(boardIndex)
			.orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));

		return BoardDetailResponseDto.fromEntity(board);
	}

	@Transactional
	public void deleteBoard(long boardIndex, String loginId) {

		Board board = boardRepository.findById(boardIndex)
			.orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));

		if (!board.getUser().getUserId().equals(loginId)) {
			throw new IllegalArgumentException("로그인한 유저와 작성한 유저 정보가 다릅니다.");
		}

		board.delete(UseType.N);
	}

	@Transactional
	public void editBoard(long boardIndex, String loginId, BoardEditForm boardWriteForm) {

		Board board = boardRepository.findById(boardIndex)
			.orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));

		if (!board.getUser().getUserId().equals(loginId)) {
			throw new IllegalArgumentException("로그인한 유저와 작성한 유저 정보가 다릅니다.");
		}

		board.update(boardWriteForm.getTitle(), boardWriteForm.getContent());

	}

	public Page<BoardListResponseDto> findPaginated(Pageable pageable) {

		int pageSize = pageable.getPageSize();
		int currentPage = pageable.getPageNumber();
		int startItem = currentPage * pageSize;

		List<BoardListResponseDto> boardList = boardRepository.findAllByUseYnOrderByCreatedAtAsc(UseType.Y).stream()
			.map(BoardListResponseDto::fromEntity)
			.collect(Collectors.toList());


		List<BoardListResponseDto> boardPageList;

		if (boardList.size() < startItem) {
			boardPageList = Collections.emptyList();
		} else {
			int toIndex = Math.min(startItem + pageSize, boardList.size());
			boardPageList = boardList.subList(startItem, toIndex);
		}

		return new PageImpl<>(boardPageList, PageRequest.of(currentPage, pageSize), boardList.size());

	}
}
