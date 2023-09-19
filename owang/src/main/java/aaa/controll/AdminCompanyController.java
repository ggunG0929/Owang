package aaa.controll;

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
import aaa.service.AdminCompanyMapper;
import aaa.service.MCompanyMapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin_company")
public class AdminCompanyController {

	@Resource
	AdminCompanyMapper adminMapper;

	@Resource
	MCompanyMapper crccMapper;

	static String path = "E:\\BackEnd_hakwon\\Spring_Team\\owang\\src\\main\\webapp\\companyup";

	@GetMapping("/search")
	String search(@RequestParam("keyword") String keyword, @RequestParam("searchOption") String searchOption,
			@ModelAttribute MCompanyDTO dto,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page, Model model) {
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
		model.addAttribute("mainData", data);
		return "admin/company/csearchResult";
	}

	// 기업 관리 페이지 ( 기업 리스트 )
	@RequestMapping("/cmanagement")
	String cmanagement(MCompanyDTO dto, Model mm, HttpServletRequest request) {

		return "admin/company/cmanagement";
	}

	// 기업 리스트
	@RequestMapping("list/{page}")
	String list(Model mm, MCompanyDTO dto) {

		dto.calc(adminMapper.adminAddCont());

		List<MCompanyDTO> data = adminMapper.companyList(dto);
		System.out.println(adminMapper.companyList(dto));
		mm.addAttribute("mainData", data);
		mm.addAttribute("realData", adminMapper.companyCapprovalList(dto));
		return "/admin/company/cList";
	}// 기업 리스트

	@RequestMapping("cRegList/{page}")
	String cRegList(Model mm, MCompanyDTO dto, @PathVariable int page) {
		dto.setPage(page);
		dto.calc(adminMapper.adminAddCont());
		System.out.println(dto);
		List<MCompanyDTO> data = adminMapper.companyCapprovalList(dto);
		mm.addAttribute("realData", data);
		return "/admin/company/cRegList";
	}

	// 미인증
	@RequestMapping("cMiList/{page}")
	String cMiList(Model mm, MCompanyDTO dto) {

		dto.calc(adminMapper.adminAddMiCont());
		List<MCompanyDTO> data = adminMapper.companyList(dto);
		mm.addAttribute("mainData", data);

		return "/admin/company/cMiList";
	}

	// 기업정보수정

	@GetMapping("modify/{cno}")
	String modify(Model mm, HttpSession session, @PathVariable int cno) {
		MCompanyDTO rcdto = adminMapper.adminCDetail(cno);

		mm.addAttribute("dto", crccMapper.deatilCompany(rcdto.getCid()));

		return "/admin/company/modify";
	}

	@PostMapping("modify/{cno}")
	String modifyReg(MCompanyDTO dto, @PathVariable int cno, PageData pd, HttpServletRequest request,
			HttpSession session) {
		pd.setMsg("수정실패");
		pd.setGoUrl("/admin_company/modify" + dto.getCno());

		if (dto.getCcompanyFile() == null) {
			System.out.println("기존파일없음");
			fileSavecompany(dto, request);
		}
		System.out.println(dto);
		int cnt = crccMapper.adminmodifffy(dto); // 메서드안의 값이 들어와서 cnt 값이 1이됌

		if (cnt > 0) {
			pd.setMsg("수정되었습니다.");
			pd.setGoUrl("/admin_company/detail/1/" + dto.getCno());
		}

		return "join/join_alert";
	}

	@RequestMapping("delete/{cno}")
	String delete(MCompanyDTO dto, Model mm, @PathVariable int cno, HttpServletRequest request) {
		MCompanyDTO rcdto = adminMapper.adminCDetail(cno);
		int cnt = adminMapper.deleteCompany(cno);
		if (cnt > 0) {
			fileDeleteModule(rcdto, request);
		}

		return "redirect:/admin_company/list/1";
	}

	

