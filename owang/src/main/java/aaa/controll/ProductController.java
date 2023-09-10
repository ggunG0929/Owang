package aaa.controll;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import aaa.model.MCompanyDTO;
import aaa.model.PaymentDTO;
import aaa.model.ProductDTO;
import aaa.model.SoloDTO;
import aaa.service.MCompanyMapper;
import aaa.service.PayMapper;
import aaa.service.PayService;
import aaa.service.ProductMapper;
import aaa.service.SoloMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("product")
public class ProductController {

	@Resource
	ProductMapper pm;
	
	@RequestMapping("/notice")
	String product_notice(Model mm) {
		// 전체 상품 목록
		List<ProductDTO> data= pm.list();
		mm.addAttribute("data", data);
		return "product/product_notice";
	}
	
	// 상품 결제 폼
	@RequestMapping("/{productId}")
	String product_form(@PathVariable String productId, Model mm, HttpSession session) {
		// 세션으로 사용자정보 채워주기
		String name, tel;
		if(session.getAttribute("sid")!=null) {
			SoloDTO solosession = (SoloDTO) session.getAttribute("solosession");
			name = solosession.getSname();
			tel = solosession.getSphone();
		}else {
			MCompanyDTO companysession = (MCompanyDTO) session.getAttribute("companysession");
			name = companysession.getCname();
			tel = companysession.getCcall();
		}
		// 상품정보 채워주기
		ProductDTO dto = pm.detail(productId);
		
		mm.addAttribute("name", name);
		mm.addAttribute("tel", tel);
		mm.addAttribute("dto", dto);
		return "product/product_form";
	}
	
	
	@Resource
	SoloMapper sm;
	@Resource
	MCompanyMapper mcm;
	
	// 주문정보 전달받음
	@RequestMapping(value ="/complete", method = RequestMethod.POST)
	@ResponseBody
	public int paymentComplete(@RequestBody PaymentDTO payDTO, HttpSession session) throws Exception {
//		System.out.println("dto: " + payDTO);
		
		// 결제정보 검증(주문정보와 금액 비교)
		// 서비스에서 토큰 가져옴
	    String token = PayService.getToken();
//		    System.out.println("토큰가져옴: " + token);
	    // 결제된 금액	// 서비스에서 imp_uid와 토큰으로 결제내역 가져옴
	    String amount = PayService.paymentInfo(payDTO.getImpUid(), token);
//		    System.out.println("금액가져옴: "+amount);
	    int res = 1;
	    // 주문정보 금액과 결제된 금액이 다를 경우 - 0
	    if (payDTO.getAmount() != Integer.parseInt(amount)) {
	    	res = 0;
//		    	System.out.println("검증실패");
			// 결제 취소
			PayService.paymentCancle(token, payDTO.getImpUid(), amount, "결제 금액 오류");
			return res;
		}
	    
	    // 결제 회원 정보 변경하기
	    // 상품 유효기간 가져오기
	    int valid = pm.period(payDTO.getProductId());
	    // 현재 날짜 구하기
	    Date today = new Date();
	    // 개인회원 세션이 존재할 경우
	    if(session.getAttribute("sid")!=null) {
	    	SoloDTO solosession = (SoloDTO) session.getAttribute("solosession");
	    	// 세션 아이디를 주문정보에 담기
	    	payDTO.setSid(solosession.getSid());
	    	// 변경될 내용을 담을 dto 만들기
	    	SoloDTO soloinfo = new SoloDTO();
	    	// 아이디 세팅
	    	soloinfo.setSid(solosession.getSid());
	    	// 타입을 2로 바꾸기
	    	soloinfo.setStype(2);
	    	// 회원 유효기간 가져오기
	    	Date sdate = solosession.getSdate();    		
    		// 유효기간이 null이거나 오늘 이전인 경우 - 오늘날짜에 상품 유효기간 더하기
    		if(sdate==null || sdate.before(today)) {
    			Calendar calendar = Calendar.getInstance();
    			calendar.setTime(today);
    			calendar.add(Calendar.DATE, valid);
    			sdate = calendar.getTime();
    			// 유효기간이 오늘 이후거나 오늘인 경우 - 유효기간에 상품 유효기간 더하기
    		}else { 
    			Calendar calendar = Calendar.getInstance();
    			calendar.setTime(sdate);
    			calendar.add(Calendar.DATE, valid);
    			sdate = calendar.getTime();
    		}
	        // 회원 유효기간 세팅
	        soloinfo.setSdate(sdate);
	        // 회원 정보 변경
	        sm.paysmember(soloinfo);
        // 기업회원 세션이 존재할 경우
	    }else if(session.getAttribute("cid")!=null) {
			MCompanyDTO companysession = (MCompanyDTO) session.getAttribute("companysession");
	    	payDTO.setCid(companysession.getCid());
	    	MCompanyDTO compinfo = new MCompanyDTO();
	    	compinfo.setCid(companysession.getCid());
	    	compinfo.setCtype(2);
	    	Date cdate = companysession.getCdate();
	    	if(cdate==null || cdate.before(today)) {    		
    			Calendar calendar = Calendar.getInstance();
    			calendar.setTime(today);
    			calendar.add(Calendar.DATE, valid);
    			cdate = calendar.getTime();
    		}else {
    			Calendar calendar = Calendar.getInstance();
    			calendar.setTime(cdate);
    			calendar.add(Calendar.DATE, valid);
    			cdate = calendar.getTime();
    		}
	        compinfo.setCdate(cdate);
	        mcm.paycmember(compinfo);
	    }
//	    System.out.println("주문정보 저장시도: "+payDTO);
	    // 주문정보를 db에 저장
	    paym.insert(payDTO);
		return res;
	}

	
	@Resource
	PayMapper paym;
	
	// 결제 금액 검증까지 성공했을 때 리다이렉트
	@RequestMapping("/result/{impUid}")
	String result(@PathVariable String impUid, Model mm) {
		// 결제시 부여된 impUid로 db내용 가져옴
		PaymentDTO dto = paym.detail(impUid);
		mm.addAttribute("dto", dto);
		return "product/product_result";
	}
	
}
