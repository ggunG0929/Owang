package aaa.controll;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import aaa.model.AskDTO;



import aaa.service.AskMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

@Controller
@RequestMapping("/ask") // Qna 오류로 다시만든거...
public class AskController {
	
	@Resource
	AskMapper mapper;
	
	@RequestMapping("list/{page}")
	String list(Model mm, AskDTO dto) {
		
		
		dto.calc(mapper.listCnt());
		System.out.println("askdto" + dto);
		List<AskDTO>data = mapper.askList(dto);
		mm.addAttribute("mainData", data);
		
		return "ask/ask_list";
	}
	
	@RequestMapping("detail/{page}/{id}")
	String detail(Model mm, @PathVariable int page, @PathVariable int id) {
		mapper.addCount(id);// 조회수증가
		AskDTO dto = mapper.detail(id);
		dto.setPage(page);
		mm.addAttribute("askdto", dto);
		return "ask/ask_detail";
	}
	
	//글을써보자
	@GetMapping("insert/{page}")
	String insert(AskDTO dto) {
		return "ask/ask_insertForm";
	}
	
	@PostMapping("insert/{page}")
	String insertReg(AskDTO dto,HttpServletRequest request ) {
		
		dto.setId(mapper.maxId()+1);
		//fileSave(dto,request);
		mapper.askInsert(dto);
		dto.setMsg("게시글 등록되었습니다.");
		dto.setGoUrl("/ask/list/1");
		
		
		return "ask/ask_alert";
	}
	
	//글을지워보자꾸나 
	@RequestMapping("delete/{page}/{id}")
	String delete(AskDTO dto, HttpServletRequest request ,@PathVariable int id) {
		
//		AskDTO adto = mapper.detail(id);
//		System.out.println("ask 삭제 dto"+adto);
		dto.setMsg("삭제에 실패했습니다");
		dto.setGoUrl("/ask/detail"+dto.getPage()+"/"+dto.getId());
		
		 AskDTO delDTO = mapper.detail(dto.getId());
		 
		 int cnt = mapper.askDelete(delDTO);
		 System.out.println("deleteReg:"+cnt);
		 
		 if(cnt>0) {
			 
			 dto.setMsg("삭제완");
			 dto.setGoUrl("/ask/list/1");
		 }
		
		return "ask/ask_alert";
	}
	
	
	//수정페이지
	@GetMapping("modify/{page}/{id}")
	String modify(Model mm, @PathVariable int page, @PathVariable int id) {
		
		AskDTO dto = mapper.detail(id);
		dto.setPage(page);
		mm.addAttribute("dto", dto);
		
		return "ask/ask_modifyform";
	}
	
	
	@PostMapping("modify/{page}/{id}")
	String modifyReg(AskDTO dto, HttpServletRequest request ) {
		
		dto.setMsg("1:1 - 수정 실패입니다.");
	    dto.setGoUrl("/ask/modify" + dto.getPage() + "/" +dto.getId());

	    // 수정된 데이터를 데이터베이스에 저장
	     int cnt = mapper.askModify(dto);
	    
	    System.out.println("1:1의 cnt"+cnt);
	    
	    if (cnt > 0) {
	        dto.setMsg("1:1 -수정되었습니다.");
	        dto.setGoUrl("/ask/detail/" + dto.getPage() + "/" + dto.getId());
	    }
		
		
		return "ask/ask_alert"; 
	} 
	
	//답변을만들어보자!
	@GetMapping("reply/{page}/{id}")
	String reply(Model mm,@PathVariable int page, @PathVariable int id ) {
		
		AskDTO dto = new AskDTO();
		dto.setPage(page);
		dto.setId(id);
		mm.addAttribute("askdto",dto);
		
		return "ask/ask_replyForm";
	}
	
//	@PostMapping("reply/{page}/{id}")
//	String replyReg(AskDTO dto, HttpServletRequest request, @PathVariable int page, @PathVariable int id) {
//	    dto.setPage(page);
//	    dto.setGid(id); // 부모 글의 id를 gid로 설정하여 답글 그룹을 형성
//	    dto.setLev(1);  // 답글의 계층을 1로 설정
//	    dto.setSeq(1);  // 답글의 순서를 1로 설정 (첫 번째 답글)
//
//	    // Mapper를 사용하여 답글 데이터를 데이터베이스에 저장
//	    int result = mapper.insertReply(dto);
//	    
//
//	    if (result > 0) {
//	        dto.setMsg("답글 작성이 완료되었습니다.");
//	        dto.setGoUrl("/ask/detail/" + page + "/" + id);
//	    } else {
//	        dto.setMsg("답글 작성에 실패했습니다.");
//	        dto.setGoUrl("/ask/reply/" + page + "/" + id);
//	    }
//	    
//	    return "ask/ask_alert";
//	}
	
	@PostMapping("reply/{page}/{id}")
	String replyReg(AskDTO dto, HttpServletRequest request, @PathVariable("page") int page, @PathVariable("id") int id) {
	    dto.setPage(page);
	    dto.setGid(id); // 부모 글의 id를 gid로 설정하여 답글 그룹을 형성
	    dto.setLev(1);  // 답글의 계층을 1로 설정
	    dto.setSeq(1);  // 답글의 순서를 1로 설정 (첫 번째 답글)

	    // Mapper를 사용하여 답글 데이터를 데이터베이스에 저장
	    int result = mapper.insertReply(dto);
	    

	    if (result > 0) {
	        dto.setMsg("답글 작성이 완료되었습니다.");
	        dto.setGoUrl("/ask/detail/" + page + "/" + id);
	    } else {
	        dto.setMsg("답글 작성에 실패했습니다.");
	        dto.setGoUrl("/ask/reply/" + page + "/" + id);
	    }
	    
	    return "ask/ask_alert";
	}
	
	
	
}
