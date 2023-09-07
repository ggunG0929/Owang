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
	public int stype=1;
	public Date sdate;
	public int sbcnt;
	//1 : 일반개인고객 , 2: 결제권 있는 개인고객, 3: 블랙개인
	
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
	public SoloDTO(MultipartFile mmff, int sno, String sid, String spw, String spw2, String sname, String sjumin,
			String sbirth, int sage, String sgender, String sphone, String semail, String saddress, String scompanyName,
			String scompanyFile, int stype, Date sdate, int sbcnt) {
		super();
		this.mmff = mmff;
		this.sno = sno;
		this.sid = sid;
		this.spw = spw;
		this.spw2 = spw2;
		this.sname = sname;
		this.sjumin = sjumin;
		this.sbirth = sbirth;
		this.sage = sage;
		this.sgender = sgender;
		this.sphone = sphone;
		this.semail = semail;
		this.saddress = saddress;
		this.scompanyName = scompanyName;
		this.scompanyFile = scompanyFile;
		this.stype = stype;
		this.sdate = sdate;
		this.sbcnt = sbcnt;
	}
	
	
	
	
	
	
}