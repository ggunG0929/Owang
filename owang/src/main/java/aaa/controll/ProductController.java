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
		// 전체상품
		List<ProductDTO> data= pm.list();
		mm.addAttribute("data", data);
		return "product/product_notice";
	}
	
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
		
//		Date today = new Date();
//		Calendar calendar = Calendar.getInstance();
//		calendar.setTime(today);
//		calendar.add(Calendar.DAY_OF_MONTH, dto.getProductValid()); // 현재 날짜에 유효일을 더함
//		Date endDate = calendar.getTime();
		mm.addAttribute("name", name);
		mm.addAttribute("tel", tel);
		mm.addAttribute("dto", dto);
//		mm.addAttribute("endDate", endDate);	// 포맷팅 필요
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
//		    System.out.println("dto: " + payDTO);
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
		    // 상품 유효기간 가져오기
		    int valid = pm.period(payDTO.getProductId());
		    // 현재 날짜 구하기
		    Date today = new Date();
		    if(session.getAttribute("sid")!=null) {
		    	SoloDTO solosession = (SoloDTO) session.getAttribute("solosession");
		    	// 세션으로 아이디를 주문정보에 담기
		    	payDTO.setSid(solosession.getSid());
		    	SoloDTO soloinfo = new SoloDTO();
		    	soloinfo.setSid(solosession.getSid());
		    	// 타입을 2로 바꾸기
		    	soloinfo.setStype(2);
		    	// 유효기간 가져오기
		    	Date sdate = solosession.getSdate();
		        // sdate가 오늘 이전인 경우
		        if(sdate.before(today)) {
		            Calendar calendar = Calendar.getInstance();
		            calendar.setTime(today);
		            calendar.add(Calendar.DATE, valid); // 현재 날짜에 valid일을 더합니다.
		            sdate = calendar.getTime();
		         // sdate가 오늘 이후이거나 오늘인 경우
		        }else { 
		            // sdate에 valid일을 더함
		            Calendar calendar = Calendar.getInstance();
		            calendar.setTime(sdate);
		            calendar.add(Calendar.DATE, valid);
		            sdate = calendar.getTime();
		        }
		        soloinfo.setSdate(sdate);
		        sm.paysmember(soloinfo);
		    }else if(session.getAttribute("cid")!=null) {
				MCompanyDTO companysession = (MCompanyDTO) session.getAttribute("companysession");
		    	// 세션으로 아이디를 주문정보에 담기
		    	payDTO.setCid(companysession.getCid());
		    	MCompanyDTO compinfo = new MCompanyDTO();
		    	compinfo.setCid(companysession.getCid());
		    	// 타입을 2로 바꾸기
		    	compinfo.setCtype(2);
		    	// 유효기간 가져오기
		    	Date cdate = companysession.getCdate();
		        // cdate가 오늘 이전인 경우
		        if(cdate.before(today)) {
		            Calendar calendar = Calendar.getInstance();
		            calendar.setTime(today);
		            calendar.add(Calendar.DATE, valid); // 현재 날짜에 valid일을 더합니다.
		            cdate = calendar.getTime();
		         // cdate가 오늘 이후이거나 오늘인 경우
		        }else { 
		            // cdate에 valid일을 더함
		            Calendar calendar = Calendar.getInstance();
		            calendar.setTime(cdate);
		            calendar.add(Calendar.DATE, valid);
		            cdate = calendar.getTime();
		        }
		        compinfo.setCdate(cdate);
		        mcm.paycmember(compinfo);
		    }
//		    System.out.println("주문정보 저장시도: "+payDTO);
		    paym.insert(payDTO);  // 매퍼로 주문정보를 db에 저장)
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
