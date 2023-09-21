# 이력서
select * from resume;
#테이블삭제
drop table resume;

CREATE TABLE resume (
    rsid	INT AUTO_INCREMENT PRIMARY KEY,
    sid	VARCHAR(100),
    rsphoto	VARCHAR(100),
    rstitle VARCHAR(100),
    rscareer	VARCHAR(100),
    rscompany	VARCHAR(100),
    rsworkstart VARCHAR(100),
    rsworkend	VARCHAR(100),
    rswork	VARCHAR(100),
    rsacademic	VARCHAR(100),
    rsacademicstat	VARCHAR(100),
    rsintroduce	VARCHAR(100),
    rslicense	VARCHAR(100),
    rslicenseorg	VARCHAR(100),
    rslicenseyear	VARCHAR(100),
    rslanguage	VARCHAR(100),
    rslanguagelevel	VARCHAR(100),
    rsregdate	TIMESTAMP,
    rsmoddate	TIMESTAMP,
    cnt	INT
) AUTO_INCREMENT = 1;


# 더미데이터
INSERT INTO resume ( rsid, sid, rstitle, rscareer, rsacademic,rsacademicstat,rsintroduce,rslicense,rslicenseorg,rslicenseyear,rslanguage,rslanguagelevel,rsregdate,rsmoddate) VALUES 
	(1, 'review', '[요리에 미치다] 강본죽입니다.', '신입', '대학(4년)', '졸업', '안녕하십니까\n 끊임없는 도전을 통해 요리의 미래를 만들고자 하는 요리사입니다. 함께 일하고 싶습니다. 감사합니다.', '한식조리사', '한국산업인력공단', '2022-01-03', '영어', '회화 능숙', '2023-09-17 16:11:19', '2023-09-17 16:11:19'),
	(2, 'review1', '[요리에 미치다] 마동석입니다.', '신입', '대학(4년)', '졸업', '안녕하십니까\n 끊임없는 도전을 통해 요리의 미래를 만들고자 하는 요리사입니다. 함께 일하고 싶습니다. 감사합니다.', '한식조리사', '한국산업인력공단', '2022-01-03', '영어', '회화 능숙', '2023-09-17 16:11:19', '2023-09-17 16:11:19'),
	(3, 'review2', '[요리에 미치다] 이민지입니다.', '신입', '대학(4년)', '졸업', '안녕하십니까\n 성장해 나아가는 요리사입니다. 함께 일하고 싶습니다. 감사합니다.', '한식조리사', '한국산업인력공단', '2022-01-03', '영어', '회화 능숙', '2023-09-17 16:11:19', '2023-09-17 16:11:19'),
	(4, 'resume','[노력하는 요리사] 백종원 입니다.','신입','대학(4년)', '졸업','안녕하십니까 저는 최선을 다하는 요리사입니다. 귀사와 함께 성장하고 싶습니다. 감사합니다.', '한식조리사', '한국산업인력공단', '2022-01-03', '영어', '회화 능숙', '2022-09-16', '2022-09-16'),
	(5, 'resume','[요리의 미래를 꿈꾸는 요리사] 백종원 입니다.','신입','대학(4년)', '졸업','안녕하세요. 저는 요리에 대한 열정을 가진 요리사입니다. 요리를 통해 사람들에게 기쁨을 전하고 싶습니다. 감사합니다.',	'한식조리사','한국산업인력공단','2022-01-03','영어','회화 능숙','2022-09-17','2022-09-17'),
	(6, 'resume','[요리의 맛을 추구하는 요리사] 백종원 입니다.','신입','대학(4년)', '졸업','안녕하십니까 요리를 통해 맛의 세계를 끝없이 탐험하고 있는 요리사입니다. 맛있는 요리를 만들어 드리고 싶습니다.','한식조리사','한국산업인력공단'	,'2022-01-03','영어','회화 능숙','2022-09-16','2022-09-16'),
	(7, 'resume','[요리에 미치다] 백종원 입니다.','신입','대학(4년)', '졸업','안녕하십니까 끊임없는 도전을 통해 요리의 미래를 만들고자 하는 요리사입니다. 함께 일하고 싶습니다. 감사합니다.','한식조리사','한국산업인력공단','2022-01-03','영어','회화 능숙','2022-09-17','2022-09-17');
