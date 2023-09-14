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
@Alias("aDTO")
public class ApplicantDTO {
	
	SoloResumeDTO srDTO;
	RecruitDTO rcDTO;
	MCompanyDTO mcDTO;
	SoloDTO sDTO;
	
	//int recruitId; // 지원한 공고의 번호
	public int ano;  // 지원자 번호
	String sname; // 개인회원
	String aptitle; // 지원서 명
	String recruitTitle; // 공고 제목
	int rsid;
	String sid;
	String cname; // 기업이름
	
	
	Date apsubmitdate; // 지원 일자
		
	// 페이징 처리
    private int start;
    private int limit = 5;
    private int pageLimit = 4;
    private int page;
    private int pageStart = 0;
    private int pageEnd;
    private int pageTotal;
	
	
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
	
	// 생성자에 필드 초기화 또는 설정 코드 추가
    public ApplicantDTO() {
        super();
        // 필드 초기화 또는 설정
        this.start = 0;
        this.page = 1;
    }

    // 필드 값 설정 메서드 추가
    public void setStart(int start) {
        this.start = start;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public void setPageLimit(int pageLimit) {
        this.pageLimit = pageLimit;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setPageStart(int pageStart) {
        this.pageStart = pageStart;
    }

    public void setPageEnd(int pageEnd) {
        this.pageEnd = pageEnd;
    }

    public void setPageTotal(int pageTotal) {
        this.pageTotal = pageTotal;
    }

	
}



