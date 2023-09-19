package aaa.service;

import org.apache.ibatis.annotations.Mapper;

import aaa.model.MCompanyDTO;


@Mapper
public interface EndCompanyMapper {
	// 삭제한 회원 삽입
	int endCompanyInsert(MCompanyDTO dto);
	
	// 아이디 검사
	int idChk(String cid);
}
