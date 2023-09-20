package com.jeongseok.portfolioboardapp.comment.repository;

import com.jeongseok.portfolioboardapp.comment.domain.Comment;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment, Long> {

//	Optional<Comment> findByBoardIndex(long boardIndex);

	@Query("select min(c.groupId) from Comment c where c.groupId = :#{#origin?.groupId} and c.groupOrder >= :#{#origin?.groupOrder} and c.depth <= :#{#origin?.depth}")
	Long getAvailableOrder(Comment origin);

	Long countByGroupId(long groupId);

	@Query("select c.groupOrder from Comment c where c.groupId = :#{#groupId} and c.groupOrder = :#{#groupOrder} and c.depth = :#{#depth}")
	int getNextGroupOrder(long groupId, long groupOrder, long depth);

	@Query("select MAX(c.groupOrder) from Comment c where c.groupId = :#{#groupId}")
	int getMaxGroupOrder(long groupId);

	@Modifying
	@Transactional
	@Query("update Comment c set c.groupOrder = c.groupOrder + 1 where c.groupId = :#{#comment.groupId} and c.groupOrder > :#{#comment.groupOrder}")
	int increaseSequence(@Param("comment") Comment comment);

	@Query("select max(c.groupId) from Comment c")
	Integer getNextGroupId();

}
