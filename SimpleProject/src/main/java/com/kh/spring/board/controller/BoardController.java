package com.kh.spring.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kh.spring.board.model.service.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
// @RequestMapping("boards")
@RequiredArgsConstructor
public class BoardController {
	
	private final BoardService boardService;
	
	@GetMapping("boards")
	public String toBoardList() {
		return "board/board_list";
	}
	
	// 10번 게시글 조회 요청
	// localhost/spring/boards/10
	// @GetMapping("/{id}")
	
	@GetMapping("form.bo")
	public String goToForm() {
		return "board/insert_board";
	}
	
}
