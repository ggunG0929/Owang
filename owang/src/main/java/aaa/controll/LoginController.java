package aaa.controll;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
		if ("company".equals(loginType)) {
			// 기업 로그인 처리
			

			int result = ccmapper.loginCompany(userid, userpw);

			pd.setGoUrl("/login/main");
			pd.setMsg("아이디나 비밀번호가 일치하지 않습니다.");
			if (result == 1) {
				MCompanyDTO companysession = ccmapper.deatilaaaCompany(userid);
				
				session.setAttribute("cid",userid);
				// 객체세션발급
				
				session.setAttribute("companysession", companysession);
				System.out.println(session.getAttribute("companysession") + "세션 등장");
				pd.setMsg("로그인이 완료되었습니다.");
				pd.setGoUrl("/");
			}

		} else if ("individual".equals(loginType)) {
			int result = ssmapper.loginSolo(userid, userpw);

			pd.setGoUrl("/login/main");
			pd.setMsg("아이디나 비밀번호가 일치하지 않습니다.");
			if (result == 1) {
				session.setAttribute("sid",userid);
				soloDTO = ssmapper.resumeSolo(userid);
				// 객체세션발급
				session.setAttribute("solosession", soloDTO);
				
				System.out.println(session.getAttribute("sid"));
				System.out.println(session.getAttribute("solosession")+"확인");
				pd.setMsg("로그인이 완료되었습니다.");
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

}
