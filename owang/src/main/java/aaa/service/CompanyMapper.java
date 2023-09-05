package aaa.service;



import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import aaa.model.CompanyDTO;
import aaa.model.SoloDTO;


@Mapper
public interface CompanyMapper {


		void insertCompany(CompanyDTO company);
	
	
}
