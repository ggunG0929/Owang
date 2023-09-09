package aaa.controll;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

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
	
	@RequestMapping("/product")
	String product(Model mm, HttpSession session) throws Exception {
		String cid = (String) session.getAttribute("cid");
    	List<String> impuidList = paym.impuidbyc(cid);
        String token = PayService.getToken(); // PayService를 통해 토큰을 가져옴
        // API에 대한 요청을 위한 URL 생성
        String apiUrl = "https://api.iamport.kr/payments?"
        		// 스트림으로 변환
                + impuidList.stream()
                // 하나씩 어떤 형태로 가져올지
                            .map(uid -> "imp_uid[]=" + uid)
                            // &로 이어줌
                            .collect(Collectors.joining("&"))
                + "&_token=" + token;
        // rest템플릿
        RestTemplate restTemplate = new RestTemplate();
        // PaymentResponse2 형태로 정보받음
        ResponseEntity<PaymentResponseMember> responseEntity = restTemplate.getForEntity(apiUrl, PaymentResponseMember.class);
        List<PaymentResponseMember.Payment> paymentData = responseEntity.getBody().getResponse();
        for (PaymentResponseMember.Payment payment : paymentData) {
            // paid_at을 포맷팅
            Long ldate = Long.parseLong(payment.getPaid_at());
            Date date = new Date(ldate * 1000L);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            payment.setPaid_at(sdf.format(date));
        }
//        System.out.println(responseEntity);
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
