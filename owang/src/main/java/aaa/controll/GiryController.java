package aaa.controll;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GiryController {

	@RequestMapping("/")
	//@ResponseBody
	String homeGo() {
		System.out.println("홈이다");
		//return "homehome";
		return "giry";// views/giry.html
		
	}
/*		
	application.yaml 설정
	server :
		  port : 80
		spring :
		  thymeleaf:
		    prefix: views/
		    suffix: .html
		    cache: false
*/

	
			
			
		
		
	
}