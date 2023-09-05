package aaa.service;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import aaa.model.ProductDTO;

@Mapper
public interface ProductMapper {

	// 리스트보기
	List<ProductDTO> list();
	// 상품생성
	int insert(ProductDTO dto);
	// 상품삭제
	int delete(ProductDTO dto);
	// 상품수정
	int modify(ProductDTO dto);
}
