package aaa.controll;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import aaa.model.RecruitDTO;
import aaa.model.SoloDTO;
import aaa.model.SoloResumeDTO;
import aaa.service.RecruitMapper;
import aaa.service.SoloResumeMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/recruit")
public class RecruitController {
	
	// path 정의 다같이 쓰는거라 위에 빼둠 받으시고 테스트할때 폴더 경로 바꾸시면되여!!
	// 승우꺼
	static String path = "E:\\BackEnd_hakwon\\Spring_Team\\owang\\src\\main\\webapp\\up";
	
	// 은별이
	//static String path = "C:\\Users\\콩쥐\\Desktop\\final\\Final_Team\\owang\\src\\main\\webapp\\up";
	
	@Resource
	RecruitMapper recruitMapper;
	
	// 지원하기때문에 넣었음
	@Resource
	SoloResumeMapper rsmapper;
	
	@GetMapping("/search")
	public String search(@RequestParam("keyword") String keyword, 
			@RequestParam("searchOption") String searchOption,
			@ModelAttribute RecruitDTO dto,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			Model model) {
		
		    List<RecruitDTO> data = recruitMapper.searchRecruit(keyword, searchOption);
		    
		    // 페이징 정보 설정
		    dto.setPage(page); // 페이지 설정
		    dto.calc(data.size()); // 페이지 계산 메서드 호출
		    
		    // 해당 페이지에 필요한 데이터만 가져오기
		    int startIndex = dto.getStart();
		    int endIndex = startIndex + dto.getLimit();
		    if (endIndex > data.size()) {
		        endIndex = data.size();
		    }
		    List<RecruitDTO> paginatedData = data.subList(startIndex, endIndex);
		
		
		try {
		
			model.addAttribute("mainData", paginatedData);
			
		} catch (Exception e) {
			model.addAttribute("mainData", "검색결과가 없습니다");
		}

		return "recruit/searchResult";
	}

	
	
	@RequestMapping("calendar")
	String calendar() {

		return "recruit/recruit_calendar";
	}

	@ResponseBody
	@PostMapping("calendar2")
	Object recruitTest() {

		ArrayList<LinkedHashMap<String, String>> list = new ArrayList<>();
		for (RecruitDTO recruitDTO : recruitMapper.recruitTest()) {
			LinkedHashMap<String, String> map=new LinkedHashMap<>();
			map.put("id",recruitDTO.getRecruitId()+"");
			map.put("title", "시작    "+recruitDTO.getRecruitTitle()+"  "+recruitDTO.getRegDate());
			map.put("start", recruitDTO.getRegDate());
			
			list.add(map);
			
		}
		
		return list;
	}
	@ResponseBody
	@PostMapping("calendar3")
	Object recruitTest3() {
		
		ArrayList<LinkedHashMap<String, String>> list = new ArrayList<>();
		for (RecruitDTO recruitDTO : recruitMapper.recruitTest()) {
			LinkedHashMap<String, String> map=new LinkedHashMap<>();
			map.put("id",recruitDTO.getRecruitId()+"");
			map.put("title", "마감    "+recruitDTO.getRecruitTitle()+"  "+recruitDTO.getRealMagam());
			map.put("start", recruitDTO.getRealMagam());
			
			list.add(map);
			
		}
		
		return list;
	}
	
	@ResponseBody
	@PostMapping("calendar23")
	public List<LinkedHashMap<String, String>> recruitTest2And3() {
	    List<LinkedHashMap<String, String>> list = new ArrayList<>();
	    
	    // calendar2 데이터 가져오기
	    for (RecruitDTO recruitDTO : recruitMapper.recruitTest()) {
	        LinkedHashMap<String, String> map = new LinkedHashMap<>();
	        map.put("id", recruitDTO.getRecruitId() + "");
	        map.put("title", "시작    " + recruitDTO.getRecruitTitle() + "  " + recruitDTO.getRegDate());
	        map.put("start", recruitDTO.getRegDate());

	        list.add(map);
	    }
	    
	    // calendar3 데이터 가져오기
	    for (RecruitDTO recruitDTO : recruitMapper.recruitTest()) {
	        LinkedHashMap<String, String> map = new LinkedHashMap<>();
	        map.put("id", recruitDTO.getRecruitId() + "");
	        map.put("title", "마감    " + recruitDTO.getRecruitTitle() + "  " + recruitDTO.getRealMagam());
	        map.put("start", recruitDTO.getRealMagam());

	        list.add(map);
	    }

	    return list;
	}

