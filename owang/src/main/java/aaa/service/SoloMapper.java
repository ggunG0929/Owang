package aaa.service;

import org.apache.ibatis.annotations.Mapper;


import aaa.model.SoloDTO;

@Mapper
public interface SoloMapper {

	// 회원 입력
	void insertSolo(SoloDTO solo);

	// 로그인 비교
	int loginSolo(String userid, String userpw);

	// 아이디확인
	int idChk(String sid);

	// 개인 정보 상세 수정 삭제
	SoloDTO detailSolo(String sid);
	int delettt(SoloDTO dto);
	int modifffy(SoloDTO dto);
	
	// 세션받아오기 객체
	SoloDTO resumeSolo(String userid);
	
	// 결제회원처리
	void paysmember(SoloDTO dto);
}
