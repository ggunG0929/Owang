package aaa.controll;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin")
public class AdminController {
	
	@RequestMapping("/product")
	String admin_product()  {
		return "admin/admin_product";
	}

}
