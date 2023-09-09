package aaa.service;



import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import aaa.model.ReviewDTO;




@Mapper
public interface ReviewMapper {

	// 기업 회원 정리
	List<ReviewDTO> reviewList(ReviewDTO dto);
	
	// 기업 회원 페이지 계산
	int reviewCnt(String cid);
	
	
}
