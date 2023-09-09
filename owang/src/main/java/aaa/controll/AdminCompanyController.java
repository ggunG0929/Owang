package aaa.controll;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import aaa.model.MCompanyDTO;
import aaa.model.PageData;
import aaa.model.RecruitDTO;
import aaa.service.AdminCompanyMapper;

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
		return "/admin/company/cList";
	}// 기업 리스트
	
	// 채용 디테일
		@RequestMapping("detail/{page}/{cno}")
		String detail(Model mm, @PathVariable int page, @PathVariable int cno) {
			// 조회수증가
			System.out.println(cno+"나다 cno");
			MCompanyDTO dto = adminMapper.adminCDetail(cno);
			dto.setPage(page);
			dto.calc(adminMapper.adminAddCont());
   
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
