package aaa.controll;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aaa.model.ProductDTO;
import aaa.service.ProductMapper;
import jakarta.annotation.Resource;

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

}
