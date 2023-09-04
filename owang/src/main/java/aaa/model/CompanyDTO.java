package aaa.model;
import java.util.Date;
import java.util.regex.Pattern;

import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Alias("cDTO")
@Data
public class CompanyDTO {
	
	MultipartFile ccompanyfile; //사업자 등록파일
	MultipartFile mmff;
	
	int cno;        	//무결성
	int cstaff;     	//직원수
	int cbcnt;      	//블랙경고
	long csales;    	//1년매출액
	int page;			
	String cid;        	//기업아이디
	String cpw;     	//기업 비밀번호
	String cname;   	//기업 이름
	String cbuild;  	//설립연도
	String ccategory;	//업종
	String ccall;		//회사전화번호
	String cceo;		//대표이름
	String caddress;	//회사주소
	String ctype;		//타입
	String cemail;		//이메일
	String ccontent;	//내용
	Date cdate;			//공고상품 종료기간
	
	int cnt, seq, lev, gid, start, limit = 3,pageLimit=4, pageStart, pageEnd, pageTotal;
	String title, upfile, msg, goUrl ;
	String grade;
	
	Date regDate;
	
	CompanyDTO(){
		
	}
	
	public String getUpfile() {
		if(upfile == null || upfile.trim().equals("") ||  upfile.trim().equals("null") ) {
			upfile = null;
		}
		return upfile;
	}
	
	public boolean isImg() {
		if(getUpfile()==null) {
			return false;
		}
		return Pattern.matches(".{1,}[.](bmp|png|gif|jpg|jpeg)", upfile.toLowerCase());
	}
	
	
	public void calc(int total) {
		
		
		start = (page -1) * limit;
		
		pageStart = (page -1)/pageLimit*pageLimit +1;
		pageEnd = pageStart + pageLimit -1;
		
		
		pageTotal = total / limit;
		if(total % limit != 0) {
			pageTotal++;
		}
		
		if(pageEnd > pageTotal) {
			pageEnd = pageTotal;
		}
		
	}

	public CompanyDTO(String cid) {
		super();
		this.cid = cid;
	}

	
}
