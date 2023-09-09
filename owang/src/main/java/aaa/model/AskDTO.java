package aaa.model;

import java.util.Date;
import java.util.regex.Pattern;

import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Alias("askDTO")
@Data
public class AskDTO {
	//seq : 답글의 순서
	//lev : 답글의 계층
	//gid : 답글 그룹에 속하는 답글들을 식별하는 데 사용
	
	

	int id, cnt, seq, lev, gid, start, limit = 10,pageLimit=5, page, pageStart, pageEnd, pageTotal;
	String title, pname, pw, upfile, content, msg, goUrl ;
	String cid;
	String grade;
	
	Date regDate;
	
	public AskDTO(){
		
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


	public AskDTO(String title, String pname, String pw, String content) {
		super();
		this.title = title;
		this.pname = pname;
		this.pw = pw;
		this.content = content;
	}
}
