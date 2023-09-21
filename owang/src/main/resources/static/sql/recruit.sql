# 채용공고 게시판
select * from recruit;
# 테이블삭제 게시판
drop table recruit;

CREATE TABLE recruit (
    recruit_id INT AUTO_INCREMENT PRIMARY KEY,
    cid VARCHAR(100),
    recruit_title VARCHAR(100),
    recruit_upfile VARCHAR(100),
    recruit_content VARCHAR(9999),
    reg_date TIMESTAMP,
    realMagam TIMESTAMP,
    recruit_magam INT,
    cnt INT,
    recruit_money INT,
    cname VARCHAR(100),
    rtype INT DEFAULT 1,
    recruit_mtype VARCHAR(100) DEFAULT NULL,  -- recruit_mtype 열 추가 및 기본값 설정
    appnum INT DEFAULT 0  -- appnum 열 추가 및 기본값 설정
);

# mtype이 없는 경우
ALTER TABLE recruit
ADD recruit_mtype varchar(100);

ALTER TABLE recruit
ADD appnum INT DEFAULT 0;

ALTER TABLE recruit
ADD appnum INT DEFAULT 0;

# realMagam  업데이트
UPDATE recruit
SET realMagam = DATE_ADD(reg_date, INTERVAL 7 DAY)
WHERE realMagam IS NULL;

# cname 채우기
UPDATE recruit r
JOIN company c ON r.cid = c.ctype
SET r.cname = c.cname;


