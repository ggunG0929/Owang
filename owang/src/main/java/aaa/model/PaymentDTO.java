package aaa.model;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("payDTO")
public class PaymentDTO {
	// 아임포트 거래고유번호
	String impUid;
	// 자체생성한 주문번호
	String merchantUid;
	// 제품명
	String pname;
	// 결제금액
	int paidAmount;
	// 결제자명
	String buyerName;
	// 결제자폰번호
	String buyerTel;
	// 결제상태
	String pstatus;
	// 결제시간
	Date paidAt;
	// (가상계좌)은행이름
	String vbankName;
	// (가상계좌)계좌번호
	String vbankNum;
	// (가상계좌)입금주
	String vbankHolder;
	
}
