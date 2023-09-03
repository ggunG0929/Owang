package aaa.model;

import java.io.File;

import lombok.Data;

@Data
public class SoloData {
    public String sid; // 아이디
    public String spw1; // 비밀번호
    public String spw2; // 암호확인
    public String sname; // 이름
    public String sjumin; // 주민등록번호 앞자리
    public String sbirth; // 생년월일
    public String sage; // 나이
    public String sgender; // 성별
    public String sphoneNumber; // 번호
    public String semail; // 이메일
    public String saddress; // 주소
    public String scompanyName; // 주소
    public File scompanyFile; // 주소
    
}
