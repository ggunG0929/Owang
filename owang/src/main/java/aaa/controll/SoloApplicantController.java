package aaa.controll;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import aaa.model.ApplicantDTO;
import aaa.model.MCompanyDTO;
import aaa.model.PageData;
import aaa.model.RecruitDTO;
import aaa.model.SoloDTO;
import aaa.model.SoloResumeDTO;
import aaa.service.MCompanyMapper;
import aaa.service.RecruitMapper;
import aaa.service.SoloMapper;
import aaa.service.SoloApplicantMapper;
import aaa.service.SoloResumeMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("solo_applicant")
public class SoloApplicantController {

	// 개인 지원서
	@Resource
	SoloApplicantMapper samapper;

	// 개인 회원
	@Resource
	SoloMapper smapper;

	// 지원하기때문에 넣었음
	@Resource
	SoloResumeMapper rsmapper;

	// solo_recruit >> home에 기업 디테일
	// 채용공고
	@Resource
	RecruitMapper recruitMapper;

	// 기업
	@Resource
	MCompanyMapper mcmapper;

	// 지원서 리스트
	@RequestMapping("home")
	String solo_recruit(Model mm, ApplicantDTO adto, HttpSession session) {
		String sid = (String) session.getAttribute("sid");
		
		List<ApplicantDTO> appdata = samapper.applist(sid);
		mm.addAttribute("appdata", appdata);

		//mm.addAttribute("adto", adto);
		System.out.println(appdata);
		return "solo_applicant/home";
	}

	// 지원서 디테일
	@RequestMapping("detail/{ano}") 
	String solo_recruit_detail(Model mm, HttpSession session, ApplicantDTO adto) {

		// 세션
		SoloDTO solosession = (SoloDTO) session.getAttribute("solosession");
		mm.addAttribute("solosession", solosession);
		
		// 
		mm.addAttribute("adto", samapper.appdetail(adto.ano, solosession.sid));
		System.out.println(solosession);
		System.out.println(adto);
		return "solo_applicant/detail";
	}	
	
	// 지원서 제출 페이지
	@GetMapping("submit/{page}/{cid}/{id}")
	String submit(Model mm, SoloResumeDTO rdto, @PathVariable int page, @PathVariable String cid, HttpSession session,
			@PathVariable int id, PageData pd) {
		String sid = (String) session.getAttribute("sid");
		SoloDTO solosession = (SoloDTO) session.getAttribute("solosession");

		// 공고 불러오기
		RecruitDTO rcdto = recruitMapper.recruitDetail(id);
		String recruitTitle = rcdto.getRecruitTitle();
		
		// 내가 쓴 이력서 불러오기
		List<SoloResumeDTO> data = rsmapper.resumelist(sid);

	    if (data.isEmpty()) {
	        pd.setMsg("이력서 등록후 이용해주세요");
	        pd.setGoUrl("/solo_resume/home");
	        
	        return "solo_applicant/alert";
	    }

	    mm.addAttribute("mainData", data);
	    mm.addAttribute("recruitTitle", recruitTitle);
	    session.setAttribute("selectedResume", rdto);
	    return "solo_applicant/submitform";
	}



	// 지원서 제출 완료 (SoloResumeDTO를 가져와서 applicant에 넣는다)
	// 1. SoloResumeDTO를 가져온다
	// 2. applicant에 삽입 메서드 넣음
	@PostMapping("submit/{page}/{cid}/{id}")
	String submitReg(PageData pd, @PathVariable String cid, HttpSession session, ApplicantDTO adto,
			@PathVariable int id, int rsid, Model mm) {

		// 개인세션 불러오기
		String sid = (String) session.getAttribute("sid");
		adto.setSDTO(smapper.detailSolo(sid));

		// 기업 불러오기
		adto.setMcDTO(mcmapper.deatilCompany(cid));

		// 이력서 불러오기
		adto.setSrDTO(rsmapper.resumedetail(rsid, sid));

		// 공고제목
		adto.setRcDTO(recruitMapper.recruitDetail(id));

		// adto.setRecruitTitle(r.getRecruitTitle());
		// System.out.println("data이야 :"+ r);

		// 지원서제목
		// adto.setAptitle(data.getRstitle());
		samapper.appinsert(adto);
		int Ano = adto.getAno();
		System.out.println("Ano는 :  " + Ano );
		// 지원자 명 == user2
		// adto.setSid(sid);
		// String adtoSid = adto.getSid();
		// System.out.println("adtoSid는 : " + adtoSid);

		// 기업 명 == ABC Company
		// adto.setCname(mcd.getCname());
		// String mcdCname = adto.getCname();
		// System.out.println("mcdCname은 : " + mcdCname);

		// List<ApplicantDTO> alist = rcmapper.appselect(adto);
		System.out.println("adto고요 cname이 들어옵니다 : " + adto);
		// System.out.println(alist);
		System.out.println("==================================");

		pd.setMsg("접수가 완료되었습니다.");
		pd.setGoUrl("/solo_applicant/home");

		return "solo_applicant/alert";
	}

	// 지원서 삭제 (지원 취소)
	@RequestMapping("delete/{ano}")
	String deleteApp(ApplicantDTO adto, PageData pd, @PathVariable int ano, HttpSession session) {
		// 일단 삭제이벤트 생성
		SoloDTO solosession = (SoloDTO) session.getAttribute("solosession");
		ApplicantDTO apdto = samapper.appdetail(adto.ano, solosession.sid);
	   
		pd.setMsg("삭제실패");
		pd.setGoUrl("/solo_applicant/home");
		
	    int cnt = samapper.appdelete(ano);
		System.out.println("deleteTest" + cnt);
		
		if (cnt>0) {
			System.out.println();
			//System.out.println(adto);
			pd.setMsg("삭제되었습니다.");
			pd.setGoUrl("/solo_applicant/home");
		}
		
		
		return "solo_applicant/alert";
	}
	
	// 지원서 제출 시 이력서 열람
	@RequestMapping("submit/{rsid}")
	String submitResume(Model mm, SoloResumeDTO rdto, HttpSession session, @PathVariable int rsid) {

		SoloDTO solosession = (SoloDTO) session.getAttribute("solosession");
		SoloResumeDTO sdto = rsmapper.resumedetail(rdto.rsid, solosession.sid);

		mm.addAttribute("sdto", sdto);
		mm.addAttribute("solosession", solosession);
		System.out.println(sdto);
		System.out.println(solosession);
		System.out.println(solosession.sid);
		// 이력서 열람만 가능
		return "solo_resume/recruit_resume";
	}

}