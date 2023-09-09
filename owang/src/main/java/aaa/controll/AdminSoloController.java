package aaa.controll;

import java.io.FileInputStream;
import java.net.URLEncoder;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import aaa.model.MCompanyDTO;
import aaa.model.PageData;
import aaa.model.RecruitDTO;
import aaa.model.SoloDTO;
import aaa.service.AdminSolo;
import aaa.service.SoloMapper;
import aaa.service.SoloResumeMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin_solo")
public class AdminSoloController {
	
	@Resource
	AdminSolo adminsmapper;
	
	@RequestMapping("smanagement")
	String management() {
		return "admin/solo/smanagement";
		
	}
	
/*	@RequestMapping("sololist/{page}")
	String sololist(Model mm, SoloDTO dto ) {
		
		dto.calc(adminsmapper.soloListCnt());

		List<SoloDTO> data = adminsmapper.soloList(dto);
		
		mm.addAttribute("soloData",data);
		return "admin/solo/sololist";
	}*/
	
	//재직증명솔로 리스트
	@RequestMapping("sololist/{page}")
	String sololist(@PathVariable int page, Model mm, SoloDTO dto) {
	    dto.setPage(page); // page 값을 설정합니다.
	    dto.calc(adminsmapper.soloListCnt()); // 페이지 계산을 위해 적절한 total 값을 전달합니다.
	    List<SoloDTO> data = adminsmapper.soloList(dto);
	    mm.addAttribute("soloData", data);
	    return "admin/solo/sololist";
	}
	
	//재직증명솔로 리스트
	@RequestMapping("solocompany/{page}")
	String solocompany(@PathVariable int page, Model mm, SoloDTO dto) {
	    dto.setPage(page); // page 값을 설정합니다.
	    dto.calc(adminsmapper.solocomListCnt()); // 페이지 계산을 위해 적절한 total 값을 전달합니다.
	    List<SoloDTO> data = adminsmapper.solocomList(dto);
	    mm.addAttribute("soloData", data);
	    return "admin/solo/solocompany";
	}
	
	// 파일 다운로드
	@RequestMapping("/soloup/download/{filename}")
	void download(@PathVariable String filename,
				   HttpServletRequest request,
				   HttpServletResponse response) {
		System.out.println("이벤트체크=> 파일 다운로드");
		try {
			System.out.println(filename+", "+"파일님 오셧나요");
			String path = request.getServletContext().getRealPath("soloup");
			FileInputStream fis = new FileInputStream(path + "\\"+filename);
			// 인코딩 설정
			String encFName = URLEncoder.encode(filename,"utf-8");
		
			response.setHeader("Content-Disposition", "attachment; filename=" + encFName);

			
			ServletOutputStream sos = response.getOutputStream();
			
			byte[] buf = new byte[1024];
			while (fis.available()>0) {
				int len = fis.read(buf); // 읽은후 buf에 저장
				// len = 넣은 길이
				sos.write(buf,0,len);// buf 0부터 len 만큼 
				
			}
			
			sos.close();
			fis.close();
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}

	}// 파일 다운로드
	
	@RequestMapping("checkId/{sno}")
	String checkId(@PathVariable int sno,SoloDTO dto) {
		adminsmapper.checkoutFile(dto.getSno());
		return "redirect:/admin_solo/solocompany/1";
	}
	
	//검색
	@GetMapping("/search")
	public String search(@RequestParam("keyword") String keyword, 
			@RequestParam("searchOption") String searchOption,
			@ModelAttribute SoloDTO dto,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			Model model) {
			System.out.println(keyword+", "+searchOption);
		 List<SoloDTO> data = adminsmapper.searchRecruit(keyword, searchOption);
		    
		    // 페이징 정보 설정
		    dto.setPage(page); // 페이지 설정
		    dto.calc(data.size()); // 페이지 계산 메서드 호출
		    System.out.println(dto+"하이이");
		    // 해당 페이지에 필요한 데이터만 가져오기
		    int startIndex = dto.getStart();
		    int endIndex = startIndex + dto.getLimit();
		    if (endIndex > data.size()) {
		        endIndex = data.size();
		    }
		    List<SoloDTO> paginatedData = data.subList(startIndex, endIndex);
		
		
		try {
		
			model.addAttribute("mainData", paginatedData);
			
		} catch (Exception e) {
			model.addAttribute("mainData", "검색결과가 없습니다");
		}

		return "admin/solo/searchResult";
	}
}

