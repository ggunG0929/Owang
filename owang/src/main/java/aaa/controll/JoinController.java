package aaa.controll;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/join")

public class JoinController {
	
	@GetMapping("solo")
	
	String joinsoloForm() {

		return "join/join_solo";
	}

	@PostMapping("solo")
	String joinsoloReg() {
		
		return "login/join_ok";
	}
	
	@GetMapping("company")
	String joincompanyForm() {
		
		return "join/join_company";
	}
	
	@PostMapping("company")
	String joincompanyReg() {
		
		return "login/join_ok";
	}
	
	
}
