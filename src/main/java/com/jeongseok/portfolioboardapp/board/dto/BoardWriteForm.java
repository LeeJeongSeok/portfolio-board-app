package com.jeongseok.portfolioboardapp.board.dto;

import com.jeongseok.portfolioboardapp.board.domain.Board;
import jakarta.validation.constraints.NotBlank;
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
public class BoardWriteForm {

	@NotBlank(message = "제목은 빈칸으로 입력될 수 없습니다.")
	private String title;

	@NotBlank(message = "내용은 빈칸으로 입력될 수 없습니다.")
	private String content;

	private String name;

	/**
	 * DTO -> Entity
	 */

	public Board toEntity() {
		return Board.builder()
			.title(title)
			.content(content)
			.name(name)
			.build();
	}
}
