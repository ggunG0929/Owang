package aaa.model;

import java.util.List;

import lombok.Data;

// imp_uid로 가져온 결제내역 조회
@Data
public class PaymentResponseMember {
    private int code;
    private String message;
    private List<Payment> response;

    @Data
    public static class Payment {
    	// 아임포트고유번호
        private String imp_uid;
        // 주문번호
        private String merchant_uid;
        // 결제수단
        private String pay_method;
        // 결제채널(폰,컴)
        private String channel;
        // 결제pg사
        private String pg_provider;
        private String emb_pg_provider;
        private String pg_tid;
        private String pg_id;
        // 에스크로 여부
        private boolean escrow;
        // 카드 승인번호
        private String apply_num;
        // 은행
        private String bank_code;
        private String bank_name;
        // 카드
        private String card_code;
        private String card_name;
        private int card_quota;
        private String card_number;
        private int card_type;
        // 가상계좌
        private String vbank_code;
        private String vbank_name;
        private String vbank_num;
        private String vbank_holder;
        private int vbank_date;
        private int vbank_issued_at;
        // 상품명
        private String name;
        // 상품가격
        private int amount;
        // 취소가격
        private int cancel_amount;
        // 통화(한국원화)
        private String currency;
        // 주문자
        private String buyer_name;
        private String buyer_email;
        private String buyer_tel;
        private String buyer_addr;
        private String buyer_postcode;
        // 옵션정보
        private String custom_data;
        private String user_agent;
        // 결제상태
        private String status;
        // 결제시작 시간
        private int started_at;
        // 결제완료 시간
        private String paid_at;
        // 결제실패 시간
        private int failed_at;
        // 결제취소 시간
        private int cancelled_at;
        // 실패이유
        private String fail_reason;
        // 취소이유
        private String cancel_reason;
        // 매출전표 링크
        private String receipt_url;
        private List<CancelHistory> cancel_history;
        private List<String> cancel_receipt_urls;
        private boolean cash_receipt_issued;
        private String customer_uid;
        private String customer_uid_usage;

        public static class CancelHistory {
            private String pg_tid;
            private int amount;
            private int cancelled_at;
            private String reason;
            private String receipt_url;
        }
    }
}
