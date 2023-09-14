package aaa.controll;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import aaa.model.MCompanyDTO;
import aaa.model.ReviewDTO;
import aaa.model.SoloDTO;
import aaa.service.ReviewMapper;
import aaa.service.SoloMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("review")
public class ReviewController {

	@Resource
	ReviewMapper reviewMapper;

	@Resource
	SoloMapper srMapper;

	// 리뷰 리스트
	@RequestMapping("lo/{cid}/{page}")
	String list(@PathVariable String cid, Model mm, @PathVariable int page, HttpSession session, ReviewDTO dto) {
		SoloDTO sdto = (SoloDTO) session.getAttribute("solosession");
		MCompanyDTO cdto = (MCompanyDTO) session.getAttribute("companysession");
		System.out.println("뀨" + sdto);
		System.out.println("기업뀨" + cdto);
		//System.out.println(sdto.isSinjueng() + "이게 트루가 아님뭐야임마");
		
		dto.setMsg("구매시이용가능!");
		dto.setGoUrl("/product/notice");
		
		if (cdto != null) {
			System.out.println(cdto);
		    // 기업회원인 경우의 처리
		    if (cdto.isCapproval()) {
		        dto.calc(reviewMapper.reviewCnt(cid));
		        List<ReviewDTO> data = reviewMapper.reviewList(dto);
		        mm.addAttribute("mainData", data);
		        dto.setMsg("기업회원님 입장");
		        dto.setGoUrl("/review/list/" + dto.getCid() + "/1");
		    }
		} else if (sdto != null) {
		    // 개인회원인 경우의 처리
		    if (sdto.isSinjueng() && sdto.getCid().equals(cid)) {
		        dto.calc(reviewMapper.reviewCnt(cid));
		        List<ReviewDTO> data = reviewMapper.reviewList(dto);
		        mm.addAttribute("mainData", data);
		        dto.setMsg("재직회원님 입장");
		        dto.setGoUrl("/review/list/" + dto.getCid() + "/1");
		    }

		    if (sdto.getStype() == 2) {
		        dto.calc(reviewMapper.reviewCnt(cid));
		        List<ReviewDTO> data = reviewMapper.reviewList(dto);
		        mm.addAttribute("mainData", data);
		        dto.setMsg("리뷰권구입고객님 입장");
		        dto.setGoUrl("/review/list/" + dto.getCid() + "/1");
		    }
		}
		
		return "review/review_alert";
	}
	
	@RequestMapping("list/{cid}/{page}")
	String hihi(@PathVariable String cid, Model mm, @PathVariable int page, HttpSession session, ReviewDTO dto) {
		dto.calc(reviewMapper.reviewCnt(cid));
		System.out.println(reviewMapper.reviewCnt(cid));
		List<ReviewDTO> data = reviewMapper.reviewList(dto);
		System.out.println(dto);

		mm.addAttribute("mainData", data);
		return "review/review_list";
	}

	// 리뷰 작성 페이지
	@GetMapping("/insert/{cid}/{page}")
	String insert(Model mm, ReviewDTO dto) {

		mm.addAttribute("mainData", reviewMapper.reviewList(dto));
		return "review/review_insert";
	}

	// 리뷰 작성 페이지
	@PostMapping("/insert/{cid}/{page}")
	String insertReg(ReviewDTO dto, @PathVariable String cid, SoloDTO sdto, HttpSession session) {
		// 리뷰 삽입
		reviewMapper.reveiwInsert(dto);

		// 세션 가져옴
		String sid = (String) session.getAttribute("sid");
		SoloDTO solosession = (SoloDTO) session.getAttribute("solosession");

		// solosession의 stype 속성을 2로 설정
		sdto.setSid(sid);
		sdto.setStype(2);
		sdto.setSdate(null);
		solosession.setStype(2);
		solosession.setSdate(null);
		// 수정된 solosession을 다시 세션에 설정
		session.setAttribute("sid", sdto.getSid());
		session.setAttribute("solosession", solosession);

		// 타입 변경
		srMapper.loginsmember(sdto);
		System.out.println("내가 sdto야");
		System.out.println(sdto);
		dto.setMsg("작성 완료!");
		dto.setGoUrl("/review/list/" + cid + "/1");
		return "review/review_alert";
	}

	// 리뷰수정페이지
	@GetMapping("/modify/{cid}/{page}/{sid}")
	String modify(Model mm, @PathVariable String cid, ReviewDTO dto, @PathVariable String sid) {

		System.out.println(cid + "널이냐?");
		dto.setCid(cid);
		dto.setSid(sid);
		ReviewDTO rdto = reviewMapper.reviewDetail(dto);
		System.out.println(rdto);
		System.out.println();

		mm.addAttribute("mainData", rdto);
		return "review/review_modify";
	}

	// 리뷰 데이서 수정 페이지
	@PostMapping("/modify/{cid}/{page}/{sid}")
	String modifyReg(ReviewDTO dto, @PathVariable String cid, @PathVariable String sid) {

		int cnt = reviewMapper.reviewModify(dto);

		if (cnt == 0) {
			dto.setMsg("수정실패");
			dto.setGoUrl("/review/modify/" + dto.getCid() + "/" + dto.getPage() + "/" + dto.getSid());
		}
		dto.setMsg("수정성공");
		dto.setGoUrl("/review/list/" + cid + "/1");

		return "review/review_alert";
	}

}
