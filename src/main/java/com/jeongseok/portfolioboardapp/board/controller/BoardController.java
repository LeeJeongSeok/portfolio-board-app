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
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequiredArgsConstructor
public class BoardController {

	private final BoardService boardService;

	@GetMapping("/")
	public String index(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) User loginMember, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size, Model model) {

		int currentPage = page.orElse(1);
		int pageSize = size.orElse(10);

		Page<BoardListResponseDto> boardList = boardService.findPaginated(PageRequest.of(currentPage - 1, pageSize));

		model.addAttribute("boardList", boardList);

		// 상태바 메뉴 구분을 위한 로그인 확인 유무 로직
		if (loginMember == null) {
			return "index";
		}

		int totalPages = boardList.getTotalPages();
		if (totalPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
				.boxed()
				.collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
		}

		model.addAttribute("user", loginMember);

		return "index";
	}

	@GetMapping("/board")
	public String writeBoardForm(Model model) {
		model.addAttribute("boardWriteForm", new BoardWriteForm());
		return "board/boardWriteForm";
	}

	@PostMapping("/board")
	public String writeBoard(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) User loginMember, @Valid @ModelAttribute BoardWriteForm boardWriteForm, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return "board/boardWriteForm";
		}

		if (loginMember == null) {
			return "redirect:/login";
		}

		boardService.writeBoard(boardWriteForm, loginMember);

		return "redirect:/";
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

		model.addAttribute("board", board);


		return "board/boardEditForm";
	}

	@PatchMapping("/board/{boardIndex}/edit")
	public String boardEdit(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) User loginMember, @PathVariable long boardIndex, @Valid @ModelAttribute("board") BoardEditForm boardEditForm, BindingResult bindingResult) {

		BoardDetailResponseDto board = boardService.getBoard(boardIndex);

		if (board == null) {
			System.out.println("게시글을 찾을 수 없습니다.");
		}

		boardService.editBoard(boardIndex, loginMember.getUserId(), boardEditForm);

		return "redirect:/board/{boardIndex}";
	}



	@DeleteMapping("/board/{boardIndex}")
	public String deleteBoard(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) User loginMember, @PathVariable long boardIndex) {

		if (loginMember == null) {
			System.out.println("게시글을 삭제할 수 없습니다.");
		}

		boardService.deleteBoard(boardIndex, loginMember.getUserId());

		return "redirect:/";
	}


}
