package com.han.admin.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
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
	
	List<ProdImgDTO> prodImgs;
	
	
}
