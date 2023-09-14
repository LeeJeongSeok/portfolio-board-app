package com.jeongseok.portfolioboardapp.controller;

import com.jeongseok.portfolioboardapp.domain.User;
import com.jeongseok.portfolioboardapp.dto.user.UserFormDto;
import com.jeongseok.portfolioboardapp.dto.user.UserFormDto.UserJoinForm;
import com.jeongseok.portfolioboardapp.dto.user.UserFormDto.UserLoginForm;
import com.jeongseok.portfolioboardapp.service.UserService;
import com.jeongseok.portfolioboardapp.util.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
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

	@GetMapping("/login")
	public String loginForm(Model model) {
		model.addAttribute("userLoginForm", new UserLoginForm());
		return "user/loginForm";
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

	@PostMapping("/login")
	public String login(@Valid @ModelAttribute UserFormDto.UserLoginForm userLoginForm, BindingResult bindingResult, HttpServletRequest request) {

		if (bindingResult.hasErrors()) {
			return "user/loginForm";
		}

		// DB에 해당 유저 조회
		User loginUser = userService.login(userLoginForm.getUserId(), userLoginForm.getPassword());

		if (loginUser == null) {
			bindingResult.reject("login Failed",  "아이디 또는 비밀번호가 일치하지 않습니다.");
			return "user/loginForm";
		}

		// 세션이 있으면 있는 세션 반환, 없으면 신규 세션 생성
		HttpSession session = request.getSession();
		// 세션에 로그인 회원 정보 보관
		session.setAttribute(SessionConst.LOGIN_MEMBER, loginUser);

		return "redirect:/";
	}

	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {

		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}

		return "redirect:/";
	}

}
