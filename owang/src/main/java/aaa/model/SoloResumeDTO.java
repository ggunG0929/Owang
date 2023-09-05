package aaa.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
@Alias("rDTO")
public class SoloResumeDTO {
	
	MultipartFile rsmmff; // 증명사진
	
	int rsid;    // 이력서 번호
	String rsphoto; // 사진
	
	String rstitle; // 제목
	String rscareer; // 경력
	String rscompany; // 회사명
	String rsworkstart; // 근무시작
	String rsworkend; // 근무끝	
	String rswork; // 담당업무
	
	
	String rsacademic; // 학력
	String rsacademicstat; // 학력 상대
	String rsintroduce; // 자기 소개
	String rslicense; // 자격증
	String rslicenseorg; // 자격증 발행처
	String rslicenseyear; // 자격증 발행년도
	String rslanguage="어학명"; // 어학능력
	String rslanguagelevel; // 어학수준
	Date rsregdate;
	
	int cnt; // 총 이력서 개 수
	
	String [] careers = {"신입", "경력"};
	
	public Map<String, List<String>> getEducational() {
	    Map<String, List<String>> res = new LinkedHashMap<>();
	    
	    List<String> graduateSchool = Arrays.asList("수료", "취득", "재학중", "휴학중", "중퇴");
	    List<String> university = Arrays.asList("졸업", "재학중", "휴학중", "중퇴", "수료");
	    List<String> college = Arrays.asList("졸업", "재학중", "휴학중", "중퇴", "수료");
	    List<String> highSchool = Arrays.asList("졸업", "재학중", "중퇴", "검정고시");
	    List<String> middleSchool = Arrays.asList("졸업", "재학중", "중퇴", "검정고시");
	    List<String> elementarySchool = Arrays.asList("졸업", "중퇴", "검정고시");
	    
	    res.put("대학원", graduateSchool);
	    res.put("대학(4년)", university);
	    res.put("대학(2, 3년)", college);
	    res.put("고등학교", highSchool);
	    res.put("중학교", middleSchool);
	    res.put("초등학교", elementarySchool);
	    
	    return res;
	}


	public Map<String, String> getLanguageList() {
		Map<String, String> res = new LinkedHashMap<>();
		
		res.put("그리스어", "그리스어");
		res.put("네덜란드어", "네덜란드어");
		res.put("노르웨이어", "노르웨이어");
		res.put("독일어", "독일어");
		res.put("러시아어", "러시아어");
		res.put("루마니아어", "루마니아어");
		res.put("마인어", "마인어");
		res.put("몽골어", "몽골어");
		res.put("미얀마어", "미얀마어");
		res.put("베트남어", "베트남어");
		res.put("세르비아어", "세르비아어");
		res.put("스웨덴어", "스웨덴어");
		res.put("스페인어", "스페인어");
		res.put("슬로바키아어", "슬로바키아어");
		res.put("아랍어", "아랍어");
		res.put("영어", "영어");
		res.put("유고어", "유고어");
		res.put("이란(페르시아어)", "이란(페르시아어)");
		res.put("이탈리아어", "이탈리아어");
		res.put("일본어", "일본어");
		res.put("중국어", "중국어");
		res.put("체코어", "체코어");
		res.put("태국어", "태국어");
		res.put("터키어", "터키어");
		res.put("포르투갈어", "포르투갈어");
		res.put("폴란드어", "폴란드어");
		res.put("프랑스어", "프랑스어");
		res.put("헝가리어", "헝가리어");
		res.put("히브리어", "히브리어");
		res.put("힌디어", "힌디어");
		res.put("기타", "기타");
		
		return res;
	}
	
	public Map<String, String> getLanguageLevelList(){
		Map<String, String> res = new LinkedHashMap<>();
		res.put("prefect", "회화 능숙");
		res.put("good", "일상 대화");
		res.put("notbad", "간단 대화");
		
		return res;
	}
	
	public String getUpfile() {
		if(rsphoto == null || rsphoto.trim().equals("") ||  rsphoto.trim().equals("null") ) {
			rsphoto = null;
		}
		return rsphoto;
	}
	
	public boolean isImg() {
		if(getUpfile()==null) {
			return false;
		}
		return Pattern.matches(".{1,}[.](bmp|png|gif|jpg|jpeg)", rsphoto.toLowerCase());
	}
}



