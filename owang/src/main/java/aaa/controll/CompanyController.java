package aaa.controll;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aaa.model.CompanyDTO;


@Controller
@RequestMapping("/company")
public class CompanyController {

	@RequestMapping("company")
	String company(CompanyDTO dto,Model mm) {
		//System.out.println("company() 실행");
		//mm.addAttribute("mainData", data);
		return "company/company_write"; //안되는이유 모름 -> 밥먹고 오니까 됨
	}
}



	
	
	