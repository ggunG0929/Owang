package aaa.controll;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import aaa.model.NoticeDTO;
import aaa.model.RecruitDTO;
import aaa.service.NoticeMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;

//공지사항 컨트롤러
//연결다하고 html이름 변경하기
@Controller
@RequestMapping("/notice") //공지사항 - 화룡님꺼 이어서
public class CusController {
	
	@Resource // 맵퍼가져오기
	NoticeMapper ntmp; //공지맵퍼
	
	//리스트
	@RequestMapping("notice/{page}")
	String notice(Model mm, NoticeDTO dto) {
		
		dto.calc(ntmp.listCnt());
		System.out.println(dto);
		List<NoticeDTO>data = ntmp.list(dto);
		mm.addAttribute("mainData", data);
		
		return "notice/list";
	}
	
	//상세페이지 연결 ok
	@RequestMapping("detail/{page}/{id}")
	String detail(Model mm, @PathVariable int page, @PathVariable int id) {
		ntmp.addCount(id);
		NoticeDTO dto = ntmp.detail(id);
		dto.setPage(page);
		mm.addAttribute("ndto", dto); // dto로 넣어서 오류났었음
		return "notice/detail";
	}
	
	//글쓰기 페이지
	@GetMapping("insert/{page}")
	String insert(NoticeDTO dto) {

		return "notice/insertForm";
	}
	
	
	@PostMapping("insert/{page}")
	String insertReg(NoticeDTO dto, HttpServletRequest request) {
		dto.setId(ntmp.maxId()+1);
		//fileSave(dto,request);
		ntmp.insseerr(dto);
		dto.setMsg("작성되었습니다.");
		dto.setGoUrl("/notice/notice/1");
		System.out.println(dto);

		return "notice/alert";
	}
	
	//게시글삭제 - 관리자만 삭제해야함...

	
	@RequestMapping("delete/{page}/{id}") // 지워지는데 뷰에서 오류
	String deleteReg(NoticeDTO dto, HttpServletRequest request) {
		
		
		dto.setMsg("삭제에 실패했습니다.");
		dto.setGoUrl("/notice/delete/"+dto.getPage()+"/"+dto.getId());
		
		NoticeDTO delDTO = ntmp.detail(dto.getId());
		
		int cnt = ntmp.delettt(dto);
		System.out.println("deleteReg:"+cnt);
		if(cnt>0) {
			
			dto.setMsg("삭제되었습니다.");
			dto.setGoUrl("/notice/notice/1"); // 수정완ok
		}

		return "notice/alert";
	}
	
	//수정페이지
	
	@GetMapping("modify/{page}/{id}")
	String modify(Model mm, @PathVariable int page, @PathVariable int id) {
		
		NoticeDTO dto = ntmp.detail(id);

		mm.addAttribute("dto",dto);
		
		
		return "notice/modifyform";
	}
	
	@PostMapping("modify/{page}/{id}")
	String modifyReg(HttpServletRequest request, NoticeDTO dto) {
		
		dto.setMsg("수정 실패입니다.");
	    dto.setGoUrl("notice/modify" + dto.getPage() + "/" +dto.getId());

	    // 수정된 데이터를 데이터베이스에 저장
	    int cnt = ntmp.modifffy(dto);
	    
	    System.out.println("cnt!"+cnt);
	    
	    if (cnt > 0) {
	        dto.setMsg("수정되었습니다.");
	        dto.setGoUrl("/notice/detail/" + dto.getPage() + "/" + dto.getId());
	    }
		
		
		return "notice/alert"; 
	}
	
	
	
	
	
	
	
	
	
	
	
	//화룡님기재하셨던거~
//	@RequestMapping("notice/notice_view1")
//	String notice_view1() {
//		
//		return "notice/notice_view1";
//	}
//	
//	@RequestMapping("notice/notice_view2")
//	String notice_view2() {
//		
//		return "notice/notice_view2";
//	}
//	
//	
//	@RequestMapping("notice/notice_view3")
//	String notice_view3() {
//		
//		return "notice/notice_view3";
//	}
//	
//	@RequestMapping("notice/notice_view4")
//	String notice_view4() {
//		
//		return "notice/notice_view4";
//	}
//	
//	@RequestMapping("notice/notice_view5")
//	String notice_view5() {
//		
//		return "notice/notice_view5";
//	}
	
	
	
}
