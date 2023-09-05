package aaa.service;



import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import aaa.model.MCompanyDTO;
import aaa.model.SoloDTO;


@Mapper
public interface SoloMapper {


		void insertSolo(SoloDTO solo);
		SoloDTO loginSolo(String userid, String userpw);
	
}
