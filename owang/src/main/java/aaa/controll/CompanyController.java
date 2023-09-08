package aaa.controll;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import aaa.model.PaymentResponseMember;
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
	
}
