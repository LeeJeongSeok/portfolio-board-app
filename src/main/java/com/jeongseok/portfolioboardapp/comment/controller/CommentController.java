package com.jeongseok.portfolioboardapp.comment.controller;

import com.jeongseok.portfolioboardapp.comment.dto.CommentWriteForm;
import com.jeongseok.portfolioboardapp.comment.service.CommentService;
import com.jeongseok.portfolioboardapp.user.domain.User;
import com.jeongseok.portfolioboardapp.util.SessionConst;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequiredArgsConstructor
public class CommentController {

	private final CommentService commentService;

	@GetMapping("/board/{boardIndex}/comment")
	public String commentWriteForm(@PathVariable long boardIndex, Model model) {
		model.addAttribute("commentWriteForm", new CommentWriteForm());

		return "comment/commentWriteForm";
	}

	@PostMapping("/board/{boardIndex}/comment")
	public String commentWrite(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) User loginMember, @PathVariable long boardIndex, @Valid @ModelAttribute CommentWriteForm commentWriteForm, BindingResult bindingResult, Model model) {
		if (loginMember == null) {
			System.out.println("답글을 작성할 수 없습니다.");
		}

		if (bindingResult.hasErrors()) {
			bindingResult.addError(new FieldError("coomentWriteForm", "content", "내용은 빈칸으로 입력될 수 없습니다."));
		}

		commentService.writeComment(boardIndex, loginMember.getUserId(), commentWriteForm);

		return "redirect:/board/{boardIndex}";
	}

}
