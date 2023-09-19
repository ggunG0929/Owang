package aaa.service;

import org.apache.ibatis.annotations.Mapper;


import aaa.model.SoloDTO;

@Mapper
public interface EndSoloMapper {
	// 삭제한 회원 삽입
	int endSoloInsert(SoloDTO dto);
	
	// 아이디 검사
	int idChk(String sid);
}
