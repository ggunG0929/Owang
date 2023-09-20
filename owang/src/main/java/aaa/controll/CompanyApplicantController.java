package aaa.controll;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import aaa.model.ApplicantDTO;
import aaa.model.MCompanyDTO;
import aaa.model.PageData;
import aaa.model.SoloResumeDTO;
import aaa.service.CompanyApplicantMapper;
import aaa.service.SoloApplicantMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("company_applicant")
public class CompanyApplicantController {
	
	@Resource
	CompanyApplicantMapper camapper;
	
	// 재훈 추가
	// 개인 지원서
	@Resource
	SoloApplicantMapper samapper;

	// 기업회원 지원자 리스트
	@RequestMapping("home/{page}")
	String com_recruit(Model mm, ApplicantDTO adto, HttpSession session) {
		String cid = (String) session.getAttribute("cid");
		// 페이징
		adto.calc(samapper.apptotal());
		adto.setCid(cid);
		System.out.println(adto);
		List<ApplicantDTO> appdata = camapper.applist(adto);
		List<ApplicantDTO> apppassdata = camapper.apppasslist(adto);
		List<ApplicantDTO> appnonpassdata = camapper.appnonpasslist(adto);
		mm.addAttribute("appdata", appdata);
		mm.addAttribute("apppassdata", apppassdata);
		mm.addAttribute("appnonpassdata", appnonpassdata);
		System.out.println(appdata);
		return "company_applicant/home";
	}

	// 기업회원 지원자 이력서 디테일
	@RequestMapping("detail/{page}/{ano}")
	String com_recruit_detail(Model mm, HttpSession session, ApplicantDTO adto, SoloResumeDTO rdto,
			@PathVariable int page, @PathVariable int ano) {

		// 세션
		MCompanyDTO companysession = (MCompanyDTO) session.getAttribute("companysession");
		ApplicantDTO cadto = camapper.appdetail(adto.ano, companysession.getCid());
		//
		System.out.println("열람: " + camapper.appread(adto.getAno()));

		adto.setPage(page);
		mm.addAttribute("cadto", cadto);
		System.out.println(cadto);
		return "company_applicant/detail";
	}

	// 기업회원 지원자 삭제
	@RequestMapping("delete/{page}/{ano}")
	String com_recruit_delete(ApplicantDTO adto, PageData pd, @PathVariable int ano, @PathVariable int page,
			HttpSession session) {
		// 일단 삭제이벤트 생성
		MCompanyDTO companysession = (MCompanyDTO) session.getAttribute("companysession");
		ApplicantDTO cadto = camapper.appdetail(adto.ano, companysession.getCid());

		pd.setMsg("삭제실패");
		pd.setGoUrl("/company_applicant/home");

		int cnt = samapper.appdelete(ano);
		System.out.println("deleteTest" + cnt);

		if (cnt > 0) {
			System.out.println();
			// System.out.println(adto);
			pd.setMsg("삭제되었습니다.");
			pd.setGoUrl("/company_applicant/home" + "/" + adto.getPage());
		}

		return "company/alert";
	}

	// 지원자 합격
	@RequestMapping("pass/{page}/{ano}")
	String com_recruit_pass(ApplicantDTO adto, PageData pd, HttpServletRequest request, @PathVariable int ano,
			@PathVariable int page, HttpSession session) {
		System.out.println("modify어서와");
		MCompanyDTO companysession = (MCompanyDTO) session.getAttribute("companysession");
		ApplicantDTO cadto = camapper.appdetail(adto.ano, companysession.getCid());
		System.out.println(cadto);

		// pd.setGoUrl("/company/applicant" + "/" + adto.getPage());
		camapper.apppass(adto.getAno());
		System.out.println(cadto);

		String contextPath = request.getContextPath();
		String redirectUrl = contextPath + "/company_applicant/home" + "/" + adto.getPage();

		return "redirect:" + redirectUrl;
	}

	// 지원자 불합격
	@RequestMapping("nonpass/{page}/{ano}")
	String com_recruit_nonpass(ApplicantDTO adto, PageData pd, HttpServletRequest request, @PathVariable int ano,
			@PathVariable int page, HttpSession session) {
		System.out.println("modify어서와");
		MCompanyDTO companysession = (MCompanyDTO) session.getAttribute("companysession");
		ApplicantDTO cadto = camapper.appdetail(adto.ano, companysession.getCid());
		System.out.println(cadto);

		// pd.setGoUrl("/company/applicant" + "/" + adto.getPage());
		camapper.appnonpass(cadto.getAno());
		System.out.println("cadto = " + cadto);

		String contextPath = request.getContextPath();
		String redirectUrl = contextPath + "/company_applicant/home" + "/" + adto.getPage();

		return "redirect:" + redirectUrl;
	}
}
