package com.jeongseok.portfolioboardapp.board.controller;

import com.jeongseok.portfolioboardapp.board.dto.BoardListResponseDto;
import com.jeongseok.portfolioboardapp.board.dto.BoardWriteForm;
import com.jeongseok.portfolioboardapp.board.service.BoardService;
import com.jeongseok.portfolioboardapp.user.domain.User;
import com.jeongseok.portfolioboardapp.util.SessionConst;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequiredArgsConstructor
public class BoardController {

	private final BoardService boardService;

	@GetMapping("/")
	public String index(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) User loginMember, Model model) {

		List<BoardListResponseDto> boardList = boardService.getBoardList();
		model.addAttribute("boardList", boardList);

		if (loginMember == null) {
			return "index";
		}

		model.addAttribute("user", loginMember);

		return "index";
	}

	@GetMapping("/board")
	public String writeBoardForm(Model model) {
		model.addAttribute("boardWriteForm", new BoardWriteForm());
		return "board/writeBoardForm";
	}

	@PostMapping("/board")
	public String writeBoard(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) User loginMember, @Valid @ModelAttribute BoardWriteForm boardWriteForm, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return "board/writeBoardForm";
		}

		boardWriteForm.setName(loginMember.getUserName());
		boardService.writeBoard(boardWriteForm);

		return "redirect:/";
	}


}
