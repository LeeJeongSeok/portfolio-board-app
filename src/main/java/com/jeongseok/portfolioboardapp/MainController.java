package com.jeongseok.portfolioboardapp;

import com.jeongseok.portfolioboardapp.user.domain.User;
import com.jeongseok.portfolioboardapp.util.SessionConst;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

public class MainController {

	@GetMapping("/")
	public String index(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) User loginMember, Model model) {

		if (loginMember == null) {
			return "index";
		}

		model.addAttribute("user", loginMember);

		return "index";
	}
}