# cname, rtype 없음
INSERT INTO recruit (
    recruit_title, cid, recruit_content, recruit_upfile, reg_date, recruit_money,
    recruit_magam, cnt, realMagam
) VALUES
('3월 채용 공고9', 'company5', '3월 채용 공고 내용입니다.', '파일명2.txt', '2023-03-06 00:00:00', 60000, 7, 0, '2023-02-22 00:00:00'),
('3월 채용 공고10', 'company5', '3월 채용 공고 내용입니다.', '파일명2.txt', '2023-03-07 00:00:00', 60000, 7, 0, '2023-02-22 00:00:00'),
('3월 채용 공고11', 'company5', '3월 채용 공고 내용입니다.', '파일명2.txt', '2023-03-13 00:00:00', 60000, 7, 0, '2023-02-22 00:00:00'),
('3월 채용 공고12', 'company5', '3월 채용 공고 내용입니다.', '파일명2.txt', '2023-03-14 00:00:00', 60000, 7, 0, '2023-02-22 00:00:00'),
('4월 채용 공고1', 'company1', '4월 채용 공고 내용입니다.', '파일명1.txt', '2023-04-01 00:00:00', 80000, 30, 0, '2023-04-30 00:00:00'),
('4월 채용 공고2', 'company2', '4월 채용 공고 내용입니다.', '파일명2.txt', '2023-04-05 00:00:00', 90000, 30, 0, '2023-05-05 00:00:00'),
('4월 채용 공고3', 'company3', '4월 채용 공고 내용입니다.', '파일명3.txt', '2023-04-10 00:00:00', 100000, 30, 0, '2023-05-10 00:00:00'),
('4월 채용 공고4', 'company4', '4월 채용 공고 내용입니다.', '파일명4.txt', '2023-04-15 00:00:00', 110000, 30, 0, '2023-05-15 00:00:00'),
('4월 채용 공고5', 'company5', '4월 채용 공고 내용입니다.', '파일명5.txt', '2023-04-20 00:00:00', 120000, 30, 0, '2023-05-20 00:00:00'),
('5월 채용 공고1', 'company1', '5월 채용 공고 내용입니다.', '파일명1.txt', '2023-05-01 00:00:00', 100000, 120, 0, '2023-08-29 00:00:00'),
('5월 채용 공고2', 'company2', '5월 채용 공고 내용입니다.', '파일명2.txt', '2023-05-05 00:00:00', 110000, 120, 0, '2023-09-02 00:00:00'),
('5월 채용 공고3', 'company3', '5월 채용 공고 내용입니다.', '파일명3.txt', '2023-05-10 00:00:00', 120000, 120, 0, '2023-09-07 00:00:00'),
('5월 채용 공고4', 'company4', '5월 채용 공고 내용입니다.', '파일명4.txt', '2023-05-15 00:00:00', 130000, 120, 0, '2023-09-12 00:00:00'),
('5월 채용 공고5', 'company5', '5월 채용 공고 내용입니다.', '파일명5.txt', '2023-05-20 00:00:00', 140000, 120, 0, '2023-09-17 00:00:00'),
('6월 채용 공고1', 'company1', '6월 채용 공고 내용입니다.', '파일명1.txt', '2023-06-01 00:00:00', 150000, 240, 0, '2023-09-29 00:00:00'),
('6월 채용 공고2', 'company2', '6월 채용 공고 내용입니다.', '파일명2.txt', '2023-06-05 00:00:00', 160000, 240, 0, '2023-10-03 00:00:00'),
('6월 채용 공고3', 'company3', '6월 채용 공고 내용입니다.', '파일명3.txt', '2023-06-10 00:00:00', 170000, 240, 0, '2023-10-08 00:00:00'),
('6월 채용 공고4', 'company4', '6월 채용 공고 내용입니다.', '파일명4.txt', '2023-06-15 00:00:00', 180000, 240, 0, '2023-10-13 00:00:00'),
('6월 채용 공고5', 'company5', '6월 채용 공고 내용입니다.', '파일명5.txt', '2023-06-20 00:00:00', 190000, 240, 0, '2023-10-18 00:00:00'),
('7월 채용 공고1', 'company1', '7월 채용 공고 내용입니다.', '파일명1.txt', '2023-07-01 00:00:00', 200000, 480, 0, '2023-11-28 00:00:00'),
('7월 채용 공고2', 'company2', '7월 채용 공고 내용입니다.', '파일명2.txt', '2023-07-05 00:00:00', 210000, 480, 0, '2023-12-02 00:00:00'),
('7월 채용 공고3', 'company3', '7월 채용 공고 내용입니다.', '파일명3.txt', '2023-07-10 00:00:00', 220000, 480, 0, '2023-12-07 00:00:00'),
('7월 채용 공고4', 'company4', '7월 채용 공고 내용입니다.', '파일명4.txt', '2023-07-15 00:00:00', 230000, 480, 0, '2023-12-12 00:00:00'),
('7월 채용 공고5', 'company5', '7월 채용 공고 내용입니다.', '파일명5.txt', '2023-07-20 00:00:00', 240000, 480, 0, '2023-12-17 00:00:00'),
('7월 채용 공고1', 'company1', '7월 채용 공고 내용입니다.', '파일명1.txt', '2023-07-01 00:00:00', 130000, 30, 0, '2023-07-31 00:00:00'),
('7월 채용 공고2', 'company2', '7월 채용 공고 내용입니다.', '파일명2.txt', '2023-07-05 00:00:00', 140000, 30, 0, '2023-08-04 00:00:00'),
('7월 채용 공고3', 'company3', '7월 채용 공고 내용입니다.', '파일명3.txt', '2023-07-10 00:00:00', 150000, 30, 0, '2023-08-09 00:00:00'),
('7월 채용 공고4', 'company4', '7월 채용 공고 내용입니다.', '파일명4.txt', '2023-07-15 00:00:00', 160000, 30, 0, '2023-08-14 00:00:00'),
('7월 채용 공고5', 'company5', '7월 채용 공고 내용입니다.', '파일명5.txt', '2023-07-20 00:00:00', 170000, 30, 0, '2023-08-19 00:00:00'),
('8월 채용 공고1', 'company1', '8월 채용 공고 내용입니다.', '파일명1.txt', '2023-08-01 00:00:00', 180000, 30, 0, '2023-08-31 00:00:00'),
('8월 채용 공고2', 'company2', '8월 채용 공고 내용입니다.', '파일명2.txt', '2023-08-05 00:00:00', 190000, 30, 0, '2023-09-04 00:00:00'),
('8월 채용 공고3', 'company3', '8월 채용 공고 내용입니다.', '파일명3.txt', '2023-08-10 00:00:00', 200000, 30, 0, '2023-09-09 00:00:00'),
('8월 채용 공고4', 'company4', '8월 채용 공고 내용입니다.', '파일명4.txt', '2023-08-15 00:00:00', 210000, 30, 0, '2023-09-14 00:00:00'),
('8월 채용 공고5', 'company5', '8월 채용 공고 내용입니다.', '파일명5.txt', '2023-08-20 00:00:00', 220000, 30, 0, '2023-09-19 00:00:00');

