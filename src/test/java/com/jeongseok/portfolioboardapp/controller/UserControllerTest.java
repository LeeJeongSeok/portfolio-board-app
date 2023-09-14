package com.jeongseok.portfolioboardapp.controller;

import com.jeongseok.portfolioboardapp.user.dto.user.UserFormDto.UserJoinForm;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class UserControllerTest {

	ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	Validator validator = factory.getValidator();

	@Test
	void 회원가입_입력폼_전체_통과() {
		UserJoinForm userJoinForm = new UserJoinForm();
		userJoinForm.setUserId("test");
		userJoinForm.setPassword("1q2w3e4r123!@#");
		userJoinForm.setUserName("테스트");

		Set<ConstraintViolation<UserJoinForm>> violations = validator.validate(userJoinForm);
		List<String> messages = new ArrayList<>();

		for (ConstraintViolation<UserJoinForm> violation : violations) {
			messages.add(violation.getMessage());
			System.out.println("violation.message=" + violation.getMessage());
		}

		Assertions.assertThat(messages).isEmpty();
	}

	@Test
	void 회원가입_입력폼_테스트_전부공백() {

		UserJoinForm userJoinForm = new UserJoinForm();
		userJoinForm.setUserId(" ");
		userJoinForm.setPassword(" ");
		userJoinForm.setUserName(" ");

		Set<ConstraintViolation<UserJoinForm>> violations = validator.validate(userJoinForm);
		List<String> messages = new ArrayList<>();

		for (ConstraintViolation<UserJoinForm> violation : violations) {
			messages.add(violation.getMessage());
			System.out.println("violation.message=" + violation.getMessage());
		}

		Assertions.assertThat(messages)
			.contains("아이디는 빈칸으로 입력될 수 없습니다.", "비밀번호는 영문 대 소문자, 숫자, 특수문자를 사용해주세요.",
				"이름은 빈칸으로 입력될 수 없습니다.");
	}

	@Test
	void 회원가입_입력폼_테스트_아이디_공백() {
		UserJoinForm userJoinForm = new UserJoinForm();
		userJoinForm.setUserId(" ");
		userJoinForm.setPassword("1q2w3e4r123!@#");
		userJoinForm.setUserName("테스트");

		Set<ConstraintViolation<UserJoinForm>> violations = validator.validate(userJoinForm);
		List<String> messages = new ArrayList<>();

		for (ConstraintViolation<UserJoinForm> violation : violations) {
			messages.add(violation.getMessage());
			System.out.println("violation.message=" + violation.getMessage());
		}

		Assertions.assertThat(messages).contains("아이디는 빈칸으로 입력될 수 없습니다.");
	}

	@Test
	void 회원가입_입력폼_테스트_비밀번호_공백() {
		UserJoinForm userJoinForm = new UserJoinForm();
		userJoinForm.setUserId("test");
		userJoinForm.setPassword(" ");
		userJoinForm.setUserName("테스트");

		Set<ConstraintViolation<UserJoinForm>> violations = validator.validate(userJoinForm);
		List<String> messages = new ArrayList<>();

		for (ConstraintViolation<UserJoinForm> violation : violations) {
			messages.add(violation.getMessage());
			System.out.println("violation.message=" + violation.getMessage());
		}

		Assertions.assertThat(messages).contains("비밀번호는 영문 대 소문자, 숫자, 특수문자를 사용해주세요.");
	}


	@Test
	void 회원가입_입력폼_테스트_비밀번호_정규식() {
		UserJoinForm userJoinForm = new UserJoinForm();
		userJoinForm.setUserId("test");
		userJoinForm.setPassword("1234");
		userJoinForm.setUserName("테스트");

		Set<ConstraintViolation<UserJoinForm>> violations = validator.validate(userJoinForm);
		List<String> messages = new ArrayList<>();

		for (ConstraintViolation<UserJoinForm> violation : violations) {
			messages.add(violation.getMessage());
			System.out.println("violation.message=" + violation.getMessage());
		}

		Assertions.assertThat(messages).contains("비밀번호는 영문 대 소문자, 숫자, 특수문자를 사용해주세요.");
	}

	@Test
	void 회원가입_입력폼_테스트_이름_공백() {
		UserJoinForm userJoinForm = new UserJoinForm();
		userJoinForm.setUserId("test");
		userJoinForm.setPassword("1234");
		userJoinForm.setUserName(" ");

		Set<ConstraintViolation<UserJoinForm>> violations = validator.validate(userJoinForm);
		List<String> messages = new ArrayList<>();

		for (ConstraintViolation<UserJoinForm> violation : violations) {
			messages.add(violation.getMessage());
			System.out.println("violation.message=" + violation.getMessage());
		}

		Assertions.assertThat(messages).contains("이름은 빈칸으로 입력될 수 없습니다.");
	}
}