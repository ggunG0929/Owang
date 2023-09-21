# 이력서 지원 게시판
select * from applicant;

#삭제
drop table applicant;

CREATE TABLE applicant (
    ano INT AUTO_INCREMENT PRIMARY KEY,
    rsid VARCHAR(100),
    sid VARCHAR(100),
    cid VARCHAR(100),
    sname VARCHAR(100),
    sbirth VARCHAR(100),
    sage INT,
    sgender VARCHAR(100),
    sphone VARCHAR(100),
    semail VARCHAR(100),
    saddress VARCHAR(100),
    scompanyName VARCHAR(100),
    scompanyFile VARCHAR(100),
    cname VARCHAR(100),
    recruit_id VARCHAR(100),
    recruit_title VARCHAR(100),
    apphoto VARCHAR(100),
    aptitle VARCHAR(100),
    apcareer VARCHAR(100),
    apcompany VARCHAR(100),
    apworkstart VARCHAR(100),
    apworkend VARCHAR(100),
    apwork VARCHAR(100),
    apacademic VARCHAR(100),
    apacademicstat VARCHAR(100),
    apintroduce VARCHAR(100),
    aplicense VARCHAR(100),
    aplicenseorg VARCHAR(100),
    aplicenseyear VARCHAR(100),
    aplanguage VARCHAR(100),
    aplanguagelevel VARCHAR(100),
    apsubmitdate TIMESTAMP,
    apcancel INT DEFAULT 0,
    apread INT DEFAULT 0,
    appass INT DEFAULT 0,
    appnum INT DEFAULT 0,
    CONSTRAINT app_duplication UNIQUE (sname, cname, recruit_title)
);



# 본죽회사의 지원자 더미
INSERT INTO applicant (rsid, sid, cid, sname, sbirth, sage, sgender, sphone, semail, saddress, scompanyName, scompanyFile, cname, recruit_id, recruit_title, apphoto, aptitle, apcareer, apcompany, apworkstart, apworkend, apwork, apacademic, apacademicstat, apintroduce, aplicense, aplicenseorg, aplicenseyear, aplanguage, aplanguagelevel, apsubmitdate)
VALUES 
(1, 'review', 'bonjuck', '강본죽', '2001-02-03', '23', '남성', '010-1234-5678', 'kbj@gmail.com', '서울시 강남구', '새마을식당', '재직증명서.hwp', '본죽', '22', '[본죽] 함께할 따뜻한 인성의 직원을 모집합니다.', NULL, '[요리에 미치다] 강본죽입니다.', '신입', NULL, NULL, NULL, NULL, '대학(4년)', '졸업', '안녕하십니까\n 끊임없는 도전을 통해 요리의 미래를 만들고자 하는 요리사입니다. 함께 일하고 싶습니다. 감사합니다.', '한식조리사', '한국산업인력공단', '2022-01-03', '영어', '회화 능숙', '2023-09-17 16:11:19'),
(2, 'review1', 'bonjuck', '마동석', '2001-02-03', '23', '남성', '010-1234-5678', 'kbj@gmail.com', '서울시 강남구', '새마을식당', '재직증명서.hwp', '본죽', '22', '[본죽] 함께할 따뜻한 인성의 직원을 모집합니다.', NULL, '[요리에 미치다] 마동석입니다.', '신입', NULL, NULL, NULL, NULL, '대학(4년)', '졸업', '안녕하세요\n 끊임없는 도전을 통해 요리의 미래를 만들고자 하는 요리사입니다. 함께 일하고 싶습니다. 감사합니다.', '한식조리사', '한국산업인력공단', '2022-01-03', '영어', '회화 능숙', '2023-09-17 16:11:19'),
(3, 'review2', 'bonjuck', '이민지', '2001-02-03', '23', '여성', '010-1234-5678', 'kbj@gmail.com', '서울시 강남구', '새마을식당', '재직증명서.hwp', '본죽', '22', '[본죽] 함께할 따뜻한 인성의 직원을 모집합니다.', NULL, '[요리에 미치다] 이민지입니다.', '신입', NULL, NULL, NULL, NULL, '대학(4년)', '졸업', '[성실하며 끊임없는 도전하는 요리사입니다.] ', '한식조리사', '한국산업인력공단', '2022-01-03', '영어', '회화 능숙', '2023-09-17 16:11:19');

# 중복제한 조건
ALTER TABLE applicant
ADD CONSTRAINT app_duplication UNIQUE (sname, cname, recruit_title);

ALTER TABLE applicant
ADD appnum INT DEFAULT 0;
