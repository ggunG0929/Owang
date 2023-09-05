package aaa.model;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("pDTO")
public class ProductDTO {
	// 상품명
	String productName;
	// 상품대상(개인/기업)
	String productClient;
	// 상품코드(개인s/기업c + 두자리숫자)
	String productCode;
	// 유효기간(일)
	int productValid;
	// 가격
	int productPrice;
}
