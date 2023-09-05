package aaa.controll;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin_company")
public class AdminCompanyController {
	
	@RequestMapping("/cmanagement")
	String cmanagement() {
		return "admin/company/cmanagement";
	}

}