	// 관리자 기업 디테일
	@RequestMapping("detail/{page}/{cno}")
	String detail(Model mm, @RequestParam(value = "page", required = false, defaultValue = "1") int page,
			@PathVariable int cno) {
		// 조회수증가
		System.out.println(cno + "나다 cno");
		MCompanyDTO dto = adminMapper.adminCDetail(cno);
		dto.setPage(page);
		dto.calc(adminMapper.adminAddCont());

		mm.addAttribute("dto", dto);
		System.out.println(dto + "디테일");
		return "/admin/company/cDetail";
	}

	// 파일 다운로드
	@RequestMapping("/companyup/download/{filename}")
	void download(@PathVariable String filename, HttpServletRequest request, HttpServletResponse response) {
		System.out.println("이벤트체크=> 파일 다운로드");
		try {
			System.out.println(filename + ", " + "파일님 오셧나요");
			String path = request.getServletContext().getRealPath("companyup");
			FileInputStream fis = new FileInputStream(path + "\\" + filename);
			// 인코딩 설정
			String encFName = URLEncoder.encode(filename, "utf-8");

			response.setHeader("Content-Disposition", "attachment; filename=" + encFName);

			ServletOutputStream sos = response.getOutputStream();

			byte[] buf = new byte[1024];
			while (fis.available() > 0) {
				int len = fis.read(buf); // 읽은후 buf에 저장
				// len = 넣은 길이
				sos.write(buf, 0, len);// buf 0부터 len 만큼

			}

			sos.close();
			fis.close();

		} catch (Exception e) {

			e.printStackTrace();
		}

	}// 파일 다운로드

	// 수락여기
	@RequestMapping("checkId/{cno}")
	String checkId(@PathVariable int cno, MCompanyDTO dto, HttpSession session) {
		MCompanyDTO mcseDto = (MCompanyDTO) session.getAttribute("companysession");

		if (mcseDto != null) {
			System.out.println("오셧나요?");
			System.out.println("mcseDto : " + mcseDto);
			mcseDto.setCapproval(true);
			session.setAttribute("companysession", mcseDto);
		}

		System.out.println(adminMapper.checkoutFile(dto.getCno()) + "왔나요");
		adminMapper.checkoutFile(dto.getCno());
		return "redirect:/admin_company/list/1";
	}

	// 사업자등록증파일저장
	void fileSavecompany(MCompanyDTO cto, HttpServletRequest request) {

		// 파일 업로드 유무 확인
		if (cto.getMmff() == null || cto.getMmff().isEmpty()) {
			return;
		}

		String path = request.getServletContext().getRealPath("companyup");
		// path = "C:\\Final_Team\\owang\\src\\main\\webapp\\member\\companyup";

		int dot = cto.getMmff().getOriginalFilename().lastIndexOf(".");
		String fDomain = cto.getMmff().getOriginalFilename().substring(0, dot);
		String ext = cto.getMmff().getOriginalFilename().substring(dot);

		cto.setCcompanyFile(fDomain + ext);
		File ff = new File(path + "\\" + cto.getCcompanyFile());
		int cnt = 1;
		while (ff.exists()) {

			cto.setCcompanyFile(fDomain + "_" + cnt + ext);
			ff = new File(path + "\\" + cto.getCcompanyFile());
			cnt++;
		}

		try {
			FileOutputStream fos = new FileOutputStream(ff);

			fos.write(cto.getMmff().getBytes());

			fos.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// 파일삭제
		void fileDeleteModule(MCompanyDTO delDTO, HttpServletRequest request) {
			if (delDTO.getCcompanyFile() != null) {

				new File(path + "\\" + delDTO.getCcompanyFile()).delete();
			}
		}// 파일삭제
	

	// 파일수정삭제
	@PostMapping("fileDelete/{cno}")
	String fileDelete(@PathVariable int cno, PageData pd, HttpServletRequest request) {

		MCompanyDTO dto = adminMapper.adminCDetail(cno);

		pd.setMsg("파일 삭제실패");
		pd.setGoUrl("/admin_company/modify/" + dto.getCno());
		System.out.println("삭제하기전dto"+dto);
		int cnt = crccMapper.fileDelete(dto);
		System.out.println("modifyReg:" + cnt);
		if (cnt > 0) {
			fileDeleteModule(dto, request);
			pd.setMsg("파일 삭제되었습니다.");
		}

		return "join/join_alert";
	}
}
