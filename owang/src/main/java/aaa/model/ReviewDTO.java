package aaa.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.ibatis.type.Alias;


import lombok.Data;

@Data
@Alias("rrDTO")
public class ReviewDTO {
	String sid, cid, rvtitle, rvjang, rvdan;
	int rvid;
	Date regDate = new Date();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	public String getRegDate() {
		return sdf.format(regDate);
	}
	int start, limit = 5, pageLimit = 4, page, pageStart, pageEnd, pageTotal, cnt;
		
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

	

		public ReviewDTO() {
			super();
		}

	
		
		
}
