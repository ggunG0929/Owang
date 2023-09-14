package aaa.controll;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.mysql.cj.Session;

import aaa.model.PageData;
import aaa.model.PaymentResponseMember;
import aaa.model.SoloDTO;
import aaa.service.PayMapper;
import aaa.service.PayService;
import aaa.service.SoloMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("solo")
public class SoloController {

	@Resource
	SoloMapper sssmapper;

	// 회원관리

	// 개인정보상세보기
	@RequestMapping("solo_info")
	String detail(Model mm, HttpSession session, PageData pd) {
		String sid = (String) session.getAttribute("sid");
		
		pd.setMsg("로그인해야 이용가능한 페이지입니다");
		pd.setGoUrl("/login/main");

		if (sid != null) { // 세선에서 값을 받으면
			SoloDTO dto = sssmapper.detailSolo(sid);
			System.out.println(dto);
			mm.addAttribute("dto", dto);

			return "solo_info/detail";

		} else {

			return "join/join_alert";
		}
	}

	// 개인정보수정

	@GetMapping("modify")
	String modify(Model mm, HttpSession session) {
		String sid = (String) session.getAttribute("sid");
		mm.addAttribute("dto", sssmapper.detailSolo(sid));

		return "solo_info/modify";
	}

	@PostMapping("modify")
	String modifyReg(SoloDTO dto, PageData pd, HttpServletRequest request) {
	  
	    pd.setMsg("수정실패");
	    pd.setGoUrl("/solo/modify");

		if(dto.getScompanyFile()==null) {	//파일 없을때만 파일저장
			
		fileSavesolo(dto, request);			
		}
		
	    int cnt = sssmapper.modifffy(dto); // 메서드 안의 값이 들어와서 cnt 값이 1이 됩니다.
	    
	    if (cnt > 0) {
	        pd.setMsg("수정되었습니다.");
	        pd.setGoUrl("/solo/solo_info");
	    }

	    return "join/join_alert";
	}

	// 개인정보삭제

	@GetMapping("delete")
	String delete( HttpSession session, Model mm) {
		
		  String sid = (String) session.getAttribute("sid"); mm.addAttribute("sid",
		  sid);
		 
		return "solo_info/delete";
	}

	@PostMapping("delete")
	String deleteReg(SoloDTO dto, PageData pd,HttpSession session) {

		pd.setMsg("삭제실패");
		pd.setGoUrl("/solo/delete");

		int cnt = sssmapper.delettt(dto); // 메서드안의 값이 들어와서 cnt 값이 1이됌
		System.out.println("deleteReg:" + cnt);
		if (cnt > 0) {
			pd.setMsg("삭제되었습니다.");
			pd.setGoUrl("/");
			session.invalidate();
		}

		return "join/join_alert";
	}
	//파일수정삭제
		@PostMapping("fileDelete")
		   String fileDelete(SoloDTO dto,PageData pd,  HttpServletRequest request) {
		      
		      SoloDTO delDTO = sssmapper.detailSolo(dto.getSid());
		      pd.setMsg("파일 삭제실패");
		      pd.setGoUrl("/solo/modify");
		      
		      int cnt = sssmapper.fileDelete(dto);
		      System.out.println("modifyReg:"+cnt);
		      if(cnt>0) {
		         fileDeleteModule(delDTO, request);
		         pd.setMsg("파일 삭제되었습니다.");
		      }

		      return "join/join_alert";
		   }

	
	void fileSavesolo(SoloDTO dto, HttpServletRequest request) {
	// 파일 업로드 유무 확인
		if (dto.getMmff() == null || dto.getMmff().isEmpty()) {
	        return;
	    }

			String path = request.getServletContext().getRealPath("soloup");
			//path = "C:\\Final_Team\\owang\\src\\main\\webapp\\member\\soloup";

			int dot = dto.getMmff().getOriginalFilename().lastIndexOf(".");
			String fDomain = dto.getMmff().getOriginalFilename().substring(0, dot);
			String ext = dto.getMmff().getOriginalFilename().substring(dot);

			dto.setScompanyFile(fDomain + ext);
			File ff = new File(path + "\\" + dto.getScompanyFile());
			int cnt = 1;
			while (ff.exists()) {

				dto.setScompanyFile(fDomain + "_" + cnt + ext);
				ff = new File(path + "\\" + dto.getScompanyFile());
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
		//파일지우기
		void fileDeleteModule(SoloDTO delDTO, HttpServletRequest request) {
		      if(delDTO.getScompanyFile()!=null) {
		         String path = request.getServletContext().getRealPath("soloup");
					/*
					 * path = "E:\\BackEnd_hakwon\\N_SpringWorks\\exboard2\\src\\main\\webapp\\up";
					 */
		         
		         new File(path+"\\"+delDTO.getScompanyFile()).delete();
		      }
		   }
		
		// 상품 결제내역
		@Resource
		PayMapper paym;
		@Autowired
		PayService payS;
		
		@RequestMapping("/product")
		String product(Model mm, HttpSession session) throws Exception {
			// 세션에서 id 가져옴
			SoloDTO solosession = (SoloDTO) session.getAttribute("solosession");
			String sid = solosession.getSid();
			System.out.println(sid);
			// sdate가 오늘 이후인 경우 - 유효상품이 있는 경우
			Date today = new Date();
			Date sdate = solosession.getSdate();
	        if(sdate!=null && sdate.after(today)) {
	        	mm.addAttribute("date", sdate);
	        }
			
			// 아이디로 db의 impuid로 리스트를 만들어 가져오고, 서버에 보내 결제내역을 가져옴
			List<String> impuidList = paym.impuids(sid);
			if(!impuidList.isEmpty())  {				
				List<PaymentResponseMember.Payment> paymentData = payS.getPaymentData(impuidList);
				mm.addAttribute("paymentData", paymentData);
			}
			return "product/payment";
		}
}
