package com.jeongseok.portfolioboardapp.board.dto;

import com.jeongseok.portfolioboardapp.board.domain.Board;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardEditForm {

	private String title;
	private String content;

	/**
	 * Entity -> DTO
	 */
	public static BoardDetailResponseDto fromEntity(Board board) {
		return BoardDetailResponseDto.builder()
			.boardIndex(board.getBoardIndex())
			.title(board.getTitle())
			.content(board.getContent())
			.build();
	}
}
