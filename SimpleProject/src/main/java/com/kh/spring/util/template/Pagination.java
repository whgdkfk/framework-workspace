package com.kh.spring.util.template;

import com.kh.spring.util.model.dto.PageInfo;

public class Pagination {
	
	public static PageInfo getPageInfo(int count, 
									   int currentPage,
									   int boardLimit,
									   int pageLimit) {
		
		int maxPage = (int)Math.ceil((double)count / boardLimit);
		int startPage = (currentPage -1) / pageLimit * pageLimit + 1;
		int endPage = startPage + pageLimit -1;
		
		if(endPage > maxPage) { endPage = maxPage; }
		
		/*
		필드 순서대로 적어야 함
		모든 필드 넣어야 함
		return new PageInfo(count, currentPage, boardLimit, pageLimit, maxPage, startPage, endPage);
		PageInfo page = new PageInfo();
		page.setBoardLimit(boardLimit);
		page.setCount(count);
		page.setCurrentPage(currentPage);
		*/
		
		// 순서 상관 없음
		// 필요한 것만 넣을 수 있음
		return PageInfo.builder()
					   .boardLimit(boardLimit)
					   .count(count)
					   .currentPage(currentPage)
					   .startPage(startPage)
					   .endPage(endPage)
					   .maxPage(maxPage)
					   .pageLimit(pageLimit)
					   .build();
	}
}
