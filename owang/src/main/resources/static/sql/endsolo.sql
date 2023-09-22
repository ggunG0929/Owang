CREATE TABLE endsolo (
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
    sjoindate	date,
    sdeletedate date
);
select * from endsolo;
drop table endsolo;