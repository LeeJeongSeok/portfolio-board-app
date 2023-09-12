package com.jeongseok.portfolioboardapp.controller;

import com.jeongseok.portfolioboardapp.dto.user.UserFormDto;
import com.jeongseok.portfolioboardapp.dto.user.UserFormDto.UserJoinForm;
import com.jeongseok.portfolioboardapp.service.UserService;
import jakarta.validation.Valid;
import java.io.File;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@GetMapping("/join")
	public String joinForm(Model model) {
		model.addAttribute("userJoinForm", new UserJoinForm());
		return "user/joinForm";
	}

	@PostMapping("/join")
	public String join(@Valid @ModelAttribute UserFormDto.UserJoinForm userJoinForm, BindingResult bindingResult) {

		// 이미 존재하는 회원 검사
		if (userService.isDuplicate(userJoinForm.getUserId())) {
			bindingResult.addError(new FieldError("userJoinForm", "userId", "이미 존재하는 회원입니다."));
		}

		if (bindingResult.hasErrors()) {
			return "user/joinForm";
		}

		userService.join(userJoinForm);

		return "redirect:/join";
	}

}
