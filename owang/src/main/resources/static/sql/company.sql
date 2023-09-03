
#cname = 기업이름
#year = 업력(회사 몇년 됐는지)
#form = 기업분류
#employees = 사원수
#sales = 매출액
#content = 내용
create table company (
	company_id INT AUTO_INCREMENT PRIMARY KEY,
    company_pw VARCHAR(500),
	company_cname VARCHAR(100), 
    company_year VARCHAR(100),
    company_form VARCHAR(100),
    company_employees VARCHAR(100),
	company_sales VARCHAR(9999),
    company_content VARCHAR(9999)
);

insert into company(
	company_cname,
    company_year,
    company_form,
    company_employees,
    company_sales,
    company_content
)
values
('(주)그렉터','업력 8년차','중소기업','50명','90억 1602만원','스마트시티 혁신기업');

select * from company;
select count(*) from company;