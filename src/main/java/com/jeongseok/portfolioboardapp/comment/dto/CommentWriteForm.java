package com.jeongseok.portfolioboardapp.comment.dto;

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
public class CommentWriteForm {

	@NotBlank(message = "내용은 빈칸으로 입력될 수 없습니다.")
	private String content;
}
