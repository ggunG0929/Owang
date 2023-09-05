package aaa.controll;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import aaa.model.PageData;

@Controller
@RequestMapping("/login")
public class LoginController {

	@GetMapping("main")
	String loginmain() {
	        
		return "login/login_main";
	}
	@PostMapping("main")
	String loginReg(@RequestParam("loginType") PageData pd,String loginType) {
		if ("company".equals(loginType)) {
			// 기업 로그인 처리
			// ...
			System.out.println("기업로그인");
		} else if ("individual".equals(loginType)) {
			// 개인 로그인 처리
			// ...
			System.out.println("개인로그인");
		}
		pd.setGoUrl("/login/login");
		pd.setMsg("로그인이 완료되었습니다.");
		return "/join/alert";
	}
	 @PostMapping("/login/company")
	    public String companyLogin() {
	        // 기업 로그인 처리
	        // ...
	        return "redirect:/welcome"; // 로그인 성공 후 환영 페이지로 리다이렉트
	    }

	    @PostMapping("/login/individual")
	    public String individualLogin() {
	        // 개인 로그인 처리
	        // ...
	        return "redirect:/welcome"; // 로그인 성공 후 환영 페이지로 리다이렉트
	    }
	@RequestMapping("login")
	String loginzz() {
		
		return "login/login_ok";
	}
	
}

