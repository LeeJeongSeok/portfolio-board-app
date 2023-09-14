package com.jeongseok.portfolioboardapp.board.domain;

import com.jeongseok.portfolioboardapp.user.domain.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "board")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Board extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "board_index")
	private long boardIndex;

	@Column(name = "title")
	private String title;

	@Column(name = "content")
	private String content;

	@Column(name = "name")
	private String name;

	@Column(name = "use_yn")
	private String useYn;

	public void update(String title, String content) {
		this.title = title;
		this.content = content;

	}
}
