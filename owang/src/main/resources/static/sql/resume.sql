CREATE TABLE resume (
    rsid INT AUTO_INCREMENT PRIMARY KEY,
    sid VARCHAR(100),
    rsphoto VARCHAR(100),
    rstitle VARCHAR(100),
    rscareer VARCHAR(100),
    rscompany VARCHAR(100),
    rsworkstart VARCHAR(100),
    rsworkend VARCHAR(100),
    rswork VARCHAR(100),
    rsacademic VARCHAR(100),
	rsacademicstat VARCHAR(100),
    rsintroduce VARCHAR(100),
    rslisence VARCHAR(100),
    rslisenceorg VARCHAR(100),
    rslisenceyear VARCHAR(100),
    rslanguage VARCHAR(100),
    rslanguagelevel VARCHAR(100),
    rsregdate TIMESTAMP,
    cnt INT
) AUTO_INCREMENT = 1;

select * from resume;
drop table resume;