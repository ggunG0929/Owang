package aaa.controll;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
	
	
}


