#솔로키입니다.
-- user 계정 
show databases;  /* db 보기 */


use owang; /* db 사용 */
show tables;  /* 테이블 보기 */

-- db 생성
create database owang;

-- db 권한주기
grant all privileges on owang.* to root@localhost with grant option;

show databases;
CREATE TABLE solo (
   sno int  NOT NULL AUTO_INCREMENT primary key,
    sid varchar(20) ,
    cid varchar(30),
    spw VARCHAR(20),
    sname VARCHAR(30),
    sbirth VARCHAR(30),
    sage int,
    sgender varchar(10),
    sphone  VARCHAR(30),
    semail  VARCHAR(100),
    saddress  VARCHAR(100),
    scompanyName  VARCHAR(100),
    scompanyFile  VARCHAR(100),
    stype int,
    sdate date,
    sbcnt int,
    sinjueng boolean default false
);

use solo;

INSERT INTO solo (sid, spw, sname, sbirth, sage, sgender, sphone, saddress, semail, 
   cid, scompanyName, scompanyFile, stype, sdate, sbcnt, sinjueng) VALUES 
   ('user1', 'pass1', '박시우', '1990-01-15', 32, '남', '010-1234-5678', '서울시 강남구', 'user1@gmail.com', 'company3', '불고기눈물', 'file3.txt', 1, '2023-05-21', 1, false),
   ('user2', 'pass2', '김현호', '1985-04-22', 37, '여', '010-9876-5432', '경기도 분당구', 'user2@gmail.com', 'company20', '음악과 비와 전', 'file20.txt', 2, '2023-07-24', 1, true),
   ('user3', 'pass3', '이진영', '1998-08-05', 24, '남', '010-5555-5555', '부산시 해운대구', 'user3@naver.com', 'company8', '비빔밥코너', 'file8.txt', 2, '2023-09-12', 1, true),
   ('user4', 'pass4', '박지민', '1976-11-30', 45, '여', '010-1234-5678', '서울시 종로구', 'user4@naver.com', 'company20', '음악과 비와 전', 'file20.txt', 1, '2023-03-19', 1, false),
   ('user5', 'pass5', '송민', '2000-03-18', 22, '남', '010-9876-5432', '인천시 남동구', 'user5@gmail.com', 'company17', '고기마루', 'file17.txt', 1, '2023-05-17', 1, true),
   ('user6', 'pass6', '최영진', '1993-07-10', 29, '여', '010-5555-5555', '대구시 수성구', 'user6@naver.com', 'company11', '국밥프라자', 'file11.txt', 2, '2023-08-02', 1, false),
   ('user7', 'pass7', '강민서', '1989-09-25', 33, '남', '010-1234-5678', '광주시 동구', 'user7@naver.com', 'company8', '비빔밥코너', 'file8.txt', 1, '2023-01-01', 1, true),
   ('user8', 'pass8', '이지음', '1995-12-03', 26, '여', '010-9876-5432', '대전시 중구', 'user8@naver.com', 'company20', '음악과 비와 전', 'file20.txt', 2, '2023-02-07', 1, false),
   ('user9', 'pass9', '오예준', '1982-02-14', 40, '남', '010-5555-5555', '울산시 남구', 'user9@gmail.com', 'company4', '갈비향연', 'file4.txt', 1, '2023-05-02', 1, true),
   ('user10', 'pass10', '김영신', '1978-06-20', 43, '여', '010-1234-5678', '세종시 조치원', 'user10@naver.com', 'company11', '국밥프라자', 'file11.txt', 1, '2022-09-01', 1, true);

select * from solo;

drop table solo;