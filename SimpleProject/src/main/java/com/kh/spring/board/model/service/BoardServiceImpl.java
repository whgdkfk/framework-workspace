package com.kh.spring.board.model.service;

import java.io.File;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kh.spring.board.model.dto.BoardDTO;
import com.kh.spring.board.model.mapper.BoardMapper;
import com.kh.spring.exception.AuthenticationException;
import com.kh.spring.member.model.dto.MemberDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
	
	private final BoardMapper boardMapper;
	
	@Override
	public void insertBoard(BoardDTO board, MultipartFile file, HttpSession session) {
		
		// 1. 권한 체크
		MemberDTO loginMember = (MemberDTO)session.getAttribute("loginMember");
		if(loginMember != null && !loginMember.getMemberId().equals(board.getBoardWriter())) {
			throw new AuthenticationException("권한 없는 접근입니다.");
		}
		
		// 2. 유효성 검사
		if(board.getBoardTitle() == null || board.getBoardTitle().trim().isEmpty() || 
		   board.getBoardContent() == null || board.getBoardContent().trim().isEmpty() ||
		   board.getBoardWriter() == null || board.getBoardWriter().trim().isEmpty()) {
			throw new InvalidParameterException("유효하지 않은 요청입니다.");
		}
		
		// 2-2.
		
		// 3) 파일 유무 체크 // 이름 바꾸기 + 저장
		if(!file.getOriginalFilename().isEmpty()) {
			
			// 이름 바꾸기
			// KH_ + 현재 시간 + 랜덤 숫자 + 원본 파일 확장자
			StringBuilder sb = new StringBuilder();
			sb.append("KH_");
			String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			// log.info("현재 시간: {}", currentTime);
			sb.append(currentTime);
			sb.append("_");
			int random = (int)(Math.random() * 900) + 100;
			sb.append(random);
			String ext = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
			sb.append(ext);
			// log.info("바뀐 파일명: {}", sb.toString());
			
			ServletContext application = session.getServletContext();
			
			String savePath = application.getRealPath("/resources/upload_files/");
			try {
				file.transferTo(new File(savePath + sb.toString()));
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
			board.setChangeName("/spring/resources/upload_files/" + sb.toString());
		}
		
		boardMapper.insertBoard(board);
		
	}

	@Override
	public List<BoardDTO> selectBoardList(int currentPage) {
		boardMapper.selectTotalCount();
		
		return null;
	}

	@Override
	public BoardDTO selectBoard(int boardNo) {
		return null;
	}

	@Override
	public BoardDTO updateBoard(BoardDTO board, MultipartFile file) {
		return null;
	}

	@Override
	public void deleteBoard(int boardNo) {

	}

	@Override
	public void insertBoard(BoardDTO board, MultipartFile file) {
		// TODO Auto-generated method stub
		
	}

}
