package aaa.controll;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("solo")
public class SoloController {
	
	@RequestMapping("/product")
	String product() {
		return "solo/product";
	}
	
}
