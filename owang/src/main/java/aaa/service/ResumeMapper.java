package aaa.service;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import aaa.model.ResumeDTO;

@Mapper
public interface ResumeMapper {
	
	// 이력서 리스트
	List<ResumeDTO> resumelist();
	
	// 이력서 디테일
	ResumeDTO resumedetail(int rsid);

	// 이력서 글쓰기
	int resumeinsert(ResumeDTO rdto);	
	
	// 이력서 총 개수
	int resumecnt();
	
	// 이력서 삭제
	int resumedelete(int rsid);
}
