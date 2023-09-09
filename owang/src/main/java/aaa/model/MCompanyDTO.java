package aaa.model;



import java.util.Date;

import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;


@Alias("mcDTO")
@Data
public class MCompanyDTO {
	
	MultipartFile mmff;
	int cno;	//개인 무결성참조번호
	String cid;	//아이디
	String cpw;	//비밀번호1
	String cpw2;	//비밀번호2
	String cname;	//회사명
	String cbuild;	//설렵닌도
	String ccategory;	//업종
	int cstaff;	//직원수
	int csales;	//1년매출액
	String ccall;	//회사전화번호
	String cceo;	//대표이름
	String caddress;	//주소
	String ccompanyFile;	//사업자등록증파일 파일명 cid로저장해주기
	String ccontent;
	int ctype=1;
	Date cdate;
	int cbcnt;
	boolean capproval;
	
	int start, limit = 5, pageLimit = 4, page, pageStart, pageEnd, pageTotal, cnt;
	
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
	
	
	
	//파일 이름 없을때 null 값 넣기
		public String getCcompanyFile() {
			if(ccompanyFile==null ||
				ccompanyFile.trim().equals("") ||
				ccompanyFile.trim().equals("null")) {
				ccompanyFile="null";
			}
			return ccompanyFile;	
	}



		
		
}

