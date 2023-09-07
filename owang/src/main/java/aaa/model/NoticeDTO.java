package aaa.model;

import java.util.Date;
import java.util.regex.Pattern;

import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Alias("nDTO")
@Data
public class NoticeDTO {
	

	int id, cnt, seq, lev, gid, start, limit = 3,pageLimit=4, page, pageStart, pageEnd, pageTotal;
	String title, pname, pw, upfile, content, msg, goUrl ;
	String grade;
	
	Date regDate;
	
	NoticeDTO(){
		
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


	public NoticeDTO(String title, String pname, String pw, String content) {
		super();
		this.title = title;
		this.pname = pname;
		this.pw = pw;
		this.content = content;
	}
}
