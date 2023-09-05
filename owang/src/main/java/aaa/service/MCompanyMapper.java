package aaa.service;



import org.apache.ibatis.annotations.Mapper;



import aaa.model.MCompanyDTO;



@Mapper
public interface MCompanyMapper {


		void insertCompany(MCompanyDTO company);
		MCompanyDTO loginCompany(String userid, String userpw);
		
	
}
