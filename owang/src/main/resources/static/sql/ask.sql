create table ask(
id int auto_increment primary key,
title varchar(100),
pname varchar(100),
pw varchar(100),
upfile varchar(100),
content varchar(9999),
reg_date timestamp,
cnt int,
seq int,
lev int,
gid int
);

SELECT * 
FROM ask 
WHERE gid IN (SELECT gid FROM ask WHERE pname = "구직자")
ORDER BY gid DESC, seq
LIMIT 0, 5;

SELECT * 
FROM ask
WHERE gid IN(SELECT gid FROM ask WHERE pname = "구직자");

SELECT * 
FROM (
  SELECT * 
  FROM ask
  WHERE gid IN (SELECT gid FROM ask WHERE pname = "구직자")
  UNION
  SELECT *
  FROM ask
  WHERE pname = "관리자"
) AS combined_result
ORDER BY gid DESC, seq 
LIMIT 0,10;

SELECT * 
FROM ask
WHERE gid IN(SELECT gid FROM ask WHERE pname = "관리자");


SELECT gid FROM ask WHERE pname = "구직자";

select * from ask;
drop table ask;

insert into ask (id, title, pname, pw, content, reg_date, cnt, seq, lev, gid)
values
(1,'문의드립니다.','콩쥐','1111','콩쥐네 한정식 채용공고 어딨나요','2023-04-01',0,0,0,1),
(2,'문의드립니다','팥쥐','1111','팥쥐네 한정식 채용공고 어딨나요?','2023-04-01',0,0,0,2),
(3,'문의드립니다','두꺼비','1111','두꺼비 한정식 채용공고 어딨나요?','2023-04-01',0,0,0,3),
(4,'문의드립니다','심청이','1111','심청이 한정식 채용 0명은 몇명 뽑는다는건가요?','2023-04-01',0,0,0,4),
(5,'문의드립니다','심봉사','1111','심봉사 지원했는데 저한테 연락이 안오는데요','2023-04-01',0,0,0,5),
(6, '안녕하세요 오광관리자님!', '구직자', '1111', '사람인 사이트를 통해 토끼네 한정식의 신규 채용 공고를 확인하려고 합니다. 어떻게 찾을 수 있나요?', '2023-04-01', 0, 0, 0, 6),
(7, '안녕하세요 오광관리자님!', '구직자', '1111', '사슴네 한정식의 최신 채용 정보를 찾고 있습니다. 어떻게 검색해야 할까요?', '2023-04-02', 0, 0, 0, 7),
(8, '안녕하세요 오광관리자님!', '구직자', '1111', '호랑이네 한정식의 채용 공고를 확인하려고 합니다. 검색하는 방법을 가르쳐 주세요.', '2023-04-03', 0, 0, 0, 8),
(9, '안녕하세요 오광관리자님!', '구직자', '1111', '곰네 한정식의 신규 채용 정보를 찾고 있습니다. 어디서 검색해야 할까요?', '2023-04-04', 0, 0, 0, 9),
(10, '안녕하세요 오광관리자님!', '구직자', '1111', '원숭이네 한정식의 채용 공고를 확인하려면 어떻게 해야 하나요? 도움 부탁드립니다.', '2023-04-05', 0, 0, 0, 10),
(11, '안녕하세요 오광관리자님!', '구직자', '1111', '기린네 한정식의 신규 채용 정보를 검색하려고 합니다. 어떻게 해야 할까요?', '2023-04-06', 0, 0, 0, 11),
(12, '안녕하세요 오광관리자님!', '구직자', '1111', '하마네 한정식의 채용 공고를 찾고 있습니다. 검색하는 방법을 알려주세요.', '2023-04-07', 0, 0, 0, 12),
(13, '안녕하세요 오광관리자님!', '구직자', '1111', '코끼리네 한정식의 신규 채용 정보를 어떻게 검색할 수 있을까요?', '2023-04-08', 0, 0, 0, 13),
(14, '안녕하세요 오광관리자님!', '구직자', '1111', '오리네 한정식의 최신 채용 공고를 확인하려면 어떻게 해야 하나요? 도움 부탁드립니다.', '2023-04-09', 0, 0, 0, 14),
(15, '안녕하세요 오광관리자님!', '구직자', '1111', '거북이네 한정식의 채용 정보를 찾고 있습니다. 검색하는 방법을 가르쳐 주세요.', '2023-04-10', 0, 0, 0, 15),
(16, '신고합니다', '사용자1', '1111', '사이트에서 부적절한 게시물이 계속해서 등장합니다. 조치 부탁드립니다.', '2023-05-01', 0, 0, 0, 16),
(17, '신고합니다', '사용자2', '1111', '스팸 댓글이 많이 올라옵니다. 대응 부탁드립니다.', '2023-05-02', 0, 0, 0, 17),
(18, '신고합니다', '사용자3', '1111', '사용자 간의 불쾌한 토론이 계속되고 있습니다. 조치 바랍니다.', '2023-05-03', 0, 0, 0, 18),
(19, '신고합니다', '사용자4', '1111', '비방글이 과도하게 등장하고 있습니다. 즉시 조치해주세요.', '2023-05-04', 0, 0, 0, 19),
(20, '신고합니다', '사용자5', '1111', '개인정보 유출이 의심됩니다. 빠른 확인 부탁드립니다.', '2023-05-05', 0, 0, 0, 20),
(21, '신고합니다', '사용자6', '1111', '불법 광고가 게시되고 있습니다. 광고 게시글을 삭제해주세요.', '2023-05-06', 0, 0, 0, 21),
(22, '신고합니다', '사용자7', '1111', '욕설과 모욕적인 내용이 계속해서 등장합니다. 대응 필요합니다.', '2023-05-07', 0, 0, 0, 22),
(23, '신고합니다', '사용자8', '1111', '불법 콘텐츠가 포함된 게시물이 있습니다. 빠른 조치를 부탁드립니다.', '2023-05-08', 0, 0, 0, 23),
(24, '신고합니다', '사용자9', '1111', '스팸 계정이 여러 개 생성되어 있습니다. 대응 부탁드립니다.', '2023-05-09', 0, 0, 0, 24),
(25, '신고합니다', '사용자10', '1111', '사용자 사이에서의 갈등이 심각해졌습니다. 중재 부탁드립니다.', '2023-05-10', 0, 0, 0, 25),
(26, '신고합니다', '사용자11', '1111', '도박과 관련된 내용이 게시되어 있습니다. 즉시 삭제해주세요.', '2023-05-11', 0, 0, 0, 26),
(27, '신고합니다', '사용자12', '1111', '허위 정보가 유포되고 있습니다. 조치를 취해주세요.', '2023-05-12', 0, 0, 0, 27),
(28, '신고합니다', '사용자13', '1111', '욕설 및 혐오 표현이 포함된 게시물이 있습니다. 삭제해주세요.', '2023-05-13', 0, 0, 0, 28),
(29, '신고합니다', '사용자14', '1111', '정체 모를 괴상한 댓글이 등장하고 있습니다. 확인 부탁드립니다.', '2023-05-14', 0, 0, 0, 29),
(30, '신고합니다', '사용자15', '1111', '개인 공격이 이어지고 있습니다. 대응이 필요합니다.', '2023-05-15', 0, 0, 0, 30),
(31, '신고합니다', '사용자16', '1111', '불법 제품 판매가 진행되고 있습니다. 조치 요청드립니다.', '2023-05-16', 0, 0, 0, 31),
(32, '신고합니다', '사용자17', '1111', '스토킹 행위가 의심됩니다. 확인 부탁드립니다.', '2023-05-17', 0, 0, 0, 32),
(33, '신고합니다', '사용자18', '1111', '음란물이 포함된 게시물이 있습니다. 빠른 조치를 부탁드립니다.', '2023-05-18', 0, 0, 0, 33),
(34, '신고합니다', '사용자19', '1111', '정보 유출이 의심됩니다. 확인하고 조치 부탁드립니다.', '2023-05-19', 0, 0, 0, 34),
(35, '신고합니다', '사용자20', '1111', '사용자 간의 갈등으로 인해 공동체 분위기가 나빠지고 있습니다. 중재 부탁드립니다.', '2023-05-20', 0, 0, 0, 35);






