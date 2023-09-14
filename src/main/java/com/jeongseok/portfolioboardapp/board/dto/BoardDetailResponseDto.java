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
public class BoardDetailResponseDto {

	private String title;
	private String content;
	private String name;
	private String createdAt;

	/**
	 * Entity -> DTO
	 */
	public static BoardDetailResponseDto fromEntity(Board board) {
		return BoardDetailResponseDto.builder()
			.title(board.getTitle())
			.content(board.getContent())
			.name(board.getName())
			.createdAt(String.valueOf(board.getCreatedAt()))
			.build();
	}

}
