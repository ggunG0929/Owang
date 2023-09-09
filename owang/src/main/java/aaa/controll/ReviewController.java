package aaa.controll;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import aaa.model.ReviewDTO;
import aaa.service.ReviewMapper;
import jakarta.annotation.Resource;

@Controller
@RequestMapping("review")
public class ReviewController {
	
	@Resource
	ReviewMapper reviewMapper;
	
	@RequestMapping("list/{cid}/{page}")
	String list(
			@PathVariable String cid,
			Model mm, 
			@PathVariable int page,
			ReviewDTO dto) {
	    
	  
		dto.calc(reviewMapper.reviewCnt(cid));
		System.out.println(reviewMapper.reviewCnt(cid));
	    List<ReviewDTO> data = reviewMapper.reviewList(dto);
	    System.out.println(dto);

	    mm.addAttribute("mainData", data);

	    return "review/review_list";
	}


}