-- 질문 답변 같이 있는 ask

insert into ask (id, title, pname, pw, content, reg_date, cnt, seq, lev, gid)
values
(1,'문의드립니다.','콩쥐','1111','콩쥐네 한정식 채용공고 어딨나요','2023-04-01',0,0,0,1),
(2,'문의드립니다','팥쥐','1111','팥쥐네 한정식 채용공고 어딨나요?','2023-04-01',0,0,0,2),
(3,'문의드립니다','두꺼비','1111','두꺼비 한정식 채용공고 어딨나요?','2023-04-01',0,0,0,3),
(4,'문의드립니다','심청이','1111','심청이 한정식 채용 0명은 몇명 뽑는다는건가요?','2023-04-01',0,0,0,4),
(5,'문의드립니다','심봉사','1111','심봉사 지원했는데 저한테 연락이 안오는데요','2023-04-01',0,0,0,5),
(6, '안녕하세요 관리자님!', '구직자', '1111', '사람인 사이트를 통해 토끼네 한정식의 신규 채용 공고를 확인하려고 합니다. 어떻게 찾을 수 있나요?', '2023-04-01', 0, 0, 0, 6),
(7, '안녕하세요 관리자님!', '구직자', '1111', '사슴네 한정식의 최신 채용 정보를 찾고 있습니다. 어떻게 검색해야 할까요?', '2023-04-02', 0, 0, 0, 7),
(8, '안녕하세요 관리자님!', '구직자', '1111', '호랑이네 한정식의 채용 공고를 확인하려고 합니다. 검색하는 방법을 가르쳐 주세요.', '2023-04-03', 0, 0, 0, 8),
(9, '안녕하세요 오왕 관리자님!', '구직자', '1111', '곰네 한정식의 신규 채용 정보를 찾고 있습니다. 어디서 검색해야 할까요?', '2023-04-04', 0, 0, 0, 9),
(10, '관리자님!', '구직자', '1111', '원숭이네 한정식의 채용 공고를 확인하려면 어떻게 해야 하나요? 도움 부탁드립니다.', '2023-04-05', 0, 0, 0, 10),
(11, '답변입니다.', '관리자', '1111', '콩쥐네 한정식 채용공고는 여기 있습니다.', '2023-04-02', 0, 0, 1, 1),
(12, '답변입니다.', '관리자', '1111', '팥쥐네 한정식 채용공고는 다음과 같습니다.', '2023-04-02', 0, 0, 1, 2),
(13, '답변입니다.', '관리자', '1111', '두꺼비네 한정식 채용공고는 다음과 같습니다.', '2023-04-02', 0, 0, 1, 3),
(14, '답변입니다.', '관리자', '1111', '심청이네 한정식 채용공고는 다음과 같습니다.', '2023-04-02', 0, 0, 1, 4),
(15, '답변입니다.', '관리자', '1111', '심봉사네 한정식 채용공고는 다음과 같습니다.', '2023-04-02', 0, 0, 1, 5),
(16, '답변입니다.', '관리자', '1111', '토끼네 한정식 채용공고 검색 방법은 다음과 같습니다.', '2023-04-02', 0, 0, 1, 6),
(17, '답변입니다.', '관리자', '1111', '사슴네 한정식 채용공고 검색 방법은 다음과 같습니다.', '2023-04-02', 0, 0, 1, 7),
(18, '답변입니다.', '관리자', '1111', '호랑이네 한정식 채용공고 검색 방법은 다음과 같습니다.', '2023-04-02', 0, 0, 1, 8),
(19, '답변입니다.', '관리자', '1111', '곰네 한정식 채용공고 검색 방법은 다음과 같습니다.', '2023-04-02', 0, 0, 1, 9),
(20, '답변입니다.', '관리자', '1111', '원숭이네 한정식 채용공고 검색 방법은 다음과 같습니다.', '2023-04-02', 0, 0, 1, 10);