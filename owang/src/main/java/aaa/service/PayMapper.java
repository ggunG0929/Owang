package aaa.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import aaa.model.PaymentDTO;

@Mapper
public interface PayMapper {

	// 리스트보기
	List<PaymentDTO> list();
	// 결제내역보기
	PaymentDTO detail(String impUid);
	// 결제내역생성
	int insert(PaymentDTO dto);
//	// 결제내역삭제
//	int delete(PaymentDTO dto);
//	// 결제내역수정
//	int modify(PaymentDTO dto);
	// 아임포트고유번호로 db에서 아이디 가져오기
	String idget(String impUid);
	// sid로 impuid리스트
    List<String> impuidbys(String sid);
	// cid로 impuid리스트
    List<String> impuidbyc(String cid);
    
    // 일별 매출액
    List<Map<String, Object>> dailytotal();
    // 아이디별 매출액
    List<Map<String, Object>> totalbys();
    List<Map<String, Object>> totalbyc();
}
