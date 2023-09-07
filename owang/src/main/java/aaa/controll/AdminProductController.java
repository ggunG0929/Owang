package aaa.controll;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import aaa.model.PaymentResponseAll;
import aaa.model.PaymentResponseAll.PaymentAll;
import aaa.model.ProductDTO;
import aaa.service.PayMapper;
import aaa.service.PayService;
import aaa.service.ProductMapper;
import jakarta.annotation.Resource;

@Controller
@RequestMapping("admin_product")
public class AdminProductController {
	
	@Resource
	ProductMapper pm;
	
	// 상품 추가삭제 목록
	@RequestMapping("/modify")
	String admin_product_modify(Model mm)  {
		List<ProductDTO> data= pm.list();
		mm.addAttribute("ProductDTO", new ProductDTO());
		mm.addAttribute("data", data);
		return "admin/product/modify";
	}
    @PostMapping("/insertProduct")
    public String addProduct(@ModelAttribute ProductDTO productDTO) {
        // 상품 추가 로직 구현 (Service나 Repository를 사용하여 데이터베이스에 저장)
        pm.insert(productDTO);
        return "redirect:/admin_product/modify"; // 목록 페이지로 리다이렉션
    }
    @RequestMapping("/deleteProduct")
    public String deleteProduct(@RequestParam("productCode") String productCode) {
        pm.delete(productCode);
        return "redirect:/admin_product/modify"; // 목록 페이지로 리다이렉션
    }

    // 정산그래프
	@RequestMapping("/graph")
	String admin_product_graph()  {
		return "admin/product/graph";
	}
	
	@Resource
	PayMapper paym;
	
	// 전체 매출내역
	@RequestMapping("/payment")
    public String getPayments(Model mm) throws Exception {
        String token = PayService.getToken(); // PayService를 통해 토큰을 가져옴
        // API에 대한 요청을 위한 URL 생성		// 모든 상태(지금은 데이터가 적어서, 원래는 paid로 하면 됨)/1000개/결제일순(내림차순)
        String apiUrl = "https://api.iamport.kr/payments/status/all?limit=1000&sorting=-paid&_token=" + token;
        // rest템플릿
        RestTemplate restTemplate = new RestTemplate();
        // PaymentResponse 형태로 정보받음
        ResponseEntity<PaymentResponseAll> responseEntity = restTemplate.getForEntity(apiUrl, PaymentResponseAll.class);
        List<PaymentAll> paymentData = responseEntity.getBody().getResponse().getList();
        for (PaymentResponseAll.PaymentAll payment : paymentData) {
        	// imp_uid로 db정보 검색해서 id 가져옴
            String id = paym.idget(payment.getImp_uid());
            // id 세팅
            payment.setId(id);
            
            // paid_at을 포맷팅
            Long ldate = Long.parseLong(payment.getPaid_at());
            Date date = new Date(ldate * 1000L);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            payment.setPaid_at(sdf.format(date));
        }
//        System.out.println(responseEntity);
        mm.addAttribute("paymentData", paymentData);
        return "admin/product/payment_admin"; 
    }
}
