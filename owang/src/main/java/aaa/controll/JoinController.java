package aaa.controll;

import java.io.File;
import java.io.FileOutputStream;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import aaa.model.MCompanyDTO;
import aaa.model.PageData;
import aaa.model.SoloDTO;

import aaa.service.MCompanyMapper;
import aaa.service.SoloMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/join")

public class JoinController {
	
	@Resource
	SoloMapper smapper;
	@Resource
	MCompanyMapper cmapper;
	
	//개인회원가입
	@GetMapping("solo")
	
	String joinsoloForm(SoloDTO solo
		
			) {

		return "join/join_solo";
	}

	@PostMapping("solo")
	
	String joinsoloReg(	SoloDTO solo , PageData pd,HttpServletRequest request
			) {
		fileSavesolo(solo,request);
		
		pd.setMsg("개인회원가입이 완료되었습니다.");
		pd.setGoUrl("/join/join_ok");
		smapper.insertSolo(solo);	//sql에 개인정보입력
		return "join/join_alert";
	}
	//기업고객
	@GetMapping("company")
	String joincompanyForm(MCompanyDTO company) {
		
		
		return "join/join_company";
	}
	
	@PostMapping("company")
	String joincompanyReg(MCompanyDTO company, PageData pd,HttpServletRequest request) {
		fileSavecompany(company,request);
		pd.setMsg("기업회원가입이 완료되었습니다.");
		pd.setGoUrl("/join/join_ok");
		cmapper.insertCompany(company);	//sql에 개인정보입력
		return "join/join_alert";
	}
	
	@RequestMapping("/join_ok")
	String joinmain() {
		
		return "join/join_ok";
	}
	void fileSavesolo(SoloDTO dto, HttpServletRequest request) {
	      
	      //파일 업로드 유무 확인
	      if(dto.getMmff().isEmpty()) {
	         
	         return;
	      }
	      
	      String path = request.getServletContext().getRealPath("soloup");
	      path = "C:\\Final_Team\\owang\\src\\main\\webapp\\member\\soloup";
	      
	      
	      int dot = dto.getMmff().getOriginalFilename().lastIndexOf(".");
	      String fDomain = dto.getMmff().getOriginalFilename().substring(0, dot);
	      String ext = dto.getMmff().getOriginalFilename().substring(dot);
	      
	      
	      
	      dto.setScompanyFile(fDomain+ext); 
	      File ff = new File(path+"\\"+dto.getScompanyFile());
	      int cnt = 1;
	      while(ff.exists()) {
	          
	         dto.setScompanyFile(fDomain+"_"+cnt+ext);
	         ff = new File(path+"\\"+dto.getScompanyFile());
	         cnt++;
	      }
	      
	      try {
	         FileOutputStream fos = new FileOutputStream(ff);
	         
	         fos.write(dto.getMmff().getBytes());
	         
	         fos.close();
	      } catch (Exception e) {
	         // TODO Auto-generated catch block
	         e.printStackTrace();
	      }
	      
	   }
	//사업자등록증파일저장
	void fileSavecompany(MCompanyDTO cto, HttpServletRequest request) {
		
		//파일 업로드 유무 확인
		if(cto.getMmff().isEmpty()) {
			
			return;
		}
		
		String path = request.getServletContext().getRealPath("companyup");
		path = "C:\\Final_Team\\owang\\src\\main\\webapp\\member\\companyup";
		
		
		int dot = cto.getMmff().getOriginalFilename().lastIndexOf(".");
		String fDomain = cto.getMmff().getOriginalFilename().substring(0, dot);
		String ext = cto.getMmff().getOriginalFilename().substring(dot);
		
		
		
		cto.setCcompanyFile(fDomain+ext); 
		File ff = new File(path+"\\"+cto.getCcompanyFile());
		int cnt = 1;
		while(ff.exists()) {
			
			cto.setCcompanyFile(fDomain+"_"+cnt+ext);
			ff = new File(path+"\\"+cto.getCcompanyFile());
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
	
}
