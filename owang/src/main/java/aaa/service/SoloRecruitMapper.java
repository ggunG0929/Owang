package aaa.service;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import aaa.model.SoloRecruitDTO;

@Mapper
public interface SoloRecruitMapper {
	
	// 지원서 리스트
	List<SoloRecruitDTO> recruitlist();
}
