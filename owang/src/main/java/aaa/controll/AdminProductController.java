package aaa.controll;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
        pm.insert(productDTO);
        return "redirect:/admin_product/modify"; // 목록 페이지로 리다이렉션
    }
    @RequestMapping("/deleteProduct")
    public String deleteProduct(@RequestParam("productId") String productId) {
        pm.delete(productId);
        return "redirect:/admin_product/modify"; // 목록 페이지로 리다이렉션
    }

    
    @Resource
    PayMapper paym;

    // 정산그래프
	@RequestMapping("/graph")
	String admin_product_graph(Model mm)  {
        List<Map<String, Object>> dailytotal = paym.dailytotal();

        // 첫 인덱스는 총 매출로 따로 처리
        BigDecimal totalAmount = (BigDecimal) dailytotal.get(0).get("totalAmount");
        // 나머지는 그래프 데이터로 사용
        List<Map<String, Object>> graphData = dailytotal.subList(1, dailytotal.size());

        // 순위리스트
        List<Map<String, Object>> totalbys = paym.totalbys();
        List<Map<String, Object>> totalbyc = paym.totalbyc();
        
        List<Map<String, Object>> slist = new ArrayList<>();
        List<Map<String, Object>> clist = new ArrayList<>();

        for (Map<String, Object> map : totalbys) {
            if (!map.containsKey("sid")) {
                map.put("sid", ""); // 만약 'sid' 키가 없다면 빈 문자열로 추가
            }
            slist.add(map);
        }
        for (Map<String, Object> map : totalbyc) {
        	if (!map.containsKey("cid")) {
        		map.put("cid", ""); // 만약 'cid' 키가 없다면 빈 문자열로 추가
        	}
        	clist.add(map);
        }
        mm.addAttribute("totalAmount", totalAmount);
        mm.addAttribute("graphData", graphData);
        mm.addAttribute("slist", slist);
        mm.addAttribute("clist", clist);
//        System.out.println(graphData);
//        System.out.println(totalbys);
//        System.out.println(totalbyc);
		return "admin/product/graph";
	}
	
	
	// 전체 매출내역
	@RequestMapping("/payment/{page}")
    public String getPayments(Model mm, @PathVariable(required = false) int page) throws Exception {
        String token = PayService.getToken(); // PayService를 통해 토큰을 가져옴
        // API에 대한 요청을 위한 URL 생성		// 결제상태/페이지당 개수/결제일순(내림차순)
        String apiUrl = "https://api.iamport.kr/payments/status/paid?page="+page+"&limit=15&sorting=-paid&_token=" + token;
        // rest템플릿
        RestTemplate restTemplate = new RestTemplate();
        // PaymentResponse 형태로 정보받음
        ResponseEntity<PaymentResponseAll> responseEntity = restTemplate.getForEntity(apiUrl, PaymentResponseAll.class);
        List<PaymentAll> paymentData = responseEntity.getBody().getResponse().getList();
 
        for (PaymentResponseAll.PaymentAll payment : paymentData) {
        	
        	// imp_uid로 db정보 검색해서 id 가져옴
            String id = paym.idget(payment.getImp_uid());
            payment.setId(id);
            
            // paid_at을 포맷팅
            Long ldate = Long.parseLong(payment.getPaid_at());
            Date date = new Date(ldate * 1000L);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            payment.setPaid_at(sdf.format(date));
        }
        int total = responseEntity.getBody().getResponse().getTotal();
        int totalPages = (int)Math.ceil((double)total / 15);
        
//        System.out.println(responseEntity);
        mm.addAttribute("paymentData", paymentData);
        mm.addAttribute("page", page);
        mm.addAttribute("totalPages", totalPages);
        return "admin/product/payment_admin"; 
    }
}
