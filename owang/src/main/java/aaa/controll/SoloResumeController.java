package aaa.controll;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import aaa.model.PageData;
import aaa.model.SoloDTO;
import aaa.model.SoloResumeDTO;
import aaa.service.SoloMapper;
import aaa.service.SoloResumeMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("solo_resume")
public class SoloResumeController {
	
	@Resource
	SoloResumeMapper rsmapper;
	
	@Resource
	SoloMapper smapper;
	
	// 이력서 리스트
	@RequestMapping("home") // 리스트 임
	String solo_resume(Model mm, SoloResumeDTO rdto, SoloDTO sdto, HttpSession session) {
		// 이력서 총 개수
		// 이게 우선 현재 로그인한 회원 세션(아이디만 넘김 sid) 가져옴
		String sid = (String)session.getAttribute("sid");
		SoloDTO solosession = (SoloDTO) session.getAttribute("solosession");
		
		int cnt = rsmapper.resumecnt();
		List<SoloResumeDTO> data = rsmapper.resumelist(sid);
		System.out.println(rdto.getCnt());
		mm.addAttribute("mainData", data);
		System.out.println(data);
		System.out.println(cnt);
		System.out.println(sid);
		mm.addAttribute("cnt", cnt);
		//mm.addAttribute("cnt", data);
		return "solo_resume/home";
	}
	
	// 이력서 디테일
	@RequestMapping("detail/{rsid}") 
	String solo_resume_detail(Model mm, HttpSession session) {
		
		
		//mm.addAttribute("rdto", rsmapper.resumedetail(sid));
		return "solo_resume/detail";
	}
	

	// 이력서 등록
	@GetMapping("write")
	String solo_resume_write(SoloResumeDTO rdto, SoloDTO sdto,
			 HttpSession session, Model mm) {
		// 세션 가져옴
		SoloDTO solosession = (SoloDTO) session.getAttribute("solosession");
		
		mm.addAttribute("solosession", solosession);
		System.out.println("solosession : " + solosession);
		return "solo_resume/write";
	}
	
	// 이력서 제출
	@PostMapping("write")
	String solo_resume_writeReg(SoloResumeDTO rdto, PageData pd, Model mm, 
			HttpServletRequest request) {

		fileSave(rdto, request);
		rsmapper.resumeinsert(rdto);
		pd.setMsg("이력서가 등록되었습니다.");
		pd.setGoUrl("home");
		return "solo_resume/alert";
	}

	// 이력서 수정
	@GetMapping("modify/{rsid}")
	String modify(SoloResumeDTO rdto, Model mm, @PathVariable int rsid) { 
		// 일단 디테일 정보 가져와 줌

		// mm.addAttribute("rdto", rsmapper.resumedetail(rsid));
		return "solo_resume/modifyForm";
	}
	
	
	@DeleteMapping("/delete/{rsid}")
	public ResponseEntity<String> solo_resume_delete(@PathVariable int rsid) {
	    try {
	        // 이력서 삭제 로직 구현
	        int deletedRows = rsmapper.resumedelete(rsid);

	        if (deletedRows > 0) {
	            return ResponseEntity.ok("이력서가 삭제되었습니다.");
	        } else {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("이력서 삭제 실패");
	        }
	    } catch (Exception e) {
	        // 예외 처리: 삭제 과정 중에 예외가 발생한 경우
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("이력서 삭제 중 오류 발생");
	    }
	}

    @DeleteMapping("/deleteReg/{rsid}")
    public ResponseEntity<String> solo_resume_deleteReg(@PathVariable int rsid) {
        SoloResumeDTO delDTO = rsmapper.resumedetail(rsid);
        if (delDTO != null) {
            int cnt = rsmapper.resumedelete(rsid);
            if (cnt > 0) {
                // 삭제 성공 시
                return ResponseEntity.ok("삭제되었습니다.");
            } else {
                // 삭제 실패 시
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("삭제 실패");
            }
        } else {
            // 해당 이력서를 찾을 수 없는 경우
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("삭제할 이력서를 찾을 수 없습니다.");
        }
    }
    
    // 파일 저장
	void fileSave(SoloResumeDTO rdto, HttpServletRequest request) {
		
		//파일 업로드 유무 확인
		if(rdto.getRsmmff().isEmpty()) {
			return;
		}
		
		String path = request.getServletContext().getRealPath("up");
		// 이건 가상서버이다, 배포 시에는 realPath로 가져온다
		path = "C:\\Final_Team\\owang\\src\\main\\webapp\\up";

		// 점 처리
		int dot = rdto.getRsmmff().getOriginalFilename().lastIndexOf(".");
		// 
		String fDomain = rdto.getRsmmff().getOriginalFilename().substring(0, dot);
		String ext = rdto.getRsmmff().getOriginalFilename().substring(dot);

		
		rdto.setRsphoto(fDomain+ext); 
		File rsmmff = new File(path+"\\"+rdto.getRsphoto());
		int cnt = 1;
		
		while(rsmmff.exists()) {
			 
			rdto.setRsphoto(fDomain+"_"+cnt+ext);
			rsmmff = new File(path+"\\"+rdto.getRsphoto());
			cnt++;
		}
		
		
		
		try {
			FileOutputStream fos = new FileOutputStream(rsmmff);
			
			fos.write(rdto.getRsmmff().getBytes());
			
			fos.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	void fileDeleteModule(SoloResumeDTO delDTO, HttpServletRequest request) {
		if(delDTO.getRsphoto()!=null) {
			String path = request.getServletContext().getRealPath("up");
			path = "C:\\Final_Team\\owang\\src\\main\\webapp\\up";
			
			new File(path+"\\"+delDTO.getRsphoto()).delete();
		}
	}   
}