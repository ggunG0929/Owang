package aaa.controll;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import aaa.model.MCompanyDTO;
import aaa.model.SoloDTO;
import aaa.service.AdminCompanyMapper;
import aaa.service.AdminSolo;
import aaa.service.EndCompanyMapper;
import aaa.service.EndSoloMapper;
import aaa.service.SoloMapper;
import jakarta.annotation.Resource;

@Controller
@RequestMapping("/admin_member")
public class AdminEndMemberController {
	
	@Resource
	EndSoloMapper endSoloMapper;
	
	@Resource
	EndCompanyMapper endCompanyMapper;
	
	// 개인탈퇴회원
	@RequestMapping("endsolo/{page}")
	String endsolo(@PathVariable int page, Model mm, SoloDTO sdto) {
		sdto.setPage(page); // page 값을 설정합니다.
		sdto.calc(endSoloMapper.endSolocnt()); // 페이지 계산을 위해 적절한 total 값을 전달합니다.
		
		// 탈퇴회원 목록 가져오기
		List<SoloDTO> data = endSoloMapper.endSoloList();
		mm.addAttribute("endsoloData", data);
		return "admin/endmember/solo";
	}
	
	// 기업탈퇴회원
	@RequestMapping("endcompany/{page}")
	String endcompany(@PathVariable int page, Model mm, MCompanyDTO mdto) {
		mdto.setPage(page);
		mdto.calc(endCompanyMapper.endCompanycnt());
		System.out.println(mdto.getStart() + ", " + mdto.getLimit());
		List<MCompanyDTO> data = endCompanyMapper.endCompanyList(mdto);
		
		mm.addAttribute("endcompanyData", data);
		return "admin/endmember/company";
	}// 기업 리스트
	
	// 기업 상세보기
	@RequestMapping("endcompany/detail/{cid}")
	String endCompanyDetail( Model mm, MCompanyDTO mdto) {
			
		return "admin/endmember/companyDetail";
	}
	
	
	
}
