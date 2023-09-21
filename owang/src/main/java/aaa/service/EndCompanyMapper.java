package aaa.service;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import aaa.model.MCompanyDTO;
import aaa.model.SoloDTO;


@Mapper
public interface EndCompanyMapper {
	// 삭제한 회원 삽입
	int endCompanyInsert(MCompanyDTO dto);
	
	// 아이디 검사
	int idChk(String cid);
	
	// 기업탈퇴회원리스트
	List<MCompanyDTO> endCompanyList();
	
	// 기업 탈퇴 회원 
	int endCompanycnt();
}
