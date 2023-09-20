package com.jeongseok.portfolioboardapp.comment.service;

import com.jeongseok.portfolioboardapp.board.domain.Board;
import com.jeongseok.portfolioboardapp.board.repository.BoardRepository;
import com.jeongseok.portfolioboardapp.comment.domain.Comment;
import com.jeongseok.portfolioboardapp.comment.dto.CommentWriteForm;
import com.jeongseok.portfolioboardapp.comment.repository.CommentRepository;
import com.jeongseok.portfolioboardapp.type.UseType;
import com.jeongseok.portfolioboardapp.user.domain.User;
import com.jeongseok.portfolioboardapp.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

	private final CommentRepository commentRepository;
	private final BoardRepository boardRepository;
	private final UserRepository userRepository;

	public void writeComment(long boardIndex, String userId, CommentWriteForm commentWriteForm) {

		Board board = boardRepository.findById(boardIndex)
			.orElseThrow(() -> new IllegalArgumentException("해당 게시글을 찾을 수 없습니다."));

		User user = userRepository.findByUserId(userId)
			.orElseThrow(() -> new IllegalArgumentException("해당 유저를 챶을 수 없습니다."));

		commentRepository.save(Comment.builder()
			.content(commentWriteForm.getContent())
			.board(board)
			.user(user)
			.content(commentWriteForm.getContent())
			.groupId(getNextGroupId() + 1)
			.groupOrder(0)
			.depth(1)
			.useYn(UseType.Y)
			.build());
	}

//	public void writeHierarchicalComment(long boardIndex, String userId, CommentWriteForm commentWriteForm) {
//
//		Board board = boardRepository.findById(boardIndex)
//			.orElseThrow(() -> new IllegalArgumentException("해당 게시글을 찾을 수 없습니다."));
//
//		User user = userRepository.findByUserId(userId)
//			.orElseThrow(() -> new IllegalArgumentException("해당 유저를 챶을 수 없습니다."));
//
//		Comment comment = commentRepository.findByBoardIndex(boardIndex)
//			.orElseThrow(() -> new IllegalArgumentException("해당 게시글을 찾을 수 없습니다."));
//
//		Long groupOrder = commentRepository.getAvailableOrder(comment);
//
//		if (groupOrder == null) {
//			groupOrder = commentRepository.countByGroupId(comment.getGroupId());
//		} else {
//			groupOrder = getNextGroupOrder(comment);
//		}
//
//		commentRepository.save(Comment.builder()
//			.content(commentWriteForm.getContent())
//			.board(board)
//			.user(user)
//			.groupId(comment.getGroupId())
//			.groupOrder(groupOrder)
//			.depth(comment.getDepth() + 1)
//			.useYn(UseType.Y)
//			.build());
//	}

	private long getNextGroupOrder(Comment parentComment) {
		int nextGroupOrder = commentRepository.getNextGroupOrder(parentComment.getGroupId(), parentComment.getGroupOrder(), parentComment.getDepth());

		if (nextGroupOrder == 0) {
			// 댓글만 달리는 경우
			nextGroupOrder = commentRepository.getMaxGroupOrder(parentComment.getGroupId()) + 1;
		} else {
			// 댓글과 댓글 사이에 대댓글이 달리는 경우
			commentRepository.increaseSequence(parentComment);
			nextGroupOrder++;
		}

		return nextGroupOrder;
	}

	private int getNextGroupId() {
		if (commentRepository.getNextGroupId() == null) {
			return 0;
		}

		return commentRepository.getNextGroupId();
	}
}
