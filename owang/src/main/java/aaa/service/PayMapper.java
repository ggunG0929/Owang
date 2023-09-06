package aaa.service;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import aaa.model.PaymentDTO;

@Mapper
public interface PayMapper {

	// 리스트보기
	List<PaymentDTO> list();
	PaymentDTO detail(String impUid);
	// 결제생성
	int insert(PaymentDTO dto);
	// 상품삭제
	int delete(PaymentDTO dto);
	// 상품수정
	int modify(PaymentDTO dto);
}
