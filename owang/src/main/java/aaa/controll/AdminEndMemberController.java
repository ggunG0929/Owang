package aaa.controll;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import aaa.model.ApplicantDTO;
import aaa.model.MCompanyDTO;
import aaa.model.RecruitDTO;
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
	
	
	///////////////////////////////////////// 개인(위에) 기업(아래) 구분선
	
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
	String endCompanyDetail( Model mm, MCompanyDTO mdto,@PathVariable String cid) {
		List<RecruitDTO> data = endCompanyMapper.endRecruitRealList(cid);
		mm.addAttribute("mainData",data);
		return "admin/endmember/companyDetail";
	}
	
	// 기업 지원자 상세보기
	@RequestMapping("appnum/detail/{cid}/{recruit_title}")
	String appnumDeatil( Model mm, MCompanyDTO mdto,@PathVariable String cid,@PathVariable String recruit_title,ApplicantDTO dto) {
		System.out.println("ApplicantDTO" + dto);
		System.out.println("MCompanyDTO" + mdto);
		RecruitDTO rdto = endCompanyMapper.endrecruitone(recruit_title);
		
		List<ApplicantDTO> adto = endCompanyMapper.endApplicantList(rdto.cid,rdto.recruitTitle);
		mm.addAttribute("mainData",adto);
		return "admin/endmember/appnum";
	}
	
	
	
}
