package aaa.controll;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import aaa.model.CompanyDTO;
import aaa.model.MCompanyDTO;
import aaa.model.PageData;
import aaa.model.SoloDTO;
import aaa.service.MCompanyMapper;
import aaa.service.SoloMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;

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
	String loginReg(HttpServletRequest request, PageData pd, MCompanyDTO mcDTO, SoloDTO soloDTO) {
		String loginType = request.getParameter("loginType");
		String userid = request.getParameter("userid");
		String userpw = request.getParameter("userpw");
		if ("company".equals(loginType)) {
			// 기업 로그인 처리
			// .

			mcDTO = ccmapper.loginCompany(userid, userpw);

			pd.setGoUrl("/login/login_main");
			pd.setMsg("아이디나 비밀번호가 일치하지 않습니다.");
			if (mcDTO.getCid().equals(userid) && mcDTO.getCpw().equals(userpw)) {

				pd.setGoUrl("/login/login_companyok");
			}

		} else if ("individual".equals(loginType)) {
			// 개인 로그인 처리
			soloDTO = ssmapper.loginSolo(userid, userpw);

			pd.setGoUrl("/login/login_main");
			pd.setMsg("아이디나 비밀번호가 일치하지 않습니다.");
			if (soloDTO.getSid().equals(userid) && soloDTO.getSpw().equals(userpw)) {

				pd.setGoUrl("/login/login_solook");
			}

		}

		pd.setMsg("로그인이 완료되었습니다.");
		return "/join/join_alert";
	}

	@RequestMapping("login_companyok")
	String logincompany() {

		return "login/login_companyok";
	}

	@RequestMapping("login_solook")
	String loginsoloz() {

		return "login/login_solook";
	}

}
