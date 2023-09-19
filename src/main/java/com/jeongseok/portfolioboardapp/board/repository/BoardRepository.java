package com.jeongseok.portfolioboardapp.board.repository;

import com.jeongseok.portfolioboardapp.board.domain.Board;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

	List<Board> findAllByUseYnOrderByCreatedAtDesc(String useYn);

}
