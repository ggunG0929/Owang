package aaa.controll;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aaa.model.SoloRecruitDTO;
import aaa.service.SoloRecruitMapper;
import jakarta.annotation.Resource;

@Controller
@RequestMapping("solo_recruit")
public class SoloRecruitController {
	
	@Resource
	SoloRecruitMapper rcmapper;
	
	// 지원서 리스트
	@RequestMapping("home")
	String solo_recruit(Model mm, SoloRecruitDTO rcdto) {
	
		List<SoloRecruitDTO> data = rcmapper.recruitlist();
		mm.addAttribute("mainData", data);
		System.out.println(data);
		return "solo_recruit/home";
	}
	
	
}