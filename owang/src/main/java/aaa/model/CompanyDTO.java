package aaa.model;
<<<<<<< HEAD
import java.util.Date;
import java.util.regex.Pattern;

import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;
=======
<<<<<<< HEAD
import org.apache.ibatis.type.Alias;
=======

import java.util.Date;
import java.util.regex.Pattern;

import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;
>>>>>>> 5ee8a3d557027ab2aeccfd5cb54ed07e5be9b894
>>>>>>> 8bd68bb398d7866c23810243622df4e0dd8ea394

import lombok.Data;

@Alias("cDTO")
@Data
public class CompanyDTO {
	
<<<<<<< HEAD
	MultipartFile ccompanyfile; //사업자 등록파일
	MultipartFile mmff;
	
	int cno;        	//무결성
	int cstaff;     	//직원수
	int cbcnt;      	//블랙경고
	long csales;    	//매출액
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
=======
<<<<<<< HEAD
	int id;
	String cname, pw, year, form, employees, sales, content;
=======
	MultipartFile mmff; // 사진 혹시몰라서 

	int cid, cnt, seq, lev, gid, start, limit = 3,pageLimit=4, page, pageStart, pageEnd, pageTotal;
	String title, pname, pw, upfile, content, msg, goUrl ;
	String grade;
	String newcomer;//신입
	String career;//경력
	String nolimit; //제한없음
	String chodae; // 초대졸
	String university; // 대졸
	String money; //급여
	String workdate; // 근무일시
	
>>>>>>> 8bd68bb398d7866c23810243622df4e0dd8ea394
	
	Date regDate;
	
	CompanyDTO(){
		
	}
	
<<<<<<< HEAD
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

=======
	
	
	



>>>>>>> 5ee8a3d557027ab2aeccfd5cb54ed07e5be9b894
>>>>>>> 8bd68bb398d7866c23810243622df4e0dd8ea394
	
}
