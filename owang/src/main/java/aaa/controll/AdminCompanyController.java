package aaa.controll;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import aaa.model.MCompanyDTO;
import aaa.model.PageData;
import aaa.model.RecruitDTO;
import aaa.service.AdminCompanyMapper;

import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/admin_company")
public class AdminCompanyController {
	
	@Resource
	AdminCompanyMapper adminMapper;
	
	static String path = "E:\\BackEnd_hakwon\\Spring_Team\\owang\\src\\main\\webapp\\companyup";
	
	@GetMapping("/search")
	String search(@RequestParam("keyword") String keyword, 
			@RequestParam("searchOption") String searchOption,
			@ModelAttribute MCompanyDTO dto,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			Model model) {
		List<MCompanyDTO> data = adminMapper.searchCompany(keyword, searchOption);
		for (MCompanyDTO mCompanyDTO : data) {
			mCompanyDTO.setPage(page);
			mCompanyDTO.calc(data.size());
		}
		int startIndex = dto.getStart();
	    int endIndex = startIndex + dto.getLimit();
	    if (endIndex > data.size()) {
	        endIndex = data.size();
	    }
		model.addAttribute("mainData",data);
		return "admin/company/csearchResult";
	}
	

	// 기업 관리 페이지 ( 기업 리스트 )
	@RequestMapping("/cmanagement")
	String cmanagement(MCompanyDTO dto, Model mm,HttpServletRequest request) {
		
		return "admin/company/cmanagement";
	}
	
	// 기업 리스트
	@RequestMapping("list/{page}")
	String list(Model mm, MCompanyDTO dto) {
		
		dto.calc(adminMapper.adminAddCont());
	
		List<MCompanyDTO> data = adminMapper.companyList(dto);
		System.out.println(adminMapper.companyList(dto));
		mm.addAttribute("mainData",data);
		mm.addAttribute("realData",adminMapper.companyCapprovalList(dto));
		return "/admin/company/cList";
	}// 기업 리스트
	
	@RequestMapping("delete/{cno}")
	String delete(MCompanyDTO dto, Model mm,@PathVariable int cno ,HttpServletRequest request) {
		MCompanyDTO rcdto = adminMapper.adminCDetail(cno);
		int cnt = adminMapper.deleteCompany(cno);
		if (cnt>0) {
			fileDeleteModule(rcdto, request);
		}
		
		return "redirect:/admin_company/list/1";
	}
	
	// 파일삭제
	void fileDeleteModule(MCompanyDTO delDTO, HttpServletRequest request) {
		if(delDTO.getCcompanyFile()!=null) {

			new File(path+"\\"+delDTO.getCcompanyFile()).delete();
		}
	}// 파일삭제
	
	// 채용 디테일
		@RequestMapping("detail/{page}/{cno}")
		String detail(Model mm, 
			@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			@PathVariable int cno) {
			// 조회수증가
			System.out.println(cno+"나다 cno");
			MCompanyDTO dto = adminMapper.adminCDetail(cno);
			dto.setPage(page);
			dto.calc(adminMapper.adminAddCont());
			System.out.println(dto);
			mm.addAttribute("dto",dto);
		 
			return "/admin/company/cDetail";
		}// 채용 디테일
		
		// 파일 다운로드
		@RequestMapping("/companyup/download/{filename}")
		void download(@PathVariable String filename,
					   HttpServletRequest request,
					   HttpServletResponse response) {
			System.out.println("이벤트체크=> 파일 다운로드");
			try {
				System.out.println(filename+", "+"파일님 오셧나요");
				String path = request.getServletContext().getRealPath("companyup");
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
		
		@RequestMapping("checkId/{cno}")
		String checkId(@PathVariable int cno,MCompanyDTO dto) {
			//adminMapper.checkoutFile(cno);
			System.out.println(adminMapper.checkoutFile(dto.getCno())+"오셧나요");
			return "redirect:/admin_company/list/1";
		}

}
