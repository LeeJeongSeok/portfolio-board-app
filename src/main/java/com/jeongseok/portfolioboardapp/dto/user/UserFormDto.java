package com.jeongseok.portfolioboardapp.dto.user;

import com.jeongseok.portfolioboardapp.domain.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class UserFormDto {

	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class UserJoinForm {

		private long userIndex;

		@NotBlank(message = "아이디는 빈칸으로 입력될 수 없습니다.")
		private String userId;

		@Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 영문 대 소문자, 숫자, 특수문자를 사용해주세요.")
		private String password;

		@NotBlank(message = "이름은 빈칸으로 입력될 수 없습니다.")
		private String userName;

		/**
		 * DTO -> Entity
		 */
		public User toEntity() {
			return User.builder()
				.userId(userId)
				.password(password)
				.userName(userName)
				.useYn("Y")
				.build();
		}
	}

}
