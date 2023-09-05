package aaa.controll;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import aaa.model.CompanyDTO;
import aaa.service.CompanyMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/company")
public class ComController {
	
	@Resource
	CompanyMapper mapper;
	
	@RequestMapping("mypage")
	String mypage(Model mm, CompanyDTO cdto) {
		List<CompanyDTO>data = mapper.mypage(cdto);
		
		mm.addAttribute("mainData", data);
		System.out.println(data);
		return "company/mypage";
	}
	
	@GetMapping("modify")
	String modify(Model mm, CompanyDTO cdto) {
		
		mm.addAttribute("cdto", cdto);
		return "company/modifyForm";
	}
	
	@PostMapping("modify")
	String modifyReg(CompanyDTO cdto,  HttpServletRequest request) {
		
		mapper.modifffy(cdto);
		cdto.setMsg("수정되었습니다.");
		cdto.setGoUrl("/company/mypage");

		return "company/alert";
	}
}
