package aaa.service;



import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import aaa.model.SoloDTO;


@Mapper
public interface SoloMapper {


		void insertSolo(SoloDTO solo);
		
	
}
