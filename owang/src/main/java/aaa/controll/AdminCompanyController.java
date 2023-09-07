package aaa.controll;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import aaa.model.MCompanyDTO;
import aaa.model.PageData;
import aaa.model.RecruitDTO;
import aaa.service.AdminCompanyMapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/admin_company")
public class AdminCompanyController {
	
	@Resource
	AdminCompanyMapper adminMapper;
	
	
	
	// 기업 관리 페이지 ( 기업 리스트 )
	@RequestMapping("/cmanagement")
	String cmanagement(MCompanyDTO dto, Model mm,HttpServletRequest request) {
		
		return "admin/company/cmanagement";
	}
	
	// 채용 리스트
	@RequestMapping("list/{page}")
	String list(Model mm, MCompanyDTO dto) {
		
		dto.calc(adminMapper.adminAddCont());
		
		List<MCompanyDTO> data = adminMapper.companyList(dto);
		System.out.println(adminMapper.companyList(dto));
		mm.addAttribute("mainData",data);
		return "/admin/company/cList";
	}// 채용 리스트
	
	// 채용 디테일
		@RequestMapping("detail/{page}/{id}")
		String detail(Model mm, @PathVariable int page, @PathVariable int id) {
			// 조회수증가
			
			
			MCompanyDTO dto = adminMapper.adminCDetail(id);
			dto.setPage(page);
			dto.calc(adminMapper.adminAddCont());
   
			mm.addAttribute("dto",dto);
		 
			return "/admin/company/cDetail";
		}// 채용 디테일
	
	

}
