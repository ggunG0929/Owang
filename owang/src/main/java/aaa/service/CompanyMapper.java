package aaa.service;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import aaa.model.CompanyDTO;

@Mapper
public interface CompanyMapper {
	
	List<CompanyDTO> mypage(CompanyDTO cdto);
	
	CompanyDTO detail(int cno);
	
	int modifffy(CompanyDTO cdto);
	
	int mypageCnt();
	
	int maxId();
	
	void addCount(int cid);
	
	int idPwChk(CompanyDTO dto);
	
}
