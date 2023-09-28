package aaa.controll;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import aaa.model.AdminDTO;
import aaa.model.MCompanyDTO;
import aaa.model.PageData;
import aaa.model.ReviewDTO;
import aaa.model.SoloDTO;
import aaa.service.MCompanyMapper;
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
	
	@Resource
	MCompanyMapper mcMapper;
	//현재날짜가져오기
	Date today = new Date();
	// 리뷰 리스트
	@RequestMapping("lo/{cid}/{page}")
	String list(@PathVariable String cid, Model mm, @PathVariable int page, HttpSession session, ReviewDTO dto) {
		SoloDTO sdto = (SoloDTO) session.getAttribute("solosession");
		MCompanyDTO cdto = (MCompanyDTO) session.getAttribute("companysession");
		AdminDTO adto = (AdminDTO)session.getAttribute("adminSession");
		System.out.println("뀨" + sdto);
		System.out.println("기업뀨" + cdto);
		//System.out.println(sdto.isSinjueng() + "이게 트루가 아님뭐야임마");
		
		
		dto.setMsg("구매시이용가능!");
		dto.setGoUrl("/product/notice");
		if(cdto==null && sdto==null && adto==null) {
			dto.setMsg("비회원은 이용이 불가합니다.");
			dto.setGoUrl("/login/main");
		} else {//비회원아닐때
			
		if (cdto != null) {
			cdto = mcMapper.deatilCompany(cdto.getCid());
			System.out.println(cdto);
		    // 기업회원인 경우의 처리
		    if (cdto.isCapproval()) {
		        dto.calc(reviewMapper.reviewCnt(cid));
		        List<ReviewDTO> data = reviewMapper.reviewList(dto);
		        mm.addAttribute("mainData", data);
		        dto.setMsg("기업회원님 입장");
		        dto.setGoUrl("/review/list/" + dto.getCid() + "/1");
		    }else if(!cdto.isCapproval()) {
		    	dto.setMsg("인증이 아직 완료 되지 않음");
				dto.setGoUrl("/product/notice");
		    }
		} else if (sdto != null) {
			sdto = srMapper.detailSolo(sdto.getSid());
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
		        dto.setMsg("리뷰 가능한 개인회원( 입장");
		        dto.setGoUrl("/review/list/" + dto.getCid() + "/1");
		    }
		}else if (adto != null) {
			dto.setMsg("관리자 입장");
	        dto.setGoUrl("/review/list/" + dto.getCid() + "/1");
		}
		}
		return "review/review_alert";
	}
	
	@RequestMapping("list/{cid}/{page}")
	String hihi(@PathVariable String cid, Model mm, @PathVariable int page, HttpSession session, ReviewDTO dto,PageData pd) {
		String sessioncid = (String) session.getAttribute("cid");
		
		if (sessioncid == null) {
			pd.setMsg("기업회원만 이용가능합니다");
			pd.setGoUrl("/");
			return "solo_resume/alert";
		}
		
		dto.calc(reviewMapper.reviewCnt(cid));
		System.out.println(reviewMapper.reviewCnt(cid));
		SoloDTO sdto = (SoloDTO) session.getAttribute("solosession");
		List<ReviewDTO> data = reviewMapper.reviewList(dto);
		System.out.println(dto);
		 System.out.println("sdto"+sdto);
		mm.addAttribute("mainData", data);
		
		return "review/review_list";
	}

	// 리뷰 작성 페이지
	@GetMapping("/insert/{cid}/{page}")
	String insert(Model mm, 
			ReviewDTO dto, 
			HttpSession session,
			@PathVariable String cid,
			PageData pd) {
		SoloDTO sdto = (SoloDTO) session.getAttribute("solosession");
		MCompanyDTO mcdto = mcMapper.deatilCompany(cid);
		dto.setCname(mcdto.getCname());
		  	dto.setMsg("리뷰작성불가");
	        dto.setGoUrl("/company/detail/" + dto.getCid());
        int cnt = reviewMapper.ccnt(sdto.getSid());
	    System.out.println("cnt"+cnt);
	    
	    SoloDTO sosession = srMapper.resumeSolo(sdto.getSid());
		
		Date ssdate = sosession.getSdate();
		int sstype = sosession.getStype();

		
		//결제관련 타입처리
		if(sstype==2 && ssdate!=null &&  ssdate.before(today)) {	//
			sosession.setStype(1);	
			srMapper.loginsmember(sosession);	
			System.out.println("타입수정완료!");
			pd.setMsg("개인회원 로그인 (리뷰열람권이 만료되었습니다.)");
		}
		
		
		// 객체세션발급
		SoloDTO solosession = srMapper.resumeSolo(sosession.sid);
		session.setAttribute("sid",sosession.sid);
		session.setAttribute("solosession", solosession);
		
	
	    
        if (cnt> 0&& sdto.getCid().equals(cid)) {
        	dto.setMsg("리뷰는 1회만 작성가능합니다.");
    	    dto.setGoUrl("/review/list/" + dto.getCid()+"/1");
	        return "review/review_alert";
        }else if (cnt> 0&& !sdto.getCid().equals(cid)) {
        	dto.setMsg("재직중인 기업만 리뷰를 작성할 수 있습니다.");
	        dto.setGoUrl("/review/list/" + dto.getCid()+"/1");
	        return "review/review_alert";
        }
		if (sosession.isSinjueng() && sosession.getCid().equals(cid)) {
				System.out.println("재직확인");
		        dto.setMsg("재직회원님 입장");
		        System.out.println("sdto"+sosession);
		        
		        
		        
		        return "review/review_insert";
		        
		    }
        
		
		mm.addAttribute("mainData", reviewMapper.reviewList(dto));
		return "review/review_alert";
	}

	// 리뷰 작성 페이지
	@PostMapping("/insert/{cid}/{page}")
	String insertReg(ReviewDTO dto, @PathVariable String cid, SoloDTO sdto, HttpSession session) {
		// 회사소환
		
		
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
		MCompanyDTO mcdto = mcMapper.deatilCompany(cid);
		dto.setCname(mcdto.getCname());
		System.out.println(cid + "널이냐?");
		dto.setCid(cid);
		dto.setSid(sid);
		System.out.println(dto);
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
	
	@RequestMapping("/delete/{cid}/{page}/{sid}")
	String delete(ReviewDTO dto,
			@PathVariable String cid,
			@PathVariable String sid,
			HttpSession session,
			SoloDTO sdto) {
		
		int cnt = reviewMapper.reviewDelete(dto);
		dto.setMsg("삭제실패");
		dto.setGoUrl("/review/modify/" + dto.getCid() + "/" + dto.getPage() + "/" + dto.getSid());
		if (cnt == 1) {
			dto.setMsg("삭제성공");
			
			
			// 세션 가져옴
			String ssid = (String) session.getAttribute("sid");
			SoloDTO solosession = (SoloDTO) session.getAttribute("solosession");
			
			// solosession의 stype 속성을 2로 설정
			sdto.setSid(sid);
			sdto.setStype(1);
			sdto.setSdate(null);
			solosession.setStype(1);
			solosession.setSdate(null);
			// 수정된 solosession을 다시 세션에 설정
			session.setAttribute("sid", sdto.getSid());
			session.setAttribute("solosession", solosession);

			// 타입 변경
			srMapper.loginsmember(sdto);
			System.out.println("내가 sdto야");
			System.out.println(sdto);
			
			dto.setGoUrl("/review/list/" + cid + "/1");
			
		}
		
		return "review/review_alert";
	}

}
