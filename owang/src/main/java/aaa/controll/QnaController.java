package aaa.controll;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/qna")
public class QnaController {
	
	@RequestMapping("qna")
	String qna() {
		
		return "qna/qna";
	}
	
	@RequestMapping("qna/qna_view1")
	String qna_view1() {
		
		return "qna/qna_view1";
	}
	
	@RequestMapping("qna/qna_view2")
	String qna_view2() {
		
		return "qna/qna_view2";
	}
	
	
	@RequestMapping("qna/qna_view3")
	String qna_view3() {
		
		return "qna/qna_view3";
	}
	
	@RequestMapping("qna/qna_view4")
	String qna_view4() {
		
		return "qna/qna_view4";
	}
	
	@RequestMapping("qna/qna_view5")
	String qna_view5() {
		
		return "qna/qna_view5";
	}
	
	@RequestMapping("qna/qna_view6")
	String qna_view6() {
		
		return "qna/qna_view6";
	}
	
	@RequestMapping("qna/qna_view7")
	String qna_view7() {
		
		return "qna/qna_view7";
	}
	
	
	
}
