package com.jeongseok.portfolioboardapp.comment.dto;

import com.jeongseok.portfolioboardapp.board.domain.Board;
import com.jeongseok.portfolioboardapp.comment.domain.Comment;
import com.jeongseok.portfolioboardapp.type.UseType;
import com.jeongseok.portfolioboardapp.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDto {

	private long commentIndex;
	private String content;
	private Board board;
	private User user;
	private long groupId;
	private long groupOrder;
	private long depth;
	private UseType useYn;

	public static CommentDto fromEntity(Comment comment) {
		return CommentDto.builder()
			.commentIndex(comment.getCommentIndex())
			.content(comment.getContent())
			.board(comment.getBoard())
			.user(comment.getUser())
			.groupId(comment.getGroupId())
			.groupOrder(comment.getGroupOrder())
			.depth(comment.getDepth())
			.useYn(comment.getUseYn())
			.build();
	}

}
