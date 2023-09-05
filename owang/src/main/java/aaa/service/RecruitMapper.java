package aaa.service;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import aaa.model.RecruitDTO;

@Mapper
public interface RecruitMapper {
	
	// 지원서 리스트
	List<RecruitDTO> recruitlist();
}
