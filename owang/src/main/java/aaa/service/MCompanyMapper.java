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
		// 기업 가져오기
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
				
		
		// 지원자 리스트 불러오기
		List<ApplicantDTO> applist(ApplicantDTO adto);
		
		// 지원서 디테일
		ApplicantDTO appdetail(int ano, String cid);
		
		int appstate(int ano);
}

