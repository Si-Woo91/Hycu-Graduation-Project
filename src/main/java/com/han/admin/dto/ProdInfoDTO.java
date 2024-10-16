package com.han.admin.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdInfoDTO {

	Long	id;
	
	String	prodType;
	
	String	prodNm;
	
	String	prodCd;
	
	int		prodPrice;
	
	int		productQuantity;
	
	ProdImgDTO prodImgs;
	
	
}
