# 결제
# 결제아이디, 개인회원아이디, 기업회원아이디, 아임포트아이디, 주문번호, 
# 상품명, 주문자명, 주문자연락처, 
# 결제상태, 결제액, 결제시간
# 가상계좌결제시: 가상계좌은행이름, 가상계좌계좌번호, 가상계좌입금주
CREATE TABLE payment(
    pay_id	INT AUTO_INCREMENT PRIMARY KEY,
    sid		VARCHAR(100),
    cid		VARCHAR(100),
    imp_uid	VARCHAR(100),
    merchant_uid	VARCHAR(100),
    pname	VARCHAR(100),
    buyer_name	VARCHAR(100),
    buyer_tel	VARCHAR(100),
    pstatus	VARCHAR(100),
    paid_amount	INT,
    paid_at		Date,
    vbank_name	VARCHAR(100),
    vbank_num	VARCHAR(100),
    vbank_holder	VARCHAR(100)
);

select * from payment;

drop table payment;