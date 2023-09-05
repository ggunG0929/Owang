package aaa.model;

import java.sql.Date;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("rcDTO")
public class SoloRecruitDTO {
	
	// 우선 이렇게 두개만 넣고 구체적인 것들은
	// 공고상세까지 넘겨 받으면 연결
	String recruitTitle;
	Date recruitRegdate;
	int recruitId;
}
