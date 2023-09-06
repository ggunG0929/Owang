package aaa.controll;

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
		List<ProductDTO> data= pm.list();
		mm.addAttribute("data", data);
		return "product/product_notice";
	}
	
	@RequestMapping("/solo")
	String product_solo() {
		return "product/product_solo_form";
	}
	
	@RequestMapping("/company")
	String product_company() {
		return "product/product_company_form";
	}
	
	@Resource
	PayMapper paym;

	@RequestMapping("/result/{impUid}")
	String result(@PathVariable String impUid, Model mm) {
		PaymentDTO dto = paym.detail(impUid);
		mm.addAttribute("dto", dto);
		return "product/product_result";
	}
	
	@RequestMapping(value ="complete", method = RequestMethod.POST)
	@ResponseBody
	public int paymentComplete(String imp_uid, String merchant_uid, String totalPrice,
		HttpSession session, @RequestBody PaymentDTO orderDTO) throws Exception {
		    System.out.println("dto: " + orderDTO);
			// 서비스에서 토큰 가져옴
		    String token = PayService.getToken();
		    System.out.println("토큰가져옴: " + token);
		    // 결제 완료된 금액 // 서비스에서 imp_uid와 토큰으로 결제내역 가져옴
		    String amount = PayService.paymentInfo(orderDTO.getImpUid(), token);
		    System.out.println("금액가져옴: "+amount);
		    int res = 1;
		    if (orderDTO.getPaidAmount() != Integer.parseInt(amount)) {
		    	res = 0;
		    	System.out.println("검증실패");
				// 주문의 결제금과 결제된 금액이 다르다면 결제 취소
				PayService.payMentCancle(token, orderDTO.getImpUid(), amount,"결제 금액 오류"); // 서비스에서 취소동작
				return res;
			}
		    System.out.println("주문정보 저장시도: "+orderDTO);
		    paym.insert(orderDTO);  // 서비스에서 주문정보를 페이에 넣음(db저장)
			return res;
	}

}
