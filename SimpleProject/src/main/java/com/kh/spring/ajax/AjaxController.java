package com.kh.spring.ajax;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kh.spring.board.model.dto.BoardDTO;
import com.kh.spring.board.model.service.BoardService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class AjaxController {
	
	@GetMapping("ajax")
	public String forward() {
		return "ajax/ajax";
	}
	
	/*
	 * 응답할 데이터를 문자열로 반환
	 * ModelAndView의 viewName 필드에 return한 스트링 값이 대입
	 * => DispatcherServlet
	 * => ViewResolver
	 * 
	 * 내가 반환하는 String 타입의 값이 View의 정보가 아니다. 응답데이터이다.
	 * => MessageConverter로 이동하게끔
	 * 
	 * @ResponseBody
	 */
	@ResponseBody
	@GetMapping(value = "test", produces="text/html; charset=UTF-8")
	public String ajaxReturn(@RequestParam(name="input") String value) {
		log.info("AJAX 요청을 통해 넘어온 VALUE값: {}", value);
		String returnValue = value.equals("admin") ? "아이디 있어요" : "아이디 없어요";
		return returnValue;
	}
	
	
	private final BoardService boardService;
	
	@Autowired
	public AjaxController(BoardService boardService) {
		this.boardService = boardService;
	}
	
	@ResponseBody
	@GetMapping(value = "study", produces="application/json; charset=UTF-8")
	public ResponseEntity<BoardDTO> ajaxStudy(@RequestParam("replyNo") int boardNo) {
		/*
		 * DTO의 목적: 테이블의 행에 있는 컬럼의 값을 필드에 담아옴
		 * 
		 * BoardDTO
		 * boardTitle
		 * boardContent
		 * boardWriter
		 * 
		 * XML
		 * <boardTitle>제목</boardTitle>
		 * <boardContent>내용</boardContent>
		 * 
		 * JSON
		 * {
		 * 	"boardTitle" : "제목",
		 *  "boardContent" : "내용"
		 * }
		 */
		BoardDTO board = boardService.selectBoard(boardNo);
		/*
		log.info("{}", board);
		
		Map<String, String> map = new HashMap();
		map.put("boardTitle", board.getBoardTitle());
		map.put("boardContent", board.getBoardContent());
		map.put("boardWriter", board.getBoardWriter());
		log.info("조회한 내용 JavaScript의 객체 모양으로 만들기:{}", map);
		*/
		return ResponseEntity.ok(board);
	}
	
}
