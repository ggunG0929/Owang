package aaa.service;



import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import aaa.model.MCompanyDTO;




@Mapper
public interface AdminCompanyMapper {

	// 기업 회원 정리
	List<MCompanyDTO> companyList(MCompanyDTO dto);
	
	// 기업 회원 페이지 계산
	int adminAddCont();
	
	// 기업 회원 상세보기
	MCompanyDTO adminCDetail(int cno);

	// 기업 체크
	int checkoutFile(int cno);
	
}
