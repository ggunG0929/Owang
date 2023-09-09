package aaa.model;

import java.io.File;
import java.util.Date;

import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Alias("soloDTO")
@Data 
public class SoloDTO {
	MultipartFile mmff; 	//
	public int sno;	//개인부여되는 번호
    public String sid; // 아이디
    public String spw; // 비밀번호
    public String spw2; // 암호확인
    public String sname; // 이름
    public String sjumin; // 주민등록번호 앞자리
    public String sbirth; // 생년월일
    public int sage; // 나이
    public String sgender; // 성별
    public String sphone; // 번호
    public String semail; // 이메일
    public String saddress; // 주소
    public String scompanyName; // 재직중인 직장
    public String scompanyFile; // 직장의 재직자 증명파일
	public int stype;
	public Date sdate;
	public int sbcnt;
	boolean sinjueng;
	//1 : 일반개인고객 , 2: 결제권 있는 개인고객, 3: 블랙개인
	
int start,limit = 5, pageLimit = 4, page, pageStart, pageEnd, pageTotal, cnt;
	
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
	public String getScompanyFile() {
		if(scompanyFile==null ||
			scompanyFile.trim().equals("") ||
			scompanyFile.trim().equals("null")) {
			scompanyFile="null";
		}
		return scompanyFile;	
}

	public SoloDTO() {
		super();
	}

	public SoloDTO(int sno, String sid, String sname, int stype) {
		super();
		this.sno = sno;
		this.sid = sid;
		this.sname = sname;
		this.stype = stype;
	}
	
	
	
	
	
	
	
	
}