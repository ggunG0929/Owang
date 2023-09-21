# 기업리뷰 게시판
select * from review;
drop table review;

CREATE TABLE review (
	rvid	INT AUTO_INCREMENT PRIMARY KEY,
    sid	VARCHAR(100),
    cid VARCHAR(100),
    rvtitle	VARCHAR(100),
    rvjang	VARCHAR(500),
    rvdan	VARCHAR(500)
);

INSERT INTO review (sid, cid, rvtitle, rvjang, rvdan)
VALUES
	('user1', 'company3', '[단짠단짠 회사]', '사장님 기분좋을때는 보너스 엄청 주심', '사장님 기분이 좋은적이 거의 없음'),
	('user2', 'company20', '[1년다닌 사람 솔직 리뷰]', '젊은 직원들', '퇴직금 주기 싫어서 일부러 11개월 계약합니다.'),
	('user3', 'company8', '[보상이 철저한 회사]', '열심히 한다면 보상은 충분할껍니다.', '노동시간이 너무 길어요'),
	('user4', 'company20', '[가족경영]', '없음', '가족경영'),
	('user5', 'company17', '[면접에서 하는 말 다 거짓말입니다.]', '급여가 좋습니다.', '면접에서 말한 사옥이전 등 다 거짓말'),
# 더미데이터
	('review', 'bonjuck', '[배울 수 있는 회사, 사회 초년생이라면 추천합니다.]', '장점) 1. 배울수 있다. 2. 대표님이 직원복지에 신경을 많이 씁니다. ', '단점) 1. 회식 강요 심함 2.상여금 주고 생색냅니다.'),
	('review1', 'bonjuck', '[경력 쌓는데 일하기에 좋습니다.]', '1) 팀원 간의 협력을 중요하게 여기며, 함께 일하는 분위기가 좋습니다, 2) 근무 환경은 깨끗하고 안전합니다. ', '1.주말과 휴일에는 고객 수가 늘어 대기 시간이 길어질 때가 있어 스트레스를 받을 때가 있습니다 2.재료를 넉넉히 안사서 옆집가서 빌려야함 종종...'),
	('review2', 'bonjuck', '[요식업계에서 성장하기에 좋은 회사]', '1. 직원들에게 꾸준한 교육 기회를 제공하여 전문성을 향상시킬 수 있습니다. ', '워라벨 없습니다. 돈 벌어도 쓸수있는 시간이 없어요'),
	('review3', 'bonjuck', '[그 누구도 추천하지 않습니다.]', '4대보험이 되는 점 ', '대표와 소통이 매우매우 힘들어 직원들끼리 끈끈합니다. '),
	('user11', 'bonjuck', '[굳이 고생하고싶다면 추천합니다]', '1 쉬는날은 칼 같음 2.중식제공', '1.업무강도가 높음 2. 소통이안됨'),
	('user12', 'bonjuck', '[좋아하는 일을 돈벌면서 할 수있다.]', '1.요식업 창업 준비한다면 추천 2. 한식 레스토랑 분야에서 유명하며, 이곳에서 일하면 한식 요리에 대한 전문 지식과 기술을 향상.', '1. 요리사와 서빙 스태프 등 업무 부담이 높을 수 있으며, 일부 직원들은 업무 양에 대한 스트레스 받음 2. 고객 서비스 측면에서 압박을 받는 경우가 있어, 일부 직원들은 고객과의 상호작용에 어려움을 겪음'),
	('saereview1', 'saema', '[네임밸류값 하는 회사]', '장점) 1.은행대출이 잘나옵니다. 2.명함보여주면 다들 멋있어함 ', '단점 1.급여가 너무 적습니다. 2. 인센티브 없습니다.'),
    ('saereview2', 'saema', '[나이들어도 다니고싶어요]', '1) 팀원 간의 협력을 중요하게 여기며, 함께 일하는 분위기가 좋습니다, 2) 근무 환경은 깨끗하고 안전 ', '1.주말과 휴일에는 고객 수가 늘어 대기 시간이 길어질 때가 있어 스트레스를 받을 때가 있습니다 2.재료를 넉넉히 안사서 옆집가서 빌려야함 종종...'),
	('saereview3', 'saema', '[요식업계에서 성장하기에 좋은 회사]', '1. 직원들에게 꾸준한 교육 기회를 제공하여 전문성을 향상시킬 수 있습니다. ', '워라벨 없습니다. 돈 벌어도 쓸수있는 시간이 없어요');