package aaa.controll;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin_solo")
public class AdminSoloController {
	
	@RequestMapping("smanagement")
	String management() {
		return "admin/solo/smanagement";
	}
	
	
}
