package aaa.model;

import java.util.Date;
import java.util.regex.Pattern;

import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Alias("cDTO")
@Data
public class CompanyDTO {
	
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
	
	
	Date regDate;
	
	CompanyDTO(){
		
	}
	
	
	
	



	
}
