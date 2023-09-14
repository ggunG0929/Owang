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
select * from review where sid = 'abc'  order by rvid desc limit 0, 3;
select * from review;
select * from review where cid = 'company1';

INSERT INTO review (sid, cid, rvtitle, rvjang, rvdan)
VALUES
    ('user1', 'company3', '두번째 회사 제목 1', '장점 1', '단점 1');
drop table review;

INSERT INTO review (sid, cid, rvtitle, rvjang, rvdan)
VALUES
     ('user1', 'company1', '제목1', '장점1', '단점1'),
  ('user2', 'company1', '제목2', '장점2', '단점2'),
  ('user3', 'company1', '제목3', '장점3', '단점3'),
  ('user4', 'company1', '제목4', '장점4', '단점4'),
  ('user5', 'company1', '제목5', '장점5', '단점5'),
  ('user5', 'company2', '제목1', '장점1', '단점1'),
  ('user6', 'company2', '제목2', '장점2', '단점2'),
  ('user7', 'company2', '제목3', '장점3', '단점3'),
  ('user8', 'company2', '제목4', '장점4', '단점4'),
  ('user9', 'company2', '제목5', '장점5', '단점5'),
  ('user1', 'company3', '제목1', '장점1', '단점1'),
  ('user2', 'company3', '제목2', '장점2', '단점2'),
  ('user3', 'company3', '제목3', '장점3', '단점3'),
  ('user4', 'company3', '제목4', '장점4', '단점4'),
  ('user5', 'company3', '제목5', '장점5', '단점5'),
  ('user5', 'company4', '제목1', '장점1', '단점1'),
  ('user6', 'company4', '제목2', '장점2', '단점2'),
  ('user7', 'company4', '제목3', '장점3', '단점3'),
  ('user8', 'company4', '제목4', '장점4', '단점4'),
  ('user9', 'company4', '제목5', '장점5', '단점5'),
  ('user1', 'company5', '제목1', '장점1', '단점1'),
  ('user2', 'company5', '제목2', '장점2', '단점2'),
  ('user3', 'company5', '제목3', '장점3', '단점3'),
  ('user4', 'company5', '제목4', '장점4', '단점4'),
  ('user5', 'company5', '제목5', '장점5', '단점5');


INSERT INTO review (sid, cid, rvtitle, rvjang, rvdan)
VALUES
    ('abc', 'company1', '제목1', '장점1', '단점1'),
    ('abc', 'company2', '제목2', '장점2', '단점2'),
    ('abc', 'company3', '제목3', '장점3', '단점3'),
    ('abc', 'company4', '제목4', '장점4', '단점4'),
    ('abc', 'company5', '제목5', '장점5', '단점5'),
    ('user1', 'company1', '제목6', '장점6', '단점6'),
    ('user1', 'company2', '제목7', '장점7', '단점7'),
    ('user1', 'company3', '제목8', '장점8', '단점8'),
    ('user1', 'company4', '제목9', '장점9', '단점9'),
    ('user1', 'company5', '제목10', '장점10', '단점10'),
    ('user2', 'company1', '제목11', '장점11', '단점11'),
    ('user2', 'company2', '제목12', '장점12', '단점12'),
    ('user2', 'company3', '제목13', '장점13', '단점13'),
    ('user2', 'company4', '제목14', '장점14', '단점14'),
    ('user2', 'company5', '제목15', '장점15', '단점15'),
    ('user3', 'company1', '제목16', '장점16', '단점16'),
    ('user3', 'company2', '제목17', '장점17', '단점17'),
    ('user3', 'company3', '제목18', '장점18', '단점18'),
    ('user3', 'company4', '제목19', '장점19', '단점19'),
    ('user3', 'company5', '제목20', '장점20', '단점20'),
    ('user4', 'company1', '제목21', '장점21', '단점21'),
    ('user4', 'company2', '제목22', '장점22', '단점22'),
    ('user4', 'company3', '제목23', '장점23', '단점23'),
    ('user4', 'company4', '제목24', '장점24', '단점24'),
    ('user4', 'company5', '제목25', '장점25', '단점25');
