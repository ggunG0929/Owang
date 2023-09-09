package aaa.controll;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aaa.model.MCompanyDTO;
import aaa.model.RecruitDTO;
import aaa.model.SoloRecruitDTO;
import aaa.service.MCompanyMapper;
import aaa.service.RecruitMapper;
import aaa.service.SoloRecruitMapper;
import jakarta.annotation.Resource;

@Controller
@RequestMapping("solo_recruit")
public class SoloRecruitController {
	
	// 개인 지원서 
	@Resource
	SoloRecruitMapper rcmapper;
	
	// solo_recruit >> home에 기업 디테일
	// 채용공고
	@Resource
	RecruitMapper recruitMapper;
	
	// 기업
	@Resource
	MCompanyMapper mcmapper;
	
	// 지원서 리스트
	@RequestMapping("home")
	String solo_recruit() {
	
		
		return "solo_applicant/home";
	}

}