INSERT INTO recruit (
    cid, recruit_title, recruit_content, reg_date, realMagam, recruit_magam, cnt, recruit_money
)VALUES
('company1', '성실한 요리사를 모집합니다.', '함께할 열정적인 요리사를 찾고 있습니다. 다음은 채용 상세 정보입니다.
* 채용인원: 2명
* 급여: 협의
* 정규직 채용
* 경력자 및 신입 모두 환영
* 연휴 근무 가능자
* 한식 조리사 자격증 소지자 우대', '2023-09-01 17:15:41', '2023-09-08 17:14:39', 7, 1, 300),
('company2', '가족같은 한정식집입니다. 함께할 요리사를 모집합니다.', '함께할 긍정적인 요리사를 찾고 있습니다. 다음은 채용 상세 정보입니다.
* 채용인원: 1명
* 급여: 협의
* 정규직 채용
* 경력자 및 신입 모두 환영
* 연휴 근무 가능자
* 영어 의사소통자 우대', '2023-09-01 17:15:41', '2023-09-08 17:14:39', 7, 1, 300),
('company2', '가족같은 한정식집입니다. 함께할 요리사를 모집합니다.', '함께할 긍정적인 요리사를 찾고 있습니다. 다음은 채용 상세 정보입니다.
* 채용인원: 1명
* 급여: 협의
* 정규직 채용
* 경력자 및 신입 모두 환영
* 연휴 근무 가능자
* 영어 의사소통자 우대', '2023-09-01 17:15:41', '2023-09-08 17:14:39', 7, 1, 300),
('company3', '[급구] 요리사 모집', '추석연휴 앞두고 함께할 직원을 구합니다.
* 채용인원: 1명
* 급여: 협의
* 정규직 채용
* 경력자 및 신입 모두 환영
* 연휴 근무 가능자', '2023-09-01 17:15:41', '2023-09-08 17:14:39', 7, 1, 300),
('company10', '[급구] 요리사 모집', '설날연휴 앞두고 함께할 직원을 구합니다.
* 채용인원: 1명
* 급여: 2600 만원
* 신입 모집
* 연휴 근무 가능자', '2023-01-01 17:15:41', '2023-01-08 17:14:39', 7, 1, 300),
('company2', '한식 요리사 모집', '한식 요리사를 구합니다. 우리 음식점에서 함께 일하실 분을 찾고 있습니다.
* 채용인원: 1명
* 급여: 300 만원
* 경력자 우대
* 연휴 근무 가능자 환영
* 식사 제공', '2023-01-01 00:00:00', '2023-01-08 00:00:00', 7, 1, 350),
('company3', '한식 요리사 모집', '한식 요리사를 구합니다. 우리 음식점에서 함께 일하실 분을 찾고 있습니다.
* 채용인원: 1명
* 급여: 270 만원
* 경력자 및 신입 모두 환영
* 연휴 근무 가능자 환영
* 근무시간 조정 가능', '2023-01-02 00:00:00', '2023-01-09 00:00:00', 7, 1, 250),
('company4', '한식 요리사 모집', '한식 요리사를 구합니다. 우리 음식점에서 함께 일하실 분을 찾고 있습니다.
* 채용인원: 1명
* 급여: 320 만원
* 경력자 우대
* 연휴 근무 가능자 환영
* 주휴일 조정 가능', '2023-01-03 00:00:00', '2023-01-10 00:00:00', 7, 1, 270),
('company5', '한식 요리사 모집', '한식 요리사를 구합니다. 우리 음식점에서 함께 일하실 분을 찾고 있습니다.
* 채용인원: 1명
* 급여: 400 만원
* 경력자 및 신입 모두 환영
* 연휴 근무 가능자 환영
* 복지 혜택 제공', '2023-01-04 00:00:00', '2023-01-11 00:00:00', 7, 1, 300),
('company6', '한식 요리사 모집', '한식 요리사를 구합니다. 우리 음식점에서 함께 일하실 분을 찾고 있습니다.
* 채용인원: 1명
* 급여: 250 만원
* 경력자 우대
* 연휴 근무 가능자 환영
* 근무지 내 식사 제공', '2023-01-05 00:00:00', '2023-01-12 00:00:00', 7, 1, 260),
('company7', '한식 요리사 모집', '한식 요리사를 구합니다. 우리 음식점에서 함께 일하실 분을 찾고 있습니다.
* 채용인원: 1명
* 급여: 290 만원
* 경력자 및 신입 모두 환영
* 연휴 근무 가능자 환영
* 근무시간 유연', '2023-01-06 00:00:00', '2023-01-13 00:00:00', 7, 1, 380),
('company8', '한식 요리사 모집', '한식 요리사를 구합니다. 우리 음식점에서 함께 일하실 분을 찾고 있습니다.
* 채용인원: 1명
* 급여: 230 만원
* 경력자 및 신입 모두 환영
* 연휴 근무 가능자 환영
* 열정적인 분 환영', '2023-01-07 00:00:00', '2023-01-14 00:00:00', 7, 1, 290),
('company9', '한식 요리사 모집', '한식 요리사를 구합니다. 우리 음식점에서 함께 일하실 분을 찾고 있습니다.
* 채용인원: 1명
* 급여: 270 만원
* 경력자 우대
* 연휴 근무 가능자 환영
* 복지 혜택 제공', '2023-01-08 00:00:00', '2023-01-15 00:00:00', 7, 1, 210),
('company10', '한식 요리사 모집', '한식 요리사를 구합니다. 우리 음식점에서 함께 일하실 분을 찾고 있습니다.
* 채용인원: 1명
* 급여: 350 만원
* 경력자 및 신입 모두 환영
* 연휴 근무 가능자 환영
* 주휴일 조정 가능', '2023-01-09 00:00:00', '2023-01-16 00:00:00', 7, 1, 300),
('company11', '직원 채용', '직원을 구합니다. 우리와 함께 일하실 분을 찾고 있습니다.
* 채용인원: 1명
* 급여: 280 만원
* 경력자 및 신입 모두 환영
* 연휴 근무 가능자 환영
* 근무시간 조정 가능', '2023-02-01 00:00:00', '2023-02-08 00:00:00', 7, 1, 320),
('company12', '[급구]직원모집', '
* 채용인원: 1명
* 급여: 260 만원
* 경력자 우대
* 연휴 근무 가능자 환영
* 주휴일 조정 가능', '2023-02-02 00:00:00', '2023-02-09 00:00:00', 7, 1, 280),
('company13', '#직원모집', '음식점 직원을 구합니다. 우리 음식점에서 함께 일하실 분을 찾고 있습니다.
* 채용인원: 1명
* 급여: 300 만원
* 경력자 및 신입 모두 환영
* 연휴 근무 가능자 환영
* 복지 혜택 제공', '2023-02-03 00:00:00', '2023-02-10 00:00:00', 7, 1, 320),
('company14', '직원_구인', '음식점 직원을 구합니다. 우리 음식점에서 함께 일하실 분을 찾고 있습니다.
* 채용인원: 1명
* 급여: 270 만원
* 경력자 및 신입 모두 환영
* 연휴 근무 가능자 환영
* 식사 제공', '2023-02-04 00:00:00', '2023-02-11 00:00:00', 7, 1, 300),
('company15', '주방이모 구합니다.', '함께 일하실 분을 찾고 있습니다.
* 채용인원: 1명
* 급여: 350 만원
* 경력자 및 신입 모두 환영
* 연휴 근무 가능자 환영
* 주휴일 조정 가능', '2023-02-05 00:00:00', '2023-02-12 00:00:00', 7, 1, 370),
('company16', '면접 바로가능 <직원구함>', '음식점 직원을 구합니다. 우리 음식점에서 함께 일하실 분을 찾고 있습니다.
* 채용인원: 1명
* 급여: 290 만원
* 경력자 우대
* 연휴 근무 가능자 환영
* 열정적인 분 환영', '2023-02-06 00:00:00', '2023-02-13 00:00:00', 7, 1, 310),
('company7', '직원 채용', '음식점 직원을 구합니다. 우리 음식점에서 함께 일하실 분을 찾고 있습니다.
* 채용인원: 1명
* 급여: 320 만원
* 경력자 및 신입 모두 환영
* 연휴 근무 가능자 환영
* 근무지 내 식사 제공', '2023-02-07 00:00:00', '2023-02-14 00:00:00', 7, 1, 340),
('company8', '즉시 면접가능', '음식점 직원을 구합니다. 우리 음식점에서 함께 일하실 분을 찾고 있습니다.
* 채용인원: 1명
* 급여: 270 만원
* 경력자 및 신입 모두 환영
* 연휴 근무 가능자 환영
* 근무시간 유연', '2023-02-08 00:00:00', '2023-02-15 00:00:00', 7, 1, 290),
('company1', '신입 대환영!!!', '음식점 직원을 구합니다. 우리 음식점에서 함께 일하실 분을 찾고 있습니다.
* 채용인원: 1명
* 급여: 280 만원
* 경력자 및 신입 모두 환영
* 연휴 근무 가능자 환영
* 복지 혜택 제공', '2023-02-09 00:00:00', '2023-02-16 00:00:00', 7, 1, 300),
('company10', '꼼꼼한 직원 구합니다.', '음식점 직원을 구합니다. 우리 음식점에서 함께 일하실 분을 찾고 있습니다.
* 채용인원: 1명
* 급여: 300 만원
* 경력자 및 신입 모두 환영
* 연휴 근무 가능자 환영
* 주휴일 조정 가능', '2023-02-10 00:00:00', '2023-02-17 00:00:00', 7, 1, 320);

-- 더미데이터
insert into recruit(
   recruit_title, cid, recruit_content, reg_date, cnt
    ) values
('콩쥐네 직원을 구합니다.' , 'company1'  ,'성실한 인재를 구합니다. 채용모집 0명 / 남녀상관없음/','2023-05-04',1),
('요리사 급구', 'company2', '능력 있는 지원자를 찾습니다.', '2023-05-06', 1),
('요리사 급구', 'company3', '팀원을 모집합니다.', '2023-05-08', 1),
('성실한 직원을 구합니다.', 'company4', '경험 있는 요리사를 찾습니다.', '2023-05-10', 1),
('팥쥐네 직원을 구합니다.' , 'company2'  ,'성실한 인재를 구합니다. 채용모집 0명 /','2023-06-04',1),
('팥쥐네 급구', 'company3', '능력 있는 지원자를 찾습니다.', '2023-06-06', 1),
('두꺼비네 급구', 'company4', '팀원을 모집합니다.', '2023-06-08', 1),
('콩쥐네 직원 구합니다.', 'company1', '경험 있는 요리사를 찾습니다.', '2023-06-10', 1),
('콩쥐네 직원을 구합니다.' , 'company1'  ,'성실한 인재를 구합니다. 채용모집 0명 / 남녀상관없음/','2023-07-04',1),
('요리사 급구', 'company2', '능력 있는 지원자를 찾습니다.', '2023-07-06', 1),
('요리사 급구', 'company3', '팀원을 모집합니다.', '2023-07-08', 1),
('성실한 직원을 구합니다.', 'company4', '경험 있는 요리사를 찾습니다.', '2023-07-10', 1),
('팥쥐네 직원을 구합니다.' , 'company2'  ,'성실한 인재를 구합니다. 채용모집 0명 /','2023-07-07',1),
('팥쥐네 급구', 'company3', '능력 있는 지원자를 찾습니다.', '2023-07-07', 1),
('두꺼비네 급구', 'company4', '팀원을 모집합니다.', '2023-07-18', 1),
('요리사[급구]', 'company1', '경험 있는 요리사를 찾습니다.', '2023-07-17', 1),
('요리사 구합니다.', 'company2', '능력 있는 지원자를 찾습니다.', '2023-07-20', 1),
('성실한 요리사 급구', 'company3', '팀을 이끌어 갈 직원들을 모집합니다.', '2023-07-22', 1),
('스탭 모집' , 'company1'  ,'성실한 인재를 구합니다. 친절한 스탭을 구합니다. 채용모집 0명 / 남녀상관없음 /','2023-08-04',1),
('[휴가철] 요리사 급구', 'company2', '능력 있는 지원자를 찾습니다.', '2023-08-06', 1),
('휴가철 비상 요리사 급구', 'company3', '팀원을 모집합니다.', '2023-08-08', 1),
('성실한 직원을 구합니다. 단순알바 지원 금지', 'company4', '경험 있는 요리사를 찾습니다.', '2023-08-10', 1),
('팥쥐네에서 직원을 구합니다.' , 'company2'  ,'성실한 인재를 구합니다. 채용모집 0명 /','2023-08-17',1),
('팥쥐네에서  [단순 요리사] [급구]', 'company3', '능력 있는 지원자를 찾습니다.', '2023-08-18', 1),
('두꺼비네 급구 친절하고 성실한 직원 구합니다.', 'company4', '팀원을 모집합니다.', '2023-08-19', 1),
('요리사[급구]', 'company1', '경험 있는 요리사를 찾습니다.', '2023-07-17', 1),
('요리사 구합니다.', 'company2', '능력 있는 지원자를 찾습니다.', '2023-07-20', 1),
('성실한 요리사 급구', 'company3', '팀을 이끌어 갈 직원들을 모집합니다. 급여는 회사내규, 합격일자는 따로 연락드릴 예정입니다.', '2023-07-22', 1),
('[추석]연휴에 함께할 요리사를 모집합니다.' , 'company1'  ,'성실한 인재를 구합니다!
저희 한정식집은 17년째 근속하는 직원들과 함께하고 있습니다.
많은 지원 부탁드립니다^^
* 채용인원 : 0명
* 급여 : 회사내규
* 정규직 채용
* 공휴일 근무자 환영
* 한식 조리사 자격증 환영
감사합니다.','2023-09-01',1),
('[추석] 요리사 채용', 'company2', '요리사를 모집합니다! 성실하고 열정적인 지원자를 기다립니다.
* 채용인원: 0명
* 급여: 협의
* 정규직 채용
* 경력자 우대
* 연휴 근무 가능
* 한식 조리사 자격증 소지자 우대', '2023-09-02', 1),
('[급구] 주방 스탭 모집', 'company3', '주방 스탭을 급하게 모집합니다. 요리 경험이 있는 분 우대.
* 채용인원: 2명
* 급여: 회사 내규
* 정규직 채용
* 무경력자도 지원 가능
* 주말 근무 가능한 분 우대', '2023-09-03', 1),
('[신입] 주방 보조 스탭 채용', 'company4', '신입 주방 보조 스탭을 모집합니다. 요리에 관심 있는 분 환영합니다.
* 채용인원: 1명
* 급여: 시급
* 아르바이트 채용
* 학생 및 초심자 환영
* 주말 근무 가능한 분 우대', '2023-09-04', 1),
('한식 조리사 모집', 'company5', '한식 조리사를 모집합니다. 경력자 우대합니다.
* 채용인원: 1명
* 급여: 협의
* 정규직 채용
* 경력자 우대
* 식당 업무 경험자 환영', '2023-09-05', 1),
('[신입] 서빙 스탭 채용', 'company1', '신입 서빙 스탭을 모집합니다. 아르바이트로서의 업무를 경험해보고 싶은 분 환영합니다.
* 채용인원: 2명
* 급여: 시급
* 아르바이트 채용
* 경력 무관
* 주말 근무 가능한 분 환영', '2023-09-06', 1),
('[경력자] 주방 스탭 채용', 'company2', '경력자 주방 스탭을 모집합니다. 레스토랑에서의 경험이 있는 분 우대.
* 채용인원: 1명
* 급여: 회사 내규
* 정규직 채용
* 경력자 우대
* 주말 근무 가능한 분 우대', '2023-09-07', 1),
('[아르바이트] 서빙 스탭 채용', 'company3', '서빙 스탭 아르바이트를 모집합니다. 아르바이트 경험이 있는 분 환영합니다.
* 채용인원: 2명
* 급여: 시급
* 아르바이트 채용
* 경력 무관
* 주말 근무 가능한 분 환영', '2023-09-08', 1),
('[주부] 주방 보조 스탭 채용', 'company4', '주부 주방 보조 스탭을 모집합니다. 주부분들 환영합니다.
* 채용인원: 1명
* 급여: 시급
* 아르바이트 채용
* 경력 무관
* 주말 근무 가능한 분 환영', '2023-09-09', 1),
('레스토랑 서빙 스탭 채용', 'company5', '레스토랑 서빙 스탭을 모집합니다. 서빙 경험이 있는 분 우대.
* 채용인원: 2명
* 급여: 회사 내규
* 정규직 채용
* 경력자 우대
* 주말 근무 가능한 분 환영', '2023-09-10', 1),
('요리사 모집', 'company1', '요리사를 모집합니다. 요리 경험이 있는 분 환영합니다.
* 채용인원: 1명
* 급여: 협의
* 정규직 채용
* 경력자 우대
* 연휴 근무 가능
* 한식 조리사 자격증 소지자 우대', '2023-09-11', 1),
('[급구] 요리사 채용', 'company2', '대기업 [장가]에서 근무할 성실한 요리 스테프를 모집합니다! 많은 지원 부탁드립니다.
* 채용인원: 1명
* 급여: 업계최고
* 정규직 채용
* 경력자 우대
* 연휴 근무
* 한식 조리사 자격증 소지자 우대', '2023-09-12', 1),
('한식 조리사 채용', 'company3', '한식 조리사를 모집합니다. 경력자 우대합니다.
* 채용인원: 1명
* 급여: 협의
* 정규직 채용
* 경력자 우대
* 식당 업무 경험자 환영', '2023-09-13', 1),
('[신입] 주방 스탭 채용', 'company3', '신입 주방 스탭을 모집합니다. 요리에 관심 있는 분 환영합니다.
* 채용인원: 2명
* 급여: 시급
* 아르바이트 채용
* 학생 및 초심자 환영
* 주말 근무 가능한 분 우대', '2023-09-14', 1),
('[경력자] 한식 메인 셰프 채용', 'company1', '경력자 서빙 스탭을 모집합니다. 한식_레스토랑에서의 경험이 있는 분 우대.
* 채용인원: 1명
* 급여: 회사 내규
* 정규직 채용
* 경력자 우대
* 주말 근무 가능한 분 우대', '2023-09-14', 1),
('김콩쥐 요리사님 보조요리사 채용', 'company1', '유명셰프 김콩쥐님의 보조 요리사를 모집합니다.
* 채용인원: 1명
* 급여: 업계최고
*  정규직 채용
* 경력 무관
* 주말 근무 가능한 분 환영', '2023-09-16', 1);



-- 본죽 채용공고 19개
insert into recruit(
	cid, recruit_title, recruit_content, reg_date, realMagam, recruit_magam, cnt, recruit_money, cname
    ) values
('bonjuck','[본죽]과 함께할 유능한 요리사를 모집합니다.', '본죽은 따뜻한 요리를 제공하는 회사입니다. 더불어 함께 따뜻한 사람과 성장하고 싶습니다. 많은 지원 부탁드립니다.
* 채용인원: 1명
* 급여: 협의
* 정규직 채용
* 경력자 우대
* 연휴 근무 가능
* 한식 조리사 자격증 소지자 우대', '2023-09-16 17:15:41','2023-09-23 17:14:39',7,1,300,'본죽'),
('bonjuck', '[본죽] 새로운 요리사를 모집합니다.', '본죽에서 함께할 열정적인 요리사를 찾고 있습니다. 다음은 채용 상세 정보입니다.
* 채용인원: 2명
* 급여: 협의
* 정규직 채용
* 경력자 및 신입 모두 환영
* 연휴 근무 가능
* 한식 조리사 자격증 소지자 우대', '2023-09-17 17:15:41', '2023-09-23 17:14:39', 7, 1, 300, '본죽'),
('bonjuck', '[본죽] 한식의 대표, [본죽]에서 요리사를 모집', '본죽에서 함께할 보조 요리사 스태프를 찾고 있습니다. 다음은 채용 상세 정보입니다.
* 채용인원: 3명
* 급여: 협의
* 아르바이트 채용
* 경험자 우대
* 주말 근무 가능자 우대', '2023-09-16 10:30:00', '2023-09-23 10:30:00', 7, 1, 350, '본죽'),
('bonjuck', '[본죽] 주방 보조 스태프 채용합니다.', '본죽에서 함께할 주방 보조 스태프를 모집합니다. 다음은 채용 상세 정보입니다.
* 채용인원: 2명
* 급여: 협의
* 아르바이트 채용
* 경험자 우대
* 주말 근무 가능자 우대', '2023-09-16 14:45:00', '2023-09-23 14:45:00', 7, 1, 280, '본죽'),
('bonjuck', '[본죽] 따뜻한 온정의 직원 구합니다.', '따뜻한 온정의 직원 구합니다. 다음은 채용 상세 정보입니다.
* 채용인원: 1명
* 급여: 협의
* 정규직 채용
* 매장 관리 경력자 우대
* 주말 근무 가능자 우대', '2023-09-16 09:00:00', '2023-09-23 09:00:00', 7, 1, 450, '본죽'),
('bonjuck', '[본죽] 다부진 체력의 직원 구합니다.', '본죽에서 함께할 직원을 찾고 있습니다. 다음은 채용 상세 정보입니다.
* 채용인원: 3명
* 급여: 협의
* 아르바이트 채용
* 경험자 우대
* 주말 근무 가능자 우대', '2023-09-16 14:30:00', '2023-09-23 14:30:00', 7, 1, 320, '본죽'),
('bonjuck', '한국의 맛을 세계로! [본죽] 요리사 채용 중', '본죽에서 함께할 조리 스태프를 모집합니다. 다음은 채용 상세 정보입니다.
* 채용인원: 2명
* 급여: 협의
* 아르바이트 채용
* 조리 경험자 우대
* 주말 근무 가능자 우대', '2023-09-16 11:00:00', '2023-09-23 11:00:00', 7, 1, 370, '본죽'),
('bonjuck', '[본죽]에서 함께할 요리사를 찾습니다. 지원하세요', '본죽에서 함께할 주방 보조 스태프를 모집합니다. 다음은 채용 상세 정보입니다.
* 채용인원: 2명
* 급여: 협의
* 아르바이트 채용
* 경험자 우대
* 주말 근무 가능자 우대', '2023-09-16 14:30:00', '2023-09-23 14:30:00', 7, 1, 300, '본죽'),
('bonjuck', '[본죽] 주방 보조 스태프 채용합니다.', '본죽에서 함께할 주방 보조 스태프를 모집합니다. 다음은 채용 상세 정보입니다.
* 채용인원: 2명
* 급여: 협의
* 아르바이트 채용
* 경험자 우대
* 주말 근무 가능자 우대', '2023-09-16 12:15:00', '2023-09-23 12:15:00', 7, 1, 350, '본죽'),
('bonjuck', '[본죽] 한식의 맛을 함께 만들어갈 [본죽] 요리사 모집 ', '본죽에서 함께할 조리 스태프를 모집합니다. 다음은 채용 상세 정보입니다.
* 채용인원: 1명
* 급여: 협의
* 아르바이트 채용
* 조리 경험자 우대
* 주말 근무 가능자 우대', '2023-09-16 15:45:00', '2023-09-23 15:45:00', 7, 1, 280, '본죽'),
('bonjuck', '[본죽] 서빙 스태프 모집합니다.', '본죽에서 함께할 서빙 스태프를 찾고 있습니다. 다음은 채용 상세 정보입니다.
* 채용인원: 3명
* 급여: 협의
* 아르바이트 채용
* 경험자 우대
* 주말 근무 가능자 우대', '2023-09-16 09:30:00', '2023-09-23 09:30:00', 7, 2, 380, '본죽'),
('bonjuck', '[본죽] 매장 관리자 채용합니다.', '본죽에서 함께할 매장 관리자를 모집합니다. 다음은 채용 상세 정보입니다.
* 채용인원: 1명
* 급여: 협의
* 정규직 채용
* 매장 관리 경력자 우대
* 주말 근무 가능자 우대', '2023-09-16 13:00:00', '2023-09-23 13:00:00', 7, 1, 300, '본죽'),
('bonjuck', '[본죽] 맛과 건강을 함께하는 [본죽]에서 요리사를 모집합니다.', '본죽에서 함께할 조리 스태프를 모집합니다. 다음은 채용 상세 정보입니다.
* 채용인원: 2명
* 급여: 협의
* 아르바이트 채용
* 조리 경험자 우대
* 주말 근무 가능자 우대', '2023-09-16 10:15:00', '2023-09-23 10:15:00', 7, 2, 350, '본죽'),
('bonjuck', '[본죽] 주방 보조 스태프 채용합니다.', '본죽에서 함께할 주방 보조 스태프를 모집합니다. 다음은 채용 상세 정보입니다.
* 채용인원: 2명
* 급여: 협의
* 아르바이트 채용
* 경험자 우대
* 주말 근무 가능자 우대', '2023-09-16 14:30:00', '2023-09-23 14:30:00', 7, 1, 280, '본죽'),
('bonjuck', '[본죽] "요리 예술가를 채용합니다.', '본죽에서 함께할 주방 보조 스태프를 모집합니다. 다음은 채용 상세 정보입니다.
* 채용인원: 2명
* 급여: 협의
* 아르바이트 채용
* 경험자 우대
* 주말 근무 가능자 우대', '2023-09-16 11:30:00', '2023-09-23 11:30:00', 7, 1, 350, '본죽'),
('bonjuck', '[본죽] 조리 스태프 채용합니다.', '본죽에서 함께할 조리 스태프를 모집합니다. 다음은 채용 상세 정보입니다.
* 채용인원: 1명
* 급여: 협의
* 조리 경험자 우대
* 주말 근무 가능자 우대', '2023-09-16 15:00:00', '2023-09-23 15:00:00', 7, 1, 280, '본죽'),
('bonjuck', '[본죽] 서빙 스태프 모집합니다.', '본죽에서 함께할 서빙 스태프를 찾고 있습니다. 다음은 채용 상세 정보입니다.
* 채용인원: 3명
* 급여: 협의
* 경험자 우대
* 주말 근무 가능자 우대', '2023-09-16 09:45:00', '2023-09-23 09:45:00', 7, 1, 370, '본죽'),
('bonjuck', '[본죽] 맛있는 이야기의 주인공, 요리사를 구합니다.', '본죽에서 함께할 직원을 모집합니다. 다음은 채용 상세 정보입니다.
* 채용인원: 1명
* 급여: 협의
* 정규직 채용
* 매장 관리 경력자 우대
* 주말 근무 가능자 우대', '2023-09-16 13:30:00', '2023-09-23 13:30:00', 7, 1, 300, '본죽'),
-- 새마을식당의 공고
('saema', '[새마을] 다부진 체력의 직원 구합니다.', '새마을에서 함께할 직원을 찾고 있습니다. 다음은 채용 상세 정보입니다.
* 채용인원: 3명
* 급여: 협의
* 경험자 우대
* 주말 근무 가능자 우대', '2023-09-01 14:30:00', '2023-09-08 14:30:00', 7, 1, 320, '새마을식당'),
('saema', '한국의 맛을 세계로! [새마을] 요리사 채용 중', '새마을에서 함께할 조리 스태프를 모집합니다. 다음은 채용 상세 정보입니다.
* 채용인원: 2명
* 급여: 협의
* 조리 경험자 우대
* 주말 근무 가능자 우대', '2023-09-03 11:00:00', '2023-09-10 11:00:00', 7, 1, 370, '새마을식당'),
('saema', '[새마을]에서 함께할 요리사를 찾습니다. 지원하세요', '새마을에서 함께할 주방 보조 스태프를 모집합니다. 다음은 채용 상세 정보입니다.
* 채용인원: 2명
* 급여: 협의
* 경험자 우대
* 주말 근무 가능자 우대', '2023-09-07 14:30:00', '2023-09-14 14:30:00', 7, 1, 300, '새마을식당'),
('saema', '[새마을] 주방 보조 스태프 채용합니다.', '새마을에서 함께할 주방 보조 스태프를 모집합니다. 다음은 채용 상세 정보입니다.
* 채용인원: 2명
* 급여: 협의
* 경험자 우대
* 주말 근무 가능자 우대', '2023-09-08 12:15:00', '2023-09-15 12:15:00', 7, 1, 350, '새마을식당'),
('saema', '[새마을] 한식의 맛을 함께 만들어갈 [새마을] 요리사 모집 ', '새마을에서 함께할 조리 스태프를 모집합니다. 다음은 채용 상세 정보입니다.
* 채용인원: 1명
* 급여: 협의
* 조리 경험자 우대
* 주말 근무 가능자 우대', '2023-09-12 15:45:00', '2023-09-19 15:45:00', 7, 1, 280, '새마을식당'),
('saema', '[새마을] 서빙 스태프 모집합니다.', '새마을에서 함께할 서빙 스태프를 찾고 있습니다. 다음은 채용 상세 정보입니다.
* 채용인원: 3명
* 급여: 협의
* 경험자 우대
* 주말 근무 가능자 우대', '2023-09-14 09:30:00', '2023-09-21 09:30:00', 7, 2, 380, '새마을식당');

# 이미 22번이 있다면 바꿔주기
UPDATE recruit 
SET 
    cid = 'bonjuck',
    recruit_title = '[본죽] 함께할 따뜻한 인성의 직원을 모집합니다.',
    recruit_content = '본죽은 따뜻한 요리를 제공하는 회사입니다. 더불어 함께 따뜻한 사람과 성장하고 싶습니다. 많은 지원 부탁드립니다. * 채용인원: 1명 * 급여: 협의 * 정규직 채용 * 경력자 우대 * 연휴 근무 가능 * 한식 조리사 자격증 소지자 우대',
    reg_date = '2023-09-16 17:15:41',
    realMagam = '2023-09-23 17:14:39',
    recruit_magam = 7,
    cnt = 1,
    recruit_money = 300,
    cname = '본죽',
    rtype = 1,
    recruit_upfile ='본죽.jpg'
WHERE
    recruit_id = 22;