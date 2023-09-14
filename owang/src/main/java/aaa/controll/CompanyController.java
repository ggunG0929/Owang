package aaa.controll;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import aaa.model.ApplicantDTO;
import aaa.model.MCompanyDTO;
import aaa.model.PageData;
import aaa.model.PaymentResponseMember;
import aaa.model.RecruitDTO;
import aaa.service.MCompanyMapper;
import aaa.service.PayMapper;
import aaa.service.PayService;
import aaa.service.RecruitMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("company")
public class CompanyController {
	
	
	@Resource
	RecruitMapper remapper;
	
	@Resource
	PayMapper paym;	
	@Autowired
	PayService payS;
	
	@RequestMapping("/product")
	String product(Model mm, HttpSession session) throws Exception {
		// 세션에서 id 가져옴
		MCompanyDTO companysession = (MCompanyDTO) session.getAttribute("companysession");
		String cid = companysession.getCid();

		// cdate가 오늘 이후인 경우 - 유효상품이 있는 경우
		Date cdate = companysession.getCdate();
		Date today = new Date();
        if(cdate.after(today)) {
        	mm.addAttribute("date", cdate);
        }
        
        // 아이디로 db의 impuid로 리스트를 만들어 가져오고, 서버에 보내 결제내역을 가져옴
    	List<String> impuidList = paym.impuids(cid);
		List<PaymentResponseMember.Payment> paymentData = payS.getPaymentData(impuidList);
        mm.addAttribute("paymentData", paymentData);
        return "product/payment";
	}
	

	
	
	// 기업
	@Resource
	MCompanyMapper cccmapper;

	// 기업

	@RequestMapping("mypage")
	String detaizlmy(Model mm, HttpSession session) {
		String cid = (String) session.getAttribute("cid");

		mm.addAttribute("dto", cccmapper.deatilCompany(cid));

		return "company/detail";
	}

	// 채용 리스트
	// 기업관리
	// 기업정보상세보기

	@RequestMapping("detail/{cid}")
	String detail(@PathVariable String cid, MCompanyDTO cDTO, Model mm, HttpSession session) {

		mm.addAttribute("dto", cccmapper.deatilCompany(cid));

		return "company/detail";
	}

	// 기업정보수정

		@GetMapping("modify")
		String modify(Model mm, HttpSession session) {
			String cid = (String) session.getAttribute("cid");
			mm.addAttribute("dto", cccmapper.deatilCompany(cid));
			
			return "company/modify";
		}

		@PostMapping("modify")
		String modifyReg(MCompanyDTO dto, PageData pd, HttpServletRequest request) {
			pd.setMsg("수정실패");
			pd.setGoUrl("/company/modify");
			
			if(dto.getCcompanyFile()==null) {
				System.out.println("기존파일없음");
			fileSavecompany(dto, request);
			}
			
			
			int cnt = cccmapper.modifffy(dto); // 메서드안의 값이 들어와서 cnt 값이 1이됌

			if (cnt > 0) {
				pd.setMsg("수정되었습니다.");
				pd.setGoUrl("/company/mypage");
			}

			return "join/join_alert";
		}
	
	// 개인정보삭제

	@GetMapping("delete")
	String delete(HttpSession session, Model mm) {

		String cid = (String) session.getAttribute("cid");
		mm.addAttribute("cid", cid);

		return "company/delete";
	}

	@PostMapping("delete")
	String deleteReg(MCompanyDTO dto, PageData pd, HttpSession session) {

		pd.setMsg("삭제실패");
		pd.setGoUrl("/company/delete");

		int cnt = cccmapper.delettt(dto); // 메서드안의 값이 들어와서 cnt 값이 1이됌

		if (cnt > 0) {
			pd.setMsg("삭제되었습니다.");
			pd.setGoUrl("/");
			session.invalidate();
		}

		return "join/join_alert";
	}

	// 파일수정삭제
	@PostMapping("fileDelete")
	String fileDelete(MCompanyDTO dto, PageData pd, HttpServletRequest request) {
		System.out.println(dto + "난 씨아이디");
		MCompanyDTO delDTO = cccmapper.deatilCompany(dto.getCid());
		pd.setMsg("파일 삭제실패");
		pd.setGoUrl("/company/modify");

		int cnt = cccmapper.fileDelete(dto);
		System.out.println("modifyReg:" + cnt);
		if (cnt > 0) {
			fileDeleteModule(delDTO, request);
			pd.setMsg("파일 삭제되었습니다.");
		}

		return "join/join_alert";
	}

	// 사업자등록증파일저장
	void fileSavecompany(MCompanyDTO cto, HttpServletRequest request) {

		// 파일 업로드 유무 확인
		if (cto.getMmff() == null || cto.getMmff().isEmpty()) {
			return;
		}

		String path = request.getServletContext().getRealPath("companyup");
		// path = "C:\\Final_Team\\owang\\src\\main\\webapp\\member\\companyup";

		int dot = cto.getMmff().getOriginalFilename().lastIndexOf(".");
		String fDomain = cto.getMmff().getOriginalFilename().substring(0, dot);
		String ext = cto.getMmff().getOriginalFilename().substring(dot);

		cto.setCcompanyFile(fDomain + ext);
		File ff = new File(path + "\\" + cto.getCcompanyFile());
		int cnt = 1;
		while (ff.exists()) {

			cto.setCcompanyFile(fDomain + "_" + cnt + ext);
			ff = new File(path + "\\" + cto.getCcompanyFile());
			cnt++;
		}

		try {
			FileOutputStream fos = new FileOutputStream(ff);

			fos.write(cto.getMmff().getBytes());

			fos.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// 파일지우기
	void fileDeleteModule(MCompanyDTO delDTO, HttpServletRequest request) {
		if (delDTO.getCcompanyFile() != null) {
			String path = request.getServletContext().getRealPath("companyup");
			/*
			 * path = "E:\\BackEnd_hakwon\\N_SpringWorks\\exboard2\\src\\main\\webapp\\up";
			 */

			new File(path + "\\" + delDTO.getCcompanyFile()).delete();
		}
	}

	// 재훈 추가
	// 기업회원 지원자 확인
	@RequestMapping("applicant")
	String solo_recruit(Model mm, ApplicantDTO adto, HttpSession session) {
		String cid = (String) session.getAttribute("cid");

		List<ApplicantDTO> appdata = cccmapper.applist(cid);
		mm.addAttribute("appdata", appdata);

		// mm.addAttribute("adto", adto);
		System.out.println(appdata);
		return "company/applicant";
	}

	@RequestMapping("/list/{cid}/1")
	String comRecruitList(Model mm, RecruitDTO dto) {
		List<RecruitDTO> data = remapper.recruitCompanyDetail(dto);
		mm.addAttribute("mainData", data);
		return "company/comRecruitList";
	}

}
