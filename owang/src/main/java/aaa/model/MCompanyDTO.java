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
	//1 : 일반기업고객 , 2: 결제권 있는 기업고객, 3: 블랙기업
	
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

