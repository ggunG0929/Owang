package aaa.service;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import aaa.model.RecruitDTO;

@Mapper
public interface RecruitMapper {
	// 게시글 정렬
	List<RecruitDTO> recruitList(RecruitDTO dto);
	
	// 게시글 페이지 계산
	int recruitListCnt();
	
	// 조회수 증가
	int recruitAddCont(int recruitId);
	
	// 게시글 상세보기
	RecruitDTO recruitDetail(int recruitId);
	
	// 게시글 추가
	int recruitInsert(RecruitDTO dto);
	
	// 게시글 추가할때 id 맨위로 올리기
	int recruitMaxId();
	
	// 게시글 삭제
	int recruitDelete(int id);
	
	// 게시글 수정
	int recruitModify(RecruitDTO dto);

	// 달력 데이ㅣ터 삽입 테스트
	List<RecruitDTO> recruitTest();
	
	// 검색기능 테스트
	List<RecruitDTO> searchRecruit(@Param("keyword") String keyword, @Param("searchOption") String searchOption);

	
	
}
