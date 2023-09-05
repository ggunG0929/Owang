package aaa.service;



import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;


import aaa.model.MCompanyDTO;
import aaa.model.SoloDTO;


@Mapper
public interface MCompanyMapper {


		void insertCompany(MCompanyDTO company);
	
	
}
