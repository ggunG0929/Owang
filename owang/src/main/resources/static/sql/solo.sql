# 개인회원
select * from solo;

CREATE TABLE solo (
	sno	int NOT NULL AUTO_INCREMENT primary key,
    sid varchar(20),
    cid varchar(30),
    spw VARCHAR(20),
    sname	VARCHAR(30),
    sbirth	VARCHAR(30),
    sage	int,
    sgender	varchar(10),
    sphone  VARCHAR(30),
    semail  VARCHAR(100),
    saddress	VARCHAR(100),
    scompanyName	VARCHAR(100),
    scompanyFile	VARCHAR(100),
    stype	int,
    sdate	date,
    sbcnt	int,
    sinjueng	boolean default false,
    sjoindate	date
);

drop table solo;

INSERT INTO solo (sid, spw, sname, sbirth, sage, sgender, sphone, saddress, semail, 
   cid, scompanyName, scompanyFile, stype, sdate, sbcnt, sinjueng, sjoindate) VALUES 
   ('user1', 'pass1', '박시우', '1990-01-15', 32, '남성', '010-1234-5678', '서울시 강남구', 'user1@gmail.com', 'company3', '불고기눈물', 'file1.jpg', 1, '2022-09-01', 1, true, '2021-08-01'),
   ('user2', 'pass2', '김현호', '1985-04-22', 37, '여성', '010-9876-5432', '경기도 분당구', 'user2@gmail.com', 'company20', '음악과 비와 전', 'file1.jpg', 2, '2023-02-07', 1, false, '2022-06-24'),
   ('user3', 'pass3', '이진영', '1998-08-05', 24, '남성', '010-5555-5555', '부산시 해운대구', 'user3@naver.com', 'company8', '비빔밥코너', 'file1.jpg', 2, '2023-03-19', 1, false, '2022-07-25'),
   ('user4', 'pass4', '박지민', '1976-11-30', 45, '여성', '010-1234-5678', '서울시 종로구', 'user4@naver.com', 'company20', '음악과 비와 전', 'file1.jpg', 1, '2023-01-01', 1, true, '2022-08-29'),
   ('user5', 'pass5', '송민', '2000-03-18', 22, '남성', '010-9876-5432', '인천시 남동구', 'user5@gmail.com', 'company17', '고기마루', 'file1.jpg', 1, '2023-08-02', 1, false, '2023-01-22'),
   ('user6', 'pass6', '최영진', '1993-07-10', 29, '여성', '010-5555-5555', '대구시 수성구', 'user6@naver.com', 'company11', '국밥프라자', 'file1.jpg', 2, '2023-05-17', 1, true, '2023-01-30'),
   ('user7', 'pass7', '강민서', '1989-09-25', 33, '남성', '010-1234-5678', '광주시 동구', 'user7@naver.com', 'company8', '비빔밥코너', 'file1.jpg', 1, '2023-05-02', 1, true, '2023-03-24'),
   ('user8', 'pass8', '이지음', '1995-12-03', 26, '여성', '010-9876-5432', '대전시 중구', 'user8@naver.com', 'company20', '음악과 비와 전', 'file1.jpg', 2, '2023-05-21', 1, false, '2023-04-24'),
   ('user9', 'pass9', '오예준', '1982-02-14', 40, '남성', '010-5555-5555', '울산시 남구', 'user9@gmail.com', 'company4', '갈비향연', 'file1.jpg', 1, '2023-07-24', 1, true, '2023-07-17'),
   ('user10', 'pass10', '김영신', '1978-06-20', 43, '여성', '010-1234-5678', '세종시 조치원', 'user10@naver.com', 'company11', '국밥프라자', 'file1.jpg', 1, '2023-09-12', 1, true, '2023-07-24'),
# 더미데이터
   ('resume', '1111', '백종원', '2001-02-03', 23, '남성', '010-1234-5678', '서울시 강남구', 'bjw@gmail.com', null, null, null, 1, '2023-05-21', 1, true, '2023-03-30'),
   ('review', '1111', '강본죽', '2001-02-03', 23, '남성', '010-1234-5678', '서울시 강남구', 'kbj@gmail.com', 'bonjuck', '본죽', '재직증명서.hwp', 2, '2023-05-21', 2, true, '2023-03-30'),
   ('review1', '1111', '마동석', '2001-02-03', 23, '남성', '010-1234-5678', '서울시 강남구', 'kbj@gmail.com', 'bonjuck', '본죽', 'kbj.pdf', 1, '2023-05-21', 1, true, '2023-03-30'),
   ('review2', '1111', '이민지', '2001-02-03', 23, '여성', '010-1234-5678', '서울시 강남구', 'kbj@gmail.com', 'bonjuck', '본죽', 'kbj.pdf', 1, '2023-05-21', 1, true, '2023-03-30'),
   ('review3', '1111', '곽두팔', '2001-02-03', 23, '여성', '010-1234-5678', '서울시 강남구', 'kbj@gmail.com', 'bonjuck', '본죽', 'kbj.pdf', 1, '2023-05-21', 1, true, '2023-03-30'),
   ('user11', '1111', '리뷰어', '2001-02-03', 23, '여성', '010-1234-5678', '서울시 강남구', 'kbj@gmail.com', 'bonjuck', '본죽', 'kbj.pdf', 1, '2023-05-21', 1, true, '2023-03-30'),
   ('user12', '1111', '천진영', '2001-02-03', 23, '여성', '010-1234-5678', '세종시 조치원', 'kbj@gmail.com', 'bonjuck', '본죽', 'kbj.pdf', 1, '2023-05-21', 1, true, '2023-03-30'),
   ('saereview1', '1111', '염승진', '2001-02-03', 23, '여성', '010-1234-5678', '경기도 의정부시', 'kbj@gmail.com', 'saema', '새마을식당', 'kbj.pdf', 1, '2023-05-21', 1, true, '2023-03-30'),
   ('saereview2', '1111', '황연두', '2001-02-03', 23, '남성', '010-1234-5678', '서울시 강남구', 'kbj@gmail.com', 'saema', '새마을식당', 'kbj.pdf', 1, '2023-05-21', 1, true, '2023-03-30'),
   ('saereview3', '1111', '최주영', '2001-02-03', 23, '여성', '010-1234-5678', '서울시 관악구', 'kbj@gmail.com', 'saema', '새마을식당', 'kbj.pdf', 1, '2023-05-21', 1, true, '2023-03-30');
	