package aaa.controll;

import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import aaa.model.MCompanyDTO;
import aaa.model.PageData;
import aaa.model.SoloDTO;
import aaa.service.MCompanyMapper;
import aaa.service.SoloMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/login")
public class LoginController {

	@Resource
	SoloMapper ssmapper;
	@Resource
	MCompanyMapper ccmapper;
	
	

	@GetMapping("main")
	String loginmain() {

		return "login/login_main";
	}

	// 기업회원 구분하기!
	@PostMapping("main")
	String loginReg(HttpServletRequest request, HttpSession session, PageData pd, MCompanyDTO mcDTO, SoloDTO soloDTO) {
		String loginType = request.getParameter("loginType");
		String userid = request.getParameter("userid");
		String userpw = request.getParameter("userpw");
		//현재날짜가져오기
				Date today = new Date();
		// 승우 추가 2시간 설정 단위 밀리세컨드(2시간 = 2 * 60분 * 60초)
		session.setMaxInactiveInterval(2 * 60 * 60);

		
		//기업 로그인
		if ("company".equals(loginType)) {
			
			

			int result = ccmapper.loginCompany(userid, userpw);

			pd.setGoUrl("/login/main");
			pd.setMsg("아이디나 비밀번호가 일치하지 않습니다.");
			if (result == 1) {
				MCompanyDTO comsession = ccmapper.deatilaaaCompany(userid);
				Date ccdate = comsession.getCdate();
				int cctype = comsession.getCtype();
				System.out.println(cctype+"hheifhaihf");
				pd.setMsg("기업회원 로그인");
				//타입결제확인
				if(cctype==2 && ccdate!=null && ccdate.before(today)) {	//
					
					comsession.setCtype(1);	
					ccmapper.logincmember(comsession);
					System.out.println("타입수정완료!");
					pd.setMsg("기업회원 로그인완료. (채용공고권이 만료되었습니다.)");
					
				}
				
						
				// 객체세션발급
				MCompanyDTO companysession = ccmapper.deatilaaaCompany(userid);
				session.setAttribute("cid",userid);
				session.setAttribute("companysession", companysession);
				pd.setGoUrl("/");
			}

		} else if ("individual".equals(loginType)) {
			int result = ssmapper.loginSolo(userid, userpw);

			pd.setGoUrl("/login/main");
			pd.setMsg("아이디나 비밀번호가 일치하지 않습니다.");
			if (result == 1) {
				pd.setMsg("개인회원 로그인");
				//객체정보받기
				SoloDTO sosession = ssmapper.resumeSolo(userid);
				
				Date ssdate = sosession.getSdate();
				int sstype = sosession.getStype();
		
				
				//결제관련 타입처리
				if(sstype==2 && ssdate!=null &&  ssdate.before(today)) {	//
					sosession.setStype(1);	
					ssmapper.loginsmember(sosession);	
					System.out.println("타입수정완료!");
					pd.setMsg("개인회원 로그인 (리뷰열람권이 만료되었습니다.)");
				}
				
				
				// 객체세션발급
				SoloDTO solosession = ssmapper.resumeSolo(userid);
				session.setAttribute("sid",userid);
				session.setAttribute("solosession", solosession);
				pd.setGoUrl("/");
			}

		}

		
		return "/join/join_alert";
	}

	 @RequestMapping("logout")
	   String logout(HttpSession session,PageData pd) {
	      pd.setMsg("로그아웃 됨?");
	      session.invalidate();
	      
	      return "redirect:/";
	   }
	// 아이디 비번찾기
		@RequestMapping("find")
		String find() {

			return "login/login_find";
		}

		// 아이디결과창
		@RequestMapping("findreg/{finduid}")
		String findreg(@PathVariable String finduid,Model mm) {

			mm.addAttribute("uid",finduid);
			return "login/find_idreg";
		}

		// 아이디찾기
		@GetMapping("findid")
		String findid() {

			return "login/find_id";
		}

		@PostMapping("findid")
		String findnid(HttpServletRequest request, HttpSession session, PageData pd, MCompanyDTO mcDTO, SoloDTO soloDTO) {
			String loginType = request.getParameter("idloginType");
			String fname = request.getParameter("idname");
			String femail = request.getParameter("idemail");
			System.out.println("이름"+fname+"이메일"+femail+"타입"+loginType);
			// 기업아이디찾기
			if ("company".equals(loginType)) {
				System.out.println("기업");
				int cresult = ccmapper.findloCompany(fname, femail); // 개인아이디찾기 }else if

				pd.setGoUrl("/login/findid");
				pd.setMsg("일치하는 아이디가 없습니다.");
				if (cresult == 1) {
					MCompanyDTO comsession = ccmapper.findid(fname, femail);
					String cid=comsession.getCid();
					 pd.setGoUrl("/login/findreg/" + cid); // URL에 sid 값을 문자열로 추가
					pd.setMsg("일치하는 아이디 검색 1건");
					System.out.println("기업아이디 성공");
					// 타입결제확인
				}
			} else if ("individual".equals(loginType)) {

				int sresult = ssmapper.findloSolo(fname, femail);

				pd.setGoUrl("/login/findid");
				pd.setMsg("일치하는 아이디가 없습니다.");
				if (sresult == 1) {
					SoloDTO sosession = ssmapper.findid(fname, femail);
					String sid=sosession.getSid();
					
					pd.setMsg("일치하는 아이디 검색 1건");
					 pd.setGoUrl("/login/findreg/" + sid); // URL에 sid 값을 문자열로 추가
					
				}
			}
			return "join/join_alert";
		}

}
