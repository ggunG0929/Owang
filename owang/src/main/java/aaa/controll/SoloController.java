package aaa.controll;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import aaa.model.PageData;
import aaa.model.PaymentResponseMember;
import aaa.model.SoloDTO;
import aaa.service.PayMapper;
import aaa.service.PayService;
import aaa.service.SoloMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("solo")
public class SoloController {

	@Resource
	SoloMapper sssmapper;

	// 회원관리

	// 개인정보상세보기
	@RequestMapping("solo_info")
	String detail(Model mm, HttpSession session, PageData pd) {
		String sid = (String) session.getAttribute("sid");
		
		pd.setMsg("로그인해야 이용가능한 페이지입니다");
		pd.setGoUrl("/login/main");

		if (sid != null) { // 세선에서 값을 받으면
			SoloDTO dto = sssmapper.detailSolo(sid);
			System.out.println(dto);
			mm.addAttribute("dto", dto);

			return "solo_info/detail";

		} else {

			return "join/join_alert";
		}
	}

	// 개인정보수정

	@GetMapping("modify")
	String modify(Model mm, HttpSession session) {
		String sid = (String) session.getAttribute("sid");
		mm.addAttribute("dto", sssmapper.detailSolo(sid));

		return "solo_info/modify";
	}

	@PostMapping("modify")
	String modifyReg(SoloDTO dto, PageData pd) {

		pd.setMsg("수정실패");
		pd.setGoUrl("/solo/modify");

		int cnt = sssmapper.modifffy(dto); // 메서드안의 값이 들어와서 cnt 값이 1이됌
		System.out.println("deleteReg:" + cnt);
		if (cnt > 0) {
			pd.setMsg("수정되었습니다.");
			pd.setGoUrl("/solo/solo_info");
		}

		return "join/join_alert";
	}

	// 개인정보삭제

	@GetMapping("delete")
	String delete( HttpSession session, Model mm) {
		
		  String sid = (String) session.getAttribute("sid"); mm.addAttribute("sid",
		  sid);
		 
		return "solo_info/delete";
	}

	@PostMapping("delete")
	String deleteReg(SoloDTO dto, PageData pd,HttpSession session) {

		pd.setMsg("삭제실패");
		pd.setGoUrl("/solo/delete");

		int cnt = sssmapper.delettt(dto); // 메서드안의 값이 들어와서 cnt 값이 1이됌
		System.out.println("deleteReg:" + cnt);
		if (cnt > 0) {
			pd.setMsg("삭제되었습니다.");
			pd.setGoUrl("/");
			session.invalidate();
		}

		return "join/join_alert";
	}
	
	
	// 상품
	@Resource
	PayMapper paym;
	@Autowired
	PayService payS;
	
	@RequestMapping("/product")
	String product(Model mm, HttpSession session) throws Exception {
		// 세션에서 id 가져옴
		SoloDTO solosession = (SoloDTO) session.getAttribute("solosession");
		String sid = solosession.getSid();
		
		// sdate가 오늘 이후인 경우 - 유효상품이 있는 경우
		Date sdate = solosession.getSdate();
		Date today = new Date();
        if(sdate.after(today)) {
        	mm.addAttribute("date", sdate);
        }
		
		// 아이디로 db의 impuid로 리스트를 만들어 가져오고, 서버에 보내 결제내역을 가져옴
		List<String> impuidList = paym.impuids(sid);
		List<PaymentResponseMember.Payment> paymentData = payS.getPaymentData(impuidList);
		mm.addAttribute("paymentData", paymentData);
		return "product/payment";
	}

}
