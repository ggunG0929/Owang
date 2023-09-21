CREATE TABLE endcompany (
	cno	int NOT NULL AUTO_INCREMENT primary key,
    cid varchar(100),
    cpw VARCHAR(100),
    cname	VARCHAR(100),
    cemail	VARCHAR(100),
    cbuild	VARCHAR(100),
    ccategory	varchar(100),
    cstaff	int,
    csales	int,
    ccall	varchar(100),
    cceo	varchar(100),
    caddress	varchar(400),
    ccompanyFile 	VARCHAR(100),
    ccontent	varchar(500),
    ctype	Int default 1,
    cdate	date,
    cbcnt	int,
    capproval	boolean default false,
    cdeletedate date
);
select * from endcompany;
drop table endcompany;