package com.jeongseok.portfolioboardapp.board.dto;

import com.jeongseok.portfolioboardapp.board.domain.Board;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardListResponseDto {

	private long boardIndex;
	private String title;
	private String name;
	private String createdAt;

	public static BoardListResponseDto fromEntity(Board board) {
		return BoardListResponseDto.builder()
			.boardIndex(board.getBoardIndex())
			.title(board.getTitle())
			.name(board.getName())
			.createdAt(String.valueOf(board.getCreatedAt()))
			.build();
	}

}
