package aaa.model;
import org.apache.ibatis.type.Alias;

import lombok.Data;

@Alias("cDTO")
@Data
public class CompanyDTO {
	
	int id;
	String cname, pw, year, form, employees, sales, content;
	
}
