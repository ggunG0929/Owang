package aaa.service;



import org.apache.ibatis.annotations.Mapper;



import aaa.model.MCompanyDTO;
import aaa.model.RecruitDTO;



@Mapper
public interface MCompanyMapper {

		//기업입력
		void insertCompany(MCompanyDTO company);
		//기업 로그인
		int loginCompany(String userid, String userpw);
		//기업 아이디체크
		int idChk(String cid);
		// 기업 가져오기
		// 게시글 상세보기
		MCompanyDTO deatilCompany(String userid);
		
		
		
		/*
		 * //기업 정보 상세 수정 삭제 MCompanyDTO detail(String cid); int modifffy(MCompanyDTO
		 * company); int delettt(MCompanyDTO company);
		 */
		
}
