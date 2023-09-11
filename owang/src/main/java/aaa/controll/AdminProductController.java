package aaa.controll;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.util.StringUtils;

import aaa.model.PaymentResponseAll;
import aaa.model.PaymentResponseMember;
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
	
	// 상품관리
	@RequestMapping("/modify")
	String admin_product_modify(Model mm)  {
		// 상품 목록
		List<ProductDTO> data= pm.list();
		mm.addAttribute("data", data);
		// 상품 추가 위한 DTO
		mm.addAttribute("ProductDTO", new ProductDTO());
		return "admin/product/modify";
	}
    @PostMapping("/insertProduct")
    public String addProduct(@ModelAttribute ProductDTO productDTO) {
    	// DTO 정보를 db에 저장(상품추가)
        pm.insert(productDTO);
        return "redirect:/admin_product/modify"; // 목록 페이지로 리다이렉트(새로고침)
    }
    @RequestMapping("/deleteProduct")
    public String deleteProduct(@RequestParam("productId") String productId) {
    	// 파라미터로 전달받은 상품 id를 통해 db 삭제(상품삭제)
        pm.delete(productId);
        return "redirect:/admin_product/modify";
    }

    
    @Resource
    PayMapper paym;

    // 결제정산
	@RequestMapping("/graph")
	String admin_product_graph(Model mm,
			@RequestParam(name = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam(name = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate)  {
		
		// 그래프 데이타
		// db에서 일매출 합산
        List<Map<String, Object>> dailytotal = paym.dailytotal(startDate, endDate);
//        System.out.println("dailytotal = "+dailytotal);

        // 리스트 데이타
        // db에서 회원종류별 매출액 순위리스트
        List<Map<String, Object>> totalbys = paym.totalbys(startDate, endDate);
        List<Map<String, Object>> totalbyc = paym.totalbyc(startDate, endDate);
//        System.out.println("totalbys = "+totalbys);
//        System.out.println("totalbyc = "+totalbyc);
        // db에서 상품별 매출액 순위리스트
        List<Map<String, Object>> totalbyp = paym.totalbyp(startDate, endDate);
//        System.out.println("totalbyp = "+totalbyp);
        // 회원자료 중 sid나 cid가 없는 자료 제외하기
        totalbys = totalbys.stream()
        		.filter(map -> map.containsKey("sid"))
                .collect(Collectors.toList());
        totalbyc = totalbyc.stream()
                .filter(map -> map.containsKey("cid"))
                .collect(Collectors.toList());
        // 기간내 값들 합산
        int totalSum = mapSum(dailytotal, "totalAmount");
        int sSum = mapSum(totalbys, "total");
        int cSum = mapSum(totalbyc, "total");
        int pSum = mapSum(totalbyp, "total");
//    	System.out.println(totalSum);
    	// 오늘날짜	// 날짜 선택시 오늘 이후 날짜는 선택 불가하도록
    	String today = PayService.dateformat(new Date()); 
//    	System.out.println(today);
    	// 선택한 날짜 뜨도록 string으로 보내주기
    	String strStartDate = null;
    	String strEndDate = null;
    	if(startDate != null && endDate != null) {        	
    		strStartDate = PayService.dateformat(startDate);
    		strEndDate = PayService.dateformat(endDate);
    	}
//    	System.out.println(startDate);
//    	System.out.println(endDate);
        mm.addAttribute("graphData", dailytotal);
        mm.addAttribute("slist", totalbys);
        mm.addAttribute("clist", totalbyc);
        mm.addAttribute("plist", totalbyp);
        mm.addAttribute("totalSum", totalSum);
        mm.addAttribute("sSum", sSum);
        mm.addAttribute("cSum", cSum);
        mm.addAttribute("pSum", pSum);
        mm.addAttribute("today", today);
        mm.addAttribute("startDate", strStartDate);
        mm.addAttribute("endDate", strEndDate);        
        
		return "admin/product/graph";
	}
	// 맵리스트에서 키 값들을 합하는 메서드
	public int mapSum(List<Map<String, Object>> list, String key) {
	    return list.stream()
	            .mapToInt(entry -> ((BigDecimal) entry.get(key)).intValue())
	            .sum();
	}
	
	
	// 전체 매출내역
	@RequestMapping("/payment/{page}")
    public String getPayments(Model mm, 
    		@PathVariable(required = false) int page,
            @RequestParam(value = "status", defaultValue = "paid") String status,
            @RequestParam(value = "limit", defaultValue = "15") int limit,
            @RequestParam(value = "from", required = false) String from,
            @RequestParam(value = "to", required = false) String to,
            @RequestParam(value = "sorting", defaultValue = "-updated") String sorting
    		) throws Exception {		
		// 날짜 변환
		String range="";
		if(!StringUtils.isEmpty(from) && !StringUtils.isEmpty(to)) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		    Date fromDate = format.parse(from);
		    Date toDate = format.parse(to);
		    // Date 객체를 Unix 타임스탬프로 변환
		    long fromunix = fromDate.getTime() / 1000;
		    long tounix = toDate.getTime() / 1000;
//			&from=1670449156&to=1671053156
		    range = "&from="+fromunix+"&to="+tounix;
		}
		// API 통신
        String token = PayService.getToken(); // PayService를 통해 토큰을 가져옴
        // URL 생성
        String apiUrl = "https://api.iamport.kr/payments/status/"+status+"?page="
        		+page+"&limit="+limit+range+"&sorting="+sorting+"&_token=" + token;
        // rest템플릿
        RestTemplate restTemplate = new RestTemplate();
        // PaymentResponse 형태로 정보받음
        ResponseEntity<PaymentResponseAll> responseEntity = restTemplate.getForEntity(apiUrl, PaymentResponseAll.class);
        List<PaymentAll> paymentData = responseEntity.getBody().getResponse().getList();
 
        for (PaymentResponseAll.PaymentAll payment : paymentData) {       	
        	// imp_uid로 db정보 검색해서 id 가져옴
            String id = paym.idget(payment.getImp_uid());
            payment.setId(id);
            
            // paid_at이 0인 경우 "미결제"로 표시
            if (payment.getPaid_at().equals("0")) {
                payment.setPaid_at("미결제");
            } else {
                // paid_at을 포맷팅
                Long ldate = Long.parseLong(payment.getPaid_at());
                Date date = new Date(ldate * 1000L);
                payment.setPaid_at(PayService.timeformat(date));
            }
        }
        int total = responseEntity.getBody().getResponse().getTotal();
        int totalPages = (int)Math.ceil((double)total / limit);
//        System.out.println(responseEntity);
        mm.addAttribute("paymentData", paymentData);
        mm.addAttribute("page", page);
        mm.addAttribute("totalPages", totalPages);
        mm.addAttribute("status", status);
        mm.addAttribute("limit", limit);
        mm.addAttribute("from", from);
        mm.addAttribute("to", to);
        mm.addAttribute("sorting", sorting);
        return "admin/product/payment_admin"; 
    }
	
	
	@RequestMapping("/payment/member/{id}")
	String paymentById(Model mm, @PathVariable String id) throws Exception {
		// 아이디로 db의 impuid로 리스트를 만들어 가져오고, 서버에 보내 결제내역을 가져옴
		List<String> impuidList = paym.impuids(id);
//		System.out.println("impuidList: "+impuidList);
		
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
		// PaymentResponseMember 형태로 정보받음
		ResponseEntity<PaymentResponseMember> responseEntity = restTemplate.getForEntity(apiUrl, PaymentResponseMember.class);
		List<PaymentResponseMember.Payment> paymentData = responseEntity.getBody().getResponse();
		for (PaymentResponseMember.Payment payment : paymentData) {
			// paid_at을 포맷팅
			Long ldate = Long.parseLong(payment.getPaid_at());
			Date date = new Date(ldate * 1000L);
			payment.setPaid_at(PayService.timeformat(date));
		}
//        System.out.println("responseEntity: "+responseEntity);
		Collections.sort(paymentData, new Comparator<PaymentResponseMember.Payment>() {
		    public int compare(PaymentResponseMember.Payment a, PaymentResponseMember.Payment b) {
		        // paid_at을 기준으로 내림차순 정렬
		        return b.getPaid_at().compareTo(a.getPaid_at());
		    }
		});
		mm.addAttribute("paymentData", paymentData);
		return "product/payment";
	}
}
