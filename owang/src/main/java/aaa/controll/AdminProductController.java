package aaa.controll;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin_product")
public class AdminProductController {
	
	@RequestMapping("/modify")
	String admin_product_modify()  {
		return "admin/product/modify";
	}

	@RequestMapping("/graph")
	String admin_product_graph()  {
		return "admin/product/graph";
	}
	
	@RequestMapping("/payment")
	String admin_product_payment()  {
		return "admin/product/payment";
	}
	
}
