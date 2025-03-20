package com.kh.spring.board.model.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.kh.spring.board.model.dto.BoardDTO;

public interface BoardService {

	// 게시글 작성(파일 첨부)
	void insertBoard(BoardDTO board, MultipartFile file); 
	/*
	 * [보편적인 메서드명]
	 * insertBoard();
	 * save();
	 */
	
	// 게시글 목록 조회
	List<BoardDTO> selectBoardList(int currentPage);
	/*
	 * selectBoardList();
	 * sellectAll();
	 * findAll();
	 */
	
	// 게시글 상세보기(댓글도 같이 조회) --> 새로운 기술 사용 
	BoardDTO selectBoard(int boardNo);
	/*
	 * selectBoard();
	 * findByXXXX();
	 */
	
	// 게시글 수정
	BoardDTO updateBoard(BoardDTO board, MultipartFile file);
	/*
	 * updateBoard();
	 * updateByXXX();
	 */
	
	// 게시글 삭제(delete인 척하고 update 할 것 STATUS 컬럼값 N으로 바꿀 것)
	void deleteBoard(int boardNo);
	
	// ---------------------------
	
	// 게시글 검색 기능

	// 댓글 작성
	
	
}
