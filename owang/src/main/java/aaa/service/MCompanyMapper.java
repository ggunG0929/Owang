package aaa.service;



import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import aaa.model.ApplicantDTO;
import aaa.model.MCompanyDTO;
import aaa.model.RecruitDTO;



@Mapper
public interface MCompanyMapper {

		//기업입력
		void insertCompany(MCompanyDTO company);
		//기업 로그인
		int loginCompany(String userid, String userpw);
		MCompanyDTO deatilaaaCompany(String cid);
		//기업 아이디체크
		int idChk(String cid);
		// 기업타입 가져오기
		Integer getctype(String cid); 
		
		// 게시글 상세보기
		
		//기업 정보 상세 수정 삭제 
		MCompanyDTO deatilCompany(String cid);
		int modifffy(MCompanyDTO dto); 
		int delettt(MCompanyDTO company);
		int fileDelete(MCompanyDTO dto);
		// 결제회원처리
		void paycmember(MCompanyDTO dto);
		// 로그인회원처리
		void logincmember(MCompanyDTO dto);
		
		
		// 아이디확인
		int findloCompany(String fname, String femail);	
		MCompanyDTO findid(String fname, String femail);	
		
		// 비번찾기
		int findpwComp(String fid, String femail, String fceo);
		int modifycpw(String cid, String cpw); 
				
		
		// 재훈 추가
		// 지원자 리스트 불러오기
		List<ApplicantDTO> applist(ApplicantDTO adto);
		
		List<ApplicantDTO> apppasslist(ApplicantDTO adto);
		
		List<ApplicantDTO> appnonpasslist(ApplicantDTO adto);
		
		// 지원서 디테일
		ApplicantDTO appdetail(int ano, String cid);
		
		// 지원자 열람여부
		int appread(int ano);
		
		// 지원자 합격
		int apppass(int ano);
		
		// 지원자 불합격
		int appnonpass(int ano);
}

