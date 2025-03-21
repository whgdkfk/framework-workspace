package com.kh.spring.board.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.kh.spring.board.model.dto.BoardDTO;
import com.kh.spring.board.model.service.BoardService;
import com.kh.spring.exception.InvalidParameterException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
// @RequestMapping("boards")
@RequiredArgsConstructor
public class BoardController {
	
	private final BoardService boardService;
	
	// ?page=1
	@GetMapping("boards")
	public String toBoardList(@RequestParam(name="page", defaultValue="1") int page) {
		// 한 페이지에 5개 보여줌
		// 버튼 5개 보여줌
		// 총 게시글 개수 == SELECT COUNT(*) FROM TB_SPRING_BOARD
		//				  WHERE STATUS = 'Y'
		if(page < 1) {
			throw new InvalidParameterException("어디 감히");
		}
		boardService.selectBoardList(page);
		
		// 첨부 파일
		return "board/board_list";
	}
	
	// 10번 게시글 조회 요청
	// localhost/spring/boards/10
	// @GetMapping("/{id}")
	
	@GetMapping("form.bo")
	public String goToForm() {
		return "board/insert_board";
	}
	
	@PostMapping("boards")
	public ModelAndView newBoard(ModelAndView mv, 
								 BoardDTO board,
								 MultipartFile upfile,
								 HttpSession session) {
		
		log.info("게시글 정보: {}, 파일 정보: {}", board, upfile);
		
		// 첨부파일의 존재 유무
		// => MultipartFile 타입의 filename 필드값으로 확인하겠다.
		
		// INSERT INTO TB_SPRING_BOARD(BOARD_TITLE, BOARD_CONTENT, BOARD_WRITER, CHANGE_NAME)
		// VALUES(#{boardTitle}, #{boardContent}, #{boardWriter}, #{changeName}); 	
		
		// 1. 권한 있는 요청인가(session)
		// 2. 값들이 유효성이 있는 값인가
		// 3. 전달된 파일이 존재할 경우 
		//    => 파일명 수정 서버에 올리고 BoardDTO의 changeName 필드에 값을 대입
		boardService.insertBoard(board, upfile, session);
		mv.setViewName("redirect:boards");
		session.setAttribute("message", "게시글 등록 성공😁");
		return mv;
	}
	
}
