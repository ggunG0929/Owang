package aaa.service;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import aaa.model.CompanyDTO;

@Mapper
public interface CompanyMapper {
	
	List<CompanyDTO> mypage(CompanyDTO dto);
	
	CompanyDTO detail(int id);
	
}
