package aaa.service;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import aaa.model.RecruitDTO;
import aaa.model.SoloDTO;

@Mapper
public interface AdminSolo {

	// 게시글 정렬
	List<SoloDTO> soloList(SoloDTO dto);

	// 게시글 페이지 계산
	int soloListCnt();

	// 게시글 정렬 재직인증x
	List<SoloDTO> solocomList(SoloDTO dto);

	// 재직 승인
	int checkoutFile(int sno);
	
	// 게시글 페이지 계산
	int solocomListCnt();
	//서치
	List<SoloDTO> searchRecruit(@Param("keyword") String keyword, @Param("searchOption") String searchOption);
}
