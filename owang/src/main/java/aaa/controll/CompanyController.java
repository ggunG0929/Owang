package aaa.controll;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import aaa.model.MCompanyDTO;
import aaa.model.PageData;
import aaa.model.PaymentResponseMember;
import aaa.service.MCompanyMapper;
import aaa.service.PayMapper;
import aaa.service.PayService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("company")
public class CompanyController {

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
	
	//기업
		@Resource
		MCompanyMapper cccmapper;
		
		
		@RequestMapping("mypage")
		String detailmy(Model mm, HttpSession session) {
			String cid = (String)session.getAttribute("cid");
			
			mm.addAttribute("dto",cccmapper.deatilCompany(cid));
			
					
			return "company/detail";
		}
		
		

		// 기업관리
		// 기업정보상세보기
		@RequestMapping("detail/{cid}")
		String detail(@PathVariable String cid, MCompanyDTO cDTO, Model mm, HttpSession session) {
			String scid = (String)session.getAttribute("cid");
			
			mm.addAttribute("dto",cccmapper.deatilCompany(cid));
			
					
			return "company/detail";
		}


		//기업정보수정

		@GetMapping("modify")
		String modify(Model mm, HttpSession session) {
			
	String cid = (String)session.getAttribute("cid");
			
			mm.addAttribute("dto",cccmapper.deatilCompany(cid));

			return "company/modify";
		}

		@PostMapping("modify")
		String modifyReg(MCompanyDTO dto, PageData pd) {
			
			pd.setMsg("수정실패");
			pd.setGoUrl("/company/modify");
			
			int cnt = cccmapper.modifffy(dto); // 메서드안의 값이 들어와서 cnt 값이 1이됌
			
			if (cnt > 0) {
				pd.setMsg("수정되었습니다.");
				pd.setGoUrl("/company/mypage");
			}

			return "join/join_alert";
		}

		// 개인정보삭제

		@GetMapping("delete")
		String delete( HttpSession session, Model mm) {
			
			  String cid = (String) session.getAttribute("cid"); mm.addAttribute("cid",
			  cid);
			 
			return "company/delete";
		}

		@PostMapping("delete")
		String deleteReg(MCompanyDTO dto, PageData pd,HttpSession session) {

			pd.setMsg("삭제실패");
			pd.setGoUrl("/solo/delete");

			int cnt = cccmapper.delettt(dto); // 메서드안의 값이 들어와서 cnt 값이 1이됌
			
			if (cnt > 0) {
				pd.setMsg("삭제되었습니다.");
				pd.setGoUrl("/");
				session.invalidate();
			}

			return "join/join_alert";
		}
		
		// 재훈 추가
		// 기업회원 지원자 확인
		@RequestMapping("applicant") 
		String appList() {
			
			return "company/applicant";
		}
	
}
