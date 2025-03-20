package com.kh.spring.member.controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.kh.spring.member.model.dto.MemberDTO;
import com.kh.spring.member.model.service.MemberService;
import com.kh.spring.member.model.service.MemberServiceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor // 의존성 주입 생성자를 생성해주는 애노테이션(lombok)
public class MemberController {
	
	// 내가 가진 bean과 연결해주는 것
	// @Autowired
	// 필드로 타입만 선언해놓음
	private final MemberService memberService;
	
	/*
	@Autowired
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}
	*/
	
	/*
	@Autowired
	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}
	*/
	
	
	// 의존성 주업
	// 1. 필드로 선언하고 Autowired Annotation을 단다.
	// 2. setter를 만들어서 Autowired Annotation을 단다.
	// 3. 생성자를 만들어서 생성자의 Autowired Annotation을 단다.(권장)
	
	/*
	@RequestMapping(value="login")
	public String login(HttpServletRequest request) {
		// System.out.println("로그인 요청 오면 출력됨");
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		// System.out.println("id: " + id + ", pw: " + pw);
		// System.out.printf("id: %s, pw: %s", id, pw);
		log.info("id: {}, pw: {}", id, pw);
		return "main_page";
	}
	*/
	
	// 권장하는 방법(@RequestParam 사용)
	// 명확하게 어떤 값을 담겠다 알려줌
	/*
	@RequestMapping("login")
	public String login(@RequestParam(value="id", defaultValue="abcde") String id, 
						@RequestParam(value="pw") String pw) {
		log.info("이렇게도 넘어오나요? id: {} / pw: {}", id, pw);
		return "main_page";
	}
	*/

	// 작업하기 편한 방법
	/*
	@PostMapping("login")
	public String login(String id, String pw) {
		log.info("이렇게도 넘어오나요? id: {} / pw: {}", id, pw);
		
		MemberDTO member = new MemberDTO();
		member.setMemberId(id);
		member.setMemberPw(pw);
		
		return "main_page";
	}
	*/
	
	/**
	 * 커맨드 객체 방식
	 * (아래 세 가지 조건을 만족하지 않으면 사용 불가)
	 * 
	 * 1. 매개 변수 자료형에 반드시 기본 생성자가 존재할 것
	 * 2. 전달되는 키값과 객체의 필드명이 동일할 것
	 * 3. setter 메서드가 반드시 존재할 것
	 * 
	 * 스프링에서 해당 객체를 기본 생성자를 통해서 생성한 후 
	 * 내부적으로 setter 메서드를 찾아서 
	 * 요청 시 전달값을 해당 필드에 대입해줌
	 * => Setter Injection
	 * 
	 * @param member
	 * @return
	 */
	/*
	// Request Handler(요청 처리기)
	@PostMapping("login")
	public String login(MemberDTO member, 
						HttpSession session,
						Model model) {
		
		// log.info("이런 건 안 돼요~ {}", member); // header.jsp name="memberId", name="memberPw"
		
		/*
		 * Controller 역할
		 * 데이터 가공(Spring Framework가 대신 해줌)
		 * 요청 처리(Service)
		 * 응답 화면 지정
		 */
		/*
		MemberDTO loginMember = memberService.login(member);
		
		if(loginMember != null) {
			log.info("로그인 성공");
		} else {
			log.info("로그인 실패");
		}

		
		if(loginMember != null) { // 성공했을 때
			// sessionScope에 로그인 정보를 담아줌
			session.setAttribute("loginMember", loginMember);
			// 그 다음에
			// main_page
			// /WEB-INF/views/
			// .jsp
			// => 포워딩
			// sendRedirect
			
			// localhost/spring  /
			return "redirect:/";
			
		} else { // 실패했을 때
			
			// error_page
			// requestScope에 에러 문구를 담아서 포워딩
			// Spring에서는 Model 객체를 이용해서 RequestScope에 값을 담음
			model.addAttribute("message", "로그인 실패");
			
			// 논리적인 경로(문자열)를 가지고 물리적인 경로(실제 위치)를 찾아간다.
			// forwarding
			// /WEB-INF/views/
			// include/error_page
			// .jsp
			return "include/error_page";
		}
		
		// return "main_page";
	}
	*/
		
	// 두 번째 방법) 반환 타입 ModelAndView로 돌아가기	
	@PostMapping("login")
	public ModelAndView login(MemberDTO member, 
							  HttpSession session,
							  ModelAndView mv) {
		
		MemberDTO loginMember = memberService.login(member);
		
		if(loginMember != null) {
			session.setAttribute("loginMember", loginMember);
			mv.setViewName("redirect:/");
		} else {
			mv.addObject("message", "로그인 실패")
			  .setViewName("include/error_page");
		}
		return mv;
	}
	
	@GetMapping("logout")
	public ModelAndView logout(HttpSession session,
					 		   ModelAndView mv) {
		
		session.removeAttribute("loginMember");
		mv.setViewName("redirect:/");
		return mv;
	}
	
	@GetMapping("signup-form")
	public String signupForm() {
		// /WEB-INF/views/  member/signup-form  .jsp
		return "member/signup-form";
	}
	
	/**
	 * 
	 * @param member id
	 * @return 성공 시 main 실패 시 error_page
	 */
	@PostMapping("signup")
	public String join(MemberDTO member, HttpServletRequest request) {
		/*
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		*/
		
		// log.info("멤버 필드 찍어보기: {}", member);
		memberService.signUp(member);
		
		return "main_page";
	}
	
	@GetMapping("my-page")
	public String myPage() {
		return "member/my_page";
	}
	
	@PostMapping("update-member")
	public String update(MemberDTO member, HttpSession session) {
		// 1. Controller에서는 RequestMapping 애노테이션 및 요청 시 전달값이 잘 전달되는지 확인
		/*
		 * 1_1) 404 발생: mapping 값 잘못 적음
		 * org.springframework.web.servlet.PageNotFound
		 * - No mapping for POST /spring/update-member
		 * 
		 * 1-2) 405 발생: 앞단에선 POST / GET으로 요청을 보내놓고 메소드와 맞지 않은 애노테이션을 사용했을 때
		 * Request method 'POST' not supported 
		 * 
		 * 1_3) 필드에 값이 들어오지 않는 경우
		 */
		log.info("사용자가 입력한 값: {}", member);
		
		// 2. 이번에 실행할 SQL문을 생각
		// UPDATE문 ==> KH_MEMBER(MEMBER_ID)
		// ID, PW, NAME, EMAIL, DATE
		// 2_1) 매개변수 MemberDTO 타입의 memberId 필드값
		// 2_2) SessionScope에 loginMember 키값에 memberId 필드값
		// 		넘겨주어야 겠구나 +
		
		// 값들이 유효한 값인치 체크하기
		// MemberId가 존재하는 아이디인지 체크하기
		
		// UPDATE KH_MEMBER SET MEMBER_NAME = 사용자가 입력한 이름, 
		//					EMAIL = 사용자가 입력한 이메일
		//			  WHERE MEMBER_ID = 사용자가 입력한 아이디
		// UPDATE 수행의 결과 => PK를 조건으로 수행함 => 0 / 1
		
		// 수행에 성공했을 경우 =>
		// my_page.jsp로 이동 + 갱신된 회원의 정보 출력
		
		// id를 가지고 다시 조회 => login 메서드 재활용
		
		// 수행에 실패했을 경우 =>
		// message를 담아서 error_page로 포워딩
		// 예외 발생 => 예외처리기로 위임
		
		memberService.update(member, session);
		
		return "redirect:my-page";
	}
	
	

}
