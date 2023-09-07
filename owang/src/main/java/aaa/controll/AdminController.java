package aaa.controll;

import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import aaa.model.AdminDTO;
import jakarta.servlet.http.HttpSession;
@Controller
@RequestMapping("/admin")
public class AdminController {
	
	
	// 로그인 폼
	@RequestMapping("/adminLogin")
	String adminLogin() {
		return "admin/adminLogin";
	}
	
	// 관리자 페이지
	@RequestMapping("/admin")
	String admin() {
		return "admin/admin";
	}
	
	/*
	// 관리자 계정 생성
	@RequestMapping("/adminLogin")
	@ResponseBody
	String make(HttpSession session) {
		session.setAttribute("admin", "1234");
		return "<h1>관리자를 선택해주세요</h1>"
				+ "<a href='/admin/loginForm'>관리자1</a>";
	}
	
	// 로그인 폼
	@RequestMapping("/loginForm")
	String adminLogin() {
		return "admin/adminLogin";
	}
	
	// 관리자 페이지
	@RequestMapping("/adminReg")
	String admin(AdminDTO admin, HttpSession session) {
		HashMap<String, AdminDTO> map = new HashMap<>();
		map.put("admin", new AdminDTO("aaa","관리자1","1234"));
		admin.setMsg("로그인 실패");
		if (map.containsKey(admin.getId())) {
			AdminDTO compareAdmin = map.get(admin.getId());
			
			if (compareAdmin.idPwChk(admin)) {
				admin.setMsg(admin.getAdminName()+"님 로그인 환영합니다.");
				session.setAttribute("admin", admin);
			}
		}
		
		return "admin/adminAlert";
	}
	*/
	
}