	// 채용 리스트
	@RequestMapping("list/{page}")
	String list(Model mm, RecruitDTO dto) {
		

		System.out.println(recruitMapper.recruitList(dto));
		dto.calc(recruitMapper.recruitListCnt());
		
		/*
		int daysToAdd = dto.getRecruitMagam(); // 고른 마감일
		
		System.out.println(daysToAdd + "오셧 나요 나의 마감일");
		
		
		Calendar currentDate = Calendar.getInstance();// 날짜 정보 선언
		currentDate.setTime(new Date());//오늘날짜
		currentDate.add(Calendar.DATE, daysToAdd);// 마감일 뒤로 더해달라는 설정
		*/
		//System.out.println("마감일: " + currentDate.getTime().toString()); // 
		List<RecruitDTO> data = recruitMapper.recruitList(dto);
		mm.addAttribute("mainData",data);
		return "recruit/recruit_list";
	}// 채용 리스트
	
	// 채용 디테일
	@RequestMapping("detail/{page}/{id}")
	String detail(Model mm, @PathVariable int page, @PathVariable int id) {
		// 조회수증가
		recruitMapper.recruitAddCont(id);
		
		RecruitDTO dto = recruitMapper.recruitDetail(id);
		dto.setPage(page);
		dto.calc(recruitMapper.recruitListCnt());
		LocalDate currentDate = LocalDate.now();
	    LocalDate futureDate = currentDate.plusDays(dto.getRecruitMagam()); 

	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd (E)");
	    String formattedDate = futureDate.format(formatter);
	    
		mm.addAttribute("dtro",dto);
	    mm.addAttribute("magam", formattedDate);
		return "/recruit/recruit_detail";
	}// 채용 디테일
	
	
	// 채용 디테일 새롭게 작성!
	@RequestMapping("detail/{cid}/{page}/{id}")
	String cdetail(Model mm, @PathVariable int page, @PathVariable int id) {
		// 조회수증가
		recruitMapper.recruitAddCont(id);
		
		RecruitDTO dto = recruitMapper.recruitDetail(id);
		dto.setPage(page);
		dto.calc(recruitMapper.recruitListCnt());
		LocalDate currentDate = LocalDate.now();
		LocalDate futureDate = currentDate.plusDays(dto.getRecruitMagam()); 
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd (E)");
		String formattedDate = futureDate.format(formatter);
		
		mm.addAttribute("dto",dto);
		mm.addAttribute("magam", formattedDate);
		return "/recruit/recruit_detail";
	}// 채용 디테일
	
	
	
	// 채용 작성 페이지
	@GetMapping("insert/{page}")
	String insert(RecruitDTO dto) {
		
		return "recruit/recruit_insert";
	}// 채용 작성 페이지
	
	// 채용 삽입
	@PostMapping("insert/{page}")
	String insertReg(RecruitDTO dto,HttpServletRequest request) {
		
		dto.setRecruitId(recruitMapper.recruitMaxId()+1);
		System.out.println(dto);
		fileSave(dto, request);
		recruitMapper.recruitInsert(dto);
		System.out.println(dto);
		dto.setMsg("작성되었습니다.");
		dto.setGoUrl("/recruit/list/1");
		return "recruit/recruit_alert";
	}// 채용 삽입
	
	// 파일 저장
	void fileSave(RecruitDTO dto, HttpServletRequest request) {

		// 파일 업로드 유무 확인
		if (dto.getMmff().isEmpty()) {
			return;
		}

		int dot = dto.getMmff().getOriginalFilename().lastIndexOf(".");
		String fDomain = dto.getMmff().getOriginalFilename().substring(0, dot);
		String ext = dto.getMmff().getOriginalFilename().substring(dot);

		dto.setRecruitUpfile(fDomain + ext);
		File ff = new File(path + "\\" + dto.getRecruitUpfile());
		int cnt = 1;
		while (ff.exists()) {

			dto.setRecruitUpfile(fDomain + "_" + cnt + ext);
			ff = new File(path + "\\" + dto.getRecruitUpfile());
			cnt++;
		}

		try {
			FileOutputStream fos = new FileOutputStream(ff);

			fos.write(dto.getMmff().getBytes());

			fos.close();
		} catch (Exception e) {
			
			e.printStackTrace();
		}

	}// 파일 저장
	
	// 파일삭제
	void fileDeleteModule(RecruitDTO delDTO, HttpServletRequest request) {
		if(delDTO.getRecruitUpfile()!=null) {

			new File(path+"\\"+delDTO.getRecruitUpfile()).delete();
		}
	}// 파일삭제
	
