# rvid 무결성
# sid 개인 아이디
# cid 기업 아이디
# rvtitle 리뷰 제목
# rvjang 리뷰 장점
# rvdan 리뷰 단점

CREATE TABLE review (
	rvid INT AUTO_INCREMENT PRIMARY KEY,
    sid VARCHAR(100),
    cid VARCHAR(100),
    rvtitle VARCHAR(100),
    rvjang VARCHAR(100),
    rvdan VARCHAR(100)
);

INSERT INTO review (sid, cid, rvtitle, rvjang, rvdan)
VALUES
    ('abc', 'company1', '리뷰 제목 6', '장점 6', '단점 1'),
    ('abc', 'company1', '리뷰 제목 7', '장점 7', '단점 2'),
    ('abc', 'company1', '리뷰 제목 8', '장점 8', '단점 3'),
    ('abc', 'company1', '리뷰 제목 9', '장점 9', '단점 4'),
    ('abc', 'company1', '리뷰 제목 10', '장점 10', '단점 5');

INSERT INTO review (sid, cid, rvtitle, rvjang, rvdan)
VALUES
    ('user1', 'company2', '두번째 회사 제목 1', '장점 1', '단점 1');
    
select * from review;

drop table review;