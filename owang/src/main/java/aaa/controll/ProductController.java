package aaa.controll;

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

import aaa.model.PaymentDTO;
import aaa.model.ProductDTO;
import aaa.service.PayMapper;
import aaa.service.PayService;
import aaa.service.ProductMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("product")
public class ProductController {

	@RequestMapping("/")
	String product() {
		return "kjh";
	}
	
	@Resource
	ProductMapper pm;
	
	@RequestMapping("/notice")
	String product_notice(Model mm) {
		// 전체상품
		List<ProductDTO> data= pm.list();
		// 세션에 따라서 리스트를 다르게 뿌리거나 다른 쪽을 바로가기버튼 없애야 할 듯
		mm.addAttribute("data", data);
		return "product/product_notice";
	}
	
	@RequestMapping("/{productCode}")
	String product_form(@PathVariable String productCode, Model mm) {
		ProductDTO dto = pm.detail(productCode);
//		Date today = new Date();
//		Calendar calendar = Calendar.getInstance();
//		calendar.setTime(today);
//		calendar.add(Calendar.DAY_OF_MONTH, dto.getProductValid()); // 현재 날짜에 유효일을 더함
//		Date endDate = calendar.getTime();
		mm.addAttribute("dto", dto);
//		mm.addAttribute("endDate", endDate);	// 포맷팅 필요
		return "product/product_form";
	}
	
	// 주문정보 전달받음
	@RequestMapping(value ="/complete", method = RequestMethod.POST)
	@ResponseBody
	public int paymentComplete(@RequestBody PaymentDTO payDTO) throws Exception {
//		    System.out.println("dto: " + payDTO);
			// 서비스에서 토큰 가져옴
		    String token = PayService.getToken();
//		    System.out.println("토큰가져옴: " + token);
		    // 결제된 금액	// 서비스에서 imp_uid와 토큰으로 결제내역 가져옴
		    String amount = PayService.paymentInfo(payDTO.getImpUid(), token);
//		    System.out.println("금액가져옴: "+amount);
		    int res = 1;
		    // 주문정보 금액과 결제된 금액이 다를 경우 - 0
		    if (payDTO.getPaidAmount() != Integer.parseInt(amount)) {
		    	res = 0;
//		    	System.out.println("검증실패");
				// 결제 취소
				PayService.paymentCancle(token, payDTO.getImpUid(), amount, "결제 금액 오류");
				return res;
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
