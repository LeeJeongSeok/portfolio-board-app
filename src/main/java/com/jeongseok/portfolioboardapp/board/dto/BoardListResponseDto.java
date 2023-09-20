package com.jeongseok.portfolioboardapp.board.dto;

import com.jeongseok.portfolioboardapp.board.domain.Board;
import com.jeongseok.portfolioboardapp.comment.dto.CommentDto;
import com.jeongseok.portfolioboardapp.user.domain.User;
import java.util.List;
import java.util.stream.Collectors;
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
	private User user;
	private List<CommentDto> comments;
	private String createdAt;

	public static BoardListResponseDto fromEntity(Board board) {
		return BoardListResponseDto.builder()
			.boardIndex(board.getBoardIndex())
			.title(board.getTitle())
			.user(board.getUser())
			.comments(board.getComments().stream().map(CommentDto::fromEntity).collect(Collectors.toList()))
			.createdAt(String.valueOf(board.getCreatedAt()))
			.build();
	}

}
