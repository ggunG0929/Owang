# cno 무결성번호
# cid 기업아이디
# cpw 기업 비번
# cname 기업이름
# cbuild 설립연도
# ccategory 업종alter
# cstaff 직원수
# csales 1년매출액
# ccall 회사 전화번호
# cceo 회사 대표 이름
# caddress 회사 주소
# cconmanyfile 사업자등록파일
# ctype 타입 ( 1번 기본, 2번유료, 3번 블랙)
# cemail 이메일

CREATE TABLE company (
    cno int  NOT NULL AUTO_INCREMENT primary key,
   cid varchar(100) ,
    cpw VARCHAR(100),
    cname VARCHAR(100),
    cemail VARCHAR(100),
    cbuild VARCHAR(100),
    ccategory varchar(100),
    cstaff int,
    csales int,
    ccall varchar(100),
    cceo varchar(100),
    caddress varchar(400),
    ccompanyFile  VARCHAR(100),
    ccontent varchar(500),
    ctype Int default 1,
    cdate date,
    cbcnt int,
    capproval boolean default false 
    
);
ALTER TABLE company
MODIFY COLUMN capproval BOOLEAN;

UPDATE `company` SET `ctype` = '2', `capproval` = 1 WHERE (cno = 29);

INSERT INTO company (cid, cpw, cname, cemail, cbuild, ccategory, cstaff, csales, ccall, cceo, caddress, ccompanyFile, ccontent, ctype, cdate, cbcnt, capproval)
VALUES
    ('company1', 'password1', '한식땡땡', 'han@test.com', '1995-08-23', '한식회사', 100, 1000, '010-1234-5678', '김철수', '서울시 강남구 역삼로 1-1', '한식땡땡.jpg', '한식땡땡은 한식 회사로 고객들에게 다양한 한식 메뉴를 제공합니다. 우리 회사는 한국 전통의 맛을 지향합니다.', 1, '2024-08-23', 5, true),
    ('company2', 'password2', '고기마루', 'gogi@test.com', '2002-03-10', '한식회사', 50, 500, '010-9876-5432', '이영희', '서울시 마포구 망원로 2-2', '고기마루.jpg', '고기마루는 한식 회사로 신선한 고기 요리를 제공합니다. 고기의 진미를 경험하세요.', 2, '2024-09-10', 3, true),
    ('company3', 'password3', '불고기눈물', 'bulgogi@test.com', '2010-12-05', '한식회사', 200, 2000, '010-5555-1234', '홍길동', '부산시 해운대구 해운로 3-3', '불고기눈물.jpg', '불고기눈물은 한식 회사로 다양한 불고기 요리를 제공합니다. 불 맛을 느껴보세요.', 1, null, 7, false),
    ('company4', 'password4', '갈비향연', 'galbi@test.com', '2015-02-25', '한식회사', 75, 7500, '010-1111-2222', '이지훈', '대구시 수성구 수성로 4-4', '갈비향연.jpg', '갈비향연은 한식 회사로 고객들에게 다양한 갈비 요리를 제공합니다. 고기의 신선함을 느껴보세요.', 2, '2024-03-15', 2, false),
    ('company5', 'password5', '삼겹살낙원', 'samgyeopsal@test.com', '2017-08-17', '한식회사', 30, 300, '010-6666-7777', '김현아', '서울시 중구 명동로 5-5', '삼겹살낙원.jpg', '삼겹살낙원은 한식 회사로 고객들에게 맛있는 삼겹살을 제공합니다. 고기의 풍미를 느껴보세요.', 1, '2024-02-20', 6, true),
    ('company6', 'password6', '소고기군국', 'sogogi@test.com', '2018-03-01', '한식회사', 45, 450, '010-9999-0000', '김미영', '서울시 종로구 종로 6-6', '소고기군국.jpg', '소고기군국은 한식 회사로 다양한 소고기 요리를 제공합니다. 고기의 풍미를 느끼세요.', 1, '2024-03-20', 4, false),
    ('company7', 'password7', '김치반점', 'kimchi@test.com', '2019-05-20', '한식회사', 60, 600, '010-7777-8888', '이동철', '대전시 중구 중앙로 7-7', '김치반점.jpg', '김치반점은 한식 회사로 고객들에게 집밥을 제공합니다. 집에서 느끼는 맛을 경험하세요.', 2, null, 8, true),
    ('company8', 'password8', '비빔밥코너', 'bibimbap@test.com', '2020-07-10', '한식회사', 25, 250, '010-8888-9999', '김지영', '부산시 사하구 사하로 8-8', '비빔밥코너.jpg', '비빔밥코너는 한식 회사로 다양한 비빔밥을 제공합니다. 간편하게 즐기세요.', 1, '2023-05-05', 1, false),
    ('company9', 'password9', '김밥행복', 'kimbap@test.com', '2021-10-05', '한식회사', 35, 350, '010-6666-5555', '박지수', '인천시 남구 인천로 9-9', '김밥행복.jpg', '김밥행복은 한식 회사로 다양한 김밥을 제공합니다. 가볍게 즐기세요.', 2, '2023-06-15', 9, false),
    ('company10', 'password10', '떡볶이나라', 'tteokbokki@test.com', '2022-01-02', '한식회사', 80, 800, '010-1234-4321', '김민우', '광주시 광산구 광산로 10-10', '떡볶이나라.jpg', '떡볶이나라는 한식 회사로 매콤한 떡볶이를 제공합니다. 한입에 행복을 느껴보세요.', 1, '2023-09-05', 3, false),
    ('company11', 'password11', '국밥프라자', 'gukbap@test.com', '2020-02-15', '한식회사', 55, 550, '010-9876-1234', '홍길순', '대전시 대덕구 대덕로 11-11', '국밥프라자.jpg', '국밥프라자는 한식 회사로 다양한 국밥을 제공합니다. 국밥의 맛을 느껴보세요.', 2, '2023-08-12', 6, true),
    ('company12', 'password12', '닭갈비단지', 'dakgalbi@test.com', '2020-04-20', '한식회사', 40, 400, '010-5555-6666', '김민재', '울산시 남구 용연로 12-12', '닭갈비단지.jpg', '닭갈비단지는 한식 회사로 고객들에게 다양한 닭갈비를 제공합니다. 닭고기의 맛을 느껴보세요.', 1, '2023-09-04', 2, true),
    ('company13', 'password13', '한식나라', 'hansik@test.com', '2010-05-15', '한식음식점', 40, 400, '010-1234-5670', '김민수', '서울시 강남구 강남로 1-1', '한식나라.jpg', '한식나라는 고객들에게 맛있는 한식을 제공합니다. 전통의 맛을 경험하세요.', 1, null, 5, true),
    ('company14', 'password14', '집밥유선생', 'jibbab@test.com', '2005-12-20', '한식음식점', 16, 300, '010-9876-5430', '오미숙', '서울시 마포구 서강로 2-2', '백반가든.jpg', '백반가든은 고객들에게 집밥을 제공합니다. 집에서 느끼는 풍미를 즐기세요.', 2, '2022-08-20', 3, true),
    ('company15', 'password15', '전통한식마을', 'jeontong@test.com', '2013-04-05', '한식음식점', 15, 250, '010-5555-1230', '안재훈', '부산시 해운대구 해운로 3-3', '전통한식마을.jpg', '전통한식마을은 고객들에게 전통적인 한식을 제공합니다. 한국의 맛을 느껴보세요.', 1, '2022-10-15', 7, true),
    ('company16', 'password16', '한식맛집', 'hansikmat@test.com', '2016-02-10', '한식음식점', 9, 500, '010-1111-2220', '유성길', '대구시 수성구 수성로 4-4', '한식맛집.jpg', '한식맛집은 고객들에게 다양한 한식을 제공합니다. 한식의 풍미를 즐기세요.', 2, '2023-05-05', 2, false),
    ('company17', 'password17', '고기마루', 'gogimaru@test.com', '2018-07-25', '한식음식점', 8, 450, '010-6116-7770', '노승우', '서울시 중구 명동로 5-5', '고기마루.jpg', '고기마루은 고객들에게 다양한 고기 요리를 제공합니다. 고기의 풍미를 느끼세요.', 1, '2023-02-20', 6, true),
    ('company18', 'password18', '고기앞으로', 'gogiaturo@test.com', '2018-07-25', '한식음식점', 7, 450, '010-6636-7770', '김홍철', '서울시 강동구 성내로 25-5', '고기앞으로.jpg', '고기앞으로은 고객들에게 다양한 고기 요리를 제공합니다. 고기의 풍미를 느끼세요.', 1, null, 6, false),
    ('company19', 'password19', '비벼비벼비빕', 'bibimbab@test.com', '2018-07-25', '한식음식점', 15, 750, '010-6611-7770', '김명자', '서울시 강남구 인사동 1-3', '비벼비벼비밥.jpg', '비벼비벼비밥은 고객들에게 다양한 비빔요리를 제공합니다. 비빔밥의 다양한 풍미를 느끼세요.', 1,null, 6, true),
    ('company20', 'password20', '음악과 비와 전', 'eumgwa@test.com', '2018-07-25', '한식음식점', 6, 450, '010-6466-7770', '홍박사', '서울시 중랑구 명량로 54-5', '음악과비와전.jpg', '음악과비와전은 고객들에게 다양한 고기 요리를 제공합니다.  풍미를 느끼세요.', 1, null, 6, true);


 update company set capproval = true and ctype = 2 where cno = 29;
select * from company;
select * from company where cid = "company4" and cpw = "password4";
select * from company where capproval = 1;
   update solo set
   ccompanyFile = ""
   where cid = "aaa" and cpw='aaa';
drop table company;

select * from company;
select * from company where cid = "company4" and cpw = "password4";
select * from company capproval where capproval = true;
select * from company where capproval = false;
update company set capproval = true where cno = 5;
drop table company;