	@RequestMapping("delete/{page}/{id}")
	String delete(RecruitDTO dto, HttpServletRequest request ,@PathVariable int id) {
		// 삭제전 세션이나 기업의 아이디 비번이 맞냐 로직을 넣어야할듯합니다.
		// 혹은 conform이엿나로 도 가능(alert인데 삭제할지 취소할지 묻는거입니다)
		// 일단 삭제이벤트 생성
		RecruitDTO rdto = recruitMapper.recruitDetail(id);
		System.out.println(dto);
		dto.setMsg("삭제실패");
		dto.setGoUrl("/recruit/detail/"+dto.getPage()+"/"+id);
		
		// 일단 dto가안와서 page에있는 id로 죽임 form으로 넘긴게안이라서 올방법을 일단 못찾음
		int cnt = recruitMapper.recruitDelete(id);
		System.out.println("deleteTest" + cnt);
		
		if (cnt>0) {
			fileDeleteModule(rdto, request);
			System.out.println();
			System.out.println(rdto);
			dto.setMsg("삭제되었습니다.");
			dto.setGoUrl("/recruit/list/1");
		}
		
		
		return "recruit/recruit_alert";
	}
	
	//채용 수정페이지  
		@GetMapping("modify/{page}/{id}")
		String modify(Model mm, @PathVariable int page, @PathVariable int id) {
			
			RecruitDTO dto = recruitMapper.recruitDetail(id); //mapper라는 객체의 recruitDetail메서드 호출


															//수정하려는 상세 정보 가져오기위해 detail메서드 사용 - 여기서 detail은 mapper.xml의 detail

			mm.addAttribute("dto",dto);
			
			
			return "recruit/recruit_modifyform";
		}
		
		@PostMapping("modify/{page}/{id}")
		String modifyReg(RecruitDTO dto, HttpServletRequest request) {
			
			dto.setMsg("수정 실패입니다.");
		    dto.setGoUrl("recruit/modify" + dto.getPage() + "/" + dto.getRecruitId());

		    // 수정된 데이터를 데이터베이스에 저장
		    int cnt = recruitMapper.recruitModify(dto);
		    System.out.println("cnt"+cnt);
		    
		    if (cnt > 0) {
		        dto.setMsg("수정되었습니다.");
		        dto.setGoUrl("/recruit/detail/" + dto.getPage() + "/" + dto.getRecruitId());
		    }
			
			
			return "recruit/recruit_alert"; 
		}
	
		// 지원서 제출 페이지
		@RequestMapping("submit/{page}/{rsid}")
		String submit(Model mm, SoloResumeDTO rdto, @PathVariable int page, 
				@PathVariable int rsid, HttpSession session) {
			String sid = (String)session.getAttribute("sid");
			SoloDTO solosession = (SoloDTO) session.getAttribute("solosession");
			List<SoloResumeDTO> data = rsmapper.resumelist(sid);
			mm.addAttribute("mainData", data);

			// 일단 이력서 가져옴
			//List<SoloResumeDTO> data = rsmapper.resumelist(rdto.rsid);
			//mm.addAttribute("mainData", rsmapper.resumedetail(rdto.rsid));
			//mm.addAttribute("mainData", data);
			//System.out.println(data + "data왔니?");
			return "recruit/recruit_submitform";
		}
		
		
		// 지원서 제출 완료 (SoloResumeDTO를 가져와서 applicant에 넣는다)
		// 1. SoloResumeDTO를 가져온다
		// 2. applicant에 삽입 메서드 넣음
		@RequestMapping("submit/{page}/{id}/{rsid}")
		String submitReg(RecruitDTO dto, HttpServletRequest request,  @PathVariable int rsid
				,HttpSession session) {
			// SoloResumeDTO 한개 불러옴
			//String sid = (String)session.getAttribute("sid");
			//SoloDTO solosession = (SoloDTO) session.getAttribute("solosession");
			dto.setMsg("접수가 완료되었습니다.");
		    dto.setGoUrl("recruit/list");
			
		    return "recruit/recruit_alert";
		}
		

		// 지원서 제출 시 이력서 열람
		@RequestMapping("submit/{rsid}")
		String submitResume(Model mm, SoloResumeDTO rdto, HttpSession session, @PathVariable int rsid) {
			
			SoloDTO solosession = (SoloDTO) session.getAttribute("solosession");
			SoloResumeDTO sdto = rsmapper.resumedetail(rdto.rsid, solosession.sid);

			mm.addAttribute("sdto", sdto);
			mm.addAttribute("solosession", solosession);
			System.out.println(sdto);
			System.out.println(solosession);
			System.out.println(solosession.sid);
			// 이력서 열람만 가능
			return "solo_resume/recruit_resume";
		}
}

