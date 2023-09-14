package com.jeongseok.portfolioboardapp.board.controller;

import com.jeongseok.portfolioboardapp.board.dto.BoardDetailResponseDto;
import com.jeongseok.portfolioboardapp.board.dto.BoardEditForm;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

	@GetMapping("/board/{boardIndex}")
	public String boardDetail(@PathVariable long boardIndex, Model model) {

		BoardDetailResponseDto board = boardService.getBoard(boardIndex);

		if (board == null) {
			System.out.println("게시글을 찾을 수 없습니다.");
		}

		model.addAttribute("board", board);

		return "board/boardDetailForm";
	}

	@GetMapping("/board/{boardIndex}/edit")
	public String boardEditForm(@PathVariable long boardIndex, Model model) {

		BoardDetailResponseDto board = boardService.getBoard(boardIndex);

		if (board == null) {
			System.out.println("게시글을 찾을 수 없습니다.");
		}


		return "board/boardEditForm";
	}

	@PatchMapping("/board/{boardIndex}/edit")
	public String boardEdit(@PathVariable long boardIndex, @Valid @ModelAttribute BoardEditForm boardEditForm, BindingResult bindingResult) {

		BoardDetailResponseDto board = boardService.getBoard(boardIndex);

		if (board == null) {
			System.out.println("게시글을 찾을 수 없습니다.");
		}

		boardService.editBoard(boardIndex, boardEditForm);

		return "redirect:/board/{boardIndex}";
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

	@DeleteMapping("/board/{boardIndex}")
	public String deleteBoard(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) User loginMember, @PathVariable long boardIndex) {

		if (loginMember == null) {
			System.out.println("게시글을 삭제할 수 없습니다.");
		}

		boardService.deleteBoard(boardIndex);

		return "redirect:/";
	}


}
