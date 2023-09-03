package aaa.controll;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("product")
public class ProductController {
	
	@RequestMapping("/")
	String product() {
//		return "product/product_notice";
		return "kjh";
	}
	
	@RequestMapping("/solo")
	String product_solo() {
		return "product/product_solo_form";
	}
	
	@RequestMapping("/company")
	String product_company() {
		return "product/product_company_form";
	}

}
