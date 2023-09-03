package aaa.controll;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import aaa.model.CompanyDTO;
import aaa.service.CompanyMapper;
import jakarta.annotation.Resource;

@Controller
@RequestMapping("/company")
public class ComController {
	
	@Resource
	CompanyMapper mapper;
	
	@RequestMapping("mypage")
	String mypage(Model mm, CompanyDTO dto) {
		
		List<CompanyDTO>data = mapper.mypage(dto);
		
		mm.addAttribute("mainData", data);
		return "company/mypage";
	}
	
	@RequestMapping("detail")
	String detail(Model mm, @PathVariable int id) {
		CompanyDTO dto = mapper.detail(id);
		mm.addAttribute("dto", dto);
		return "company/detail";
	}
	
	
}
