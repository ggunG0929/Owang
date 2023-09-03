package aaa.controll;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/notice")
public class CusController {
	
	@RequestMapping("notice")
	String notice() {
		
		return "notice/notice";
	}
	
	@RequestMapping("notice/notice_view1")
	String notice_view1() {
		
		return "notice/notice_view1";
	}
	
	@RequestMapping("notice/notice_view2")
	String notice_view2() {
		
		return "notice/notice_view2";
	}
	
	
	@RequestMapping("notice/notice_view3")
	String notice_view3() {
		
		return "notice/notice_view3";
	}
	
	@RequestMapping("notice/notice_view4")
	String notice_view4() {
		
		return "notice/notice_view4";
	}
	
	@RequestMapping("notice/notice_view5")
	String notice_view5() {
		
		return "notice/notice_view5";
	}
	
	
	
}
