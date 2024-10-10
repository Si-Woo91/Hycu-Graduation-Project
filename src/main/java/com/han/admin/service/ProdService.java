package com.han.admin.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.han.admin.domain.ProdImg;
import com.han.admin.domain.ProdInfo;
import com.han.admin.dto.ProdImgDTO;
import com.han.admin.dto.ProdInfoDTO;
import com.han.admin.repository.ProdRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProdService {
	
	private static final Logger logger = LoggerFactory.getLogger(ProdService.class);
	
	private final ProdRepository prodRepository;
	
	// 상품 DTO 세팅
	public ProdInfoDTO getProdDTO(String productType, String productName, String productCode
									, int productPrice,int  productQuantity) {
		
		ProdInfoDTO outDTO = new ProdInfoDTO();
		
		outDTO.setProdType(productType);
		outDTO.setProdNm(productName);
		outDTO.setProdCd(productCode);
		outDTO.setProdPrice(productPrice);
		outDTO.setProductQuantity(productQuantity);
		
		
		return outDTO;
	}
	
	/**
	 * 상품 조회 service
	 * 
	 */
	public List<ProdInfoDTO> getPordList(){
		
		List<ProdInfoDTO> outDTOList = new ArrayList<>();
		
		List<ProdInfo> inList = prodRepository.findAll();
		
		
		for(ProdInfo inEntity : inList) {
			
			ProdInfoDTO inDTO = new ProdInfoDTO();
			
			inDTO.setId(inEntity.getId());
			inDTO.setProdType(inEntity.getProdType());
			inDTO.setProdNm(inEntity.getProdNm());
			inDTO.setProdCd(inEntity.getProdCd());
			inDTO.setProdPrice(inEntity.getPrdPrc());
			inDTO.setProductQuantity(inEntity.getPrdQnt());
			
			outDTOList.add(inDTO);
		}
		
		return outDTOList;
	}
	
	
	/**
	 * 상품 저장 service
	 * 
	 * @param inProdDTO
	 * @param inImgDTO
	 */
	public void saveProd(ProdInfoDTO inProdDTO, ProdImgDTO inImgDTO) {

	    logger.debug("상품 저장 service");

	    // ProdImg 엔티티 생성 (ProdInfo는 아직 설정하지 않음)
	    ProdImg inImg = ProdImg.builder()
	            .prodNm(inImgDTO.getProdNm())
	            .imgName(inImgDTO.getImgName())
	            .imgPath(inImgDTO.getImgPath())
	            .imgDetailNm(inImgDTO.getImgDetailNm())
	            .imgDetailPath(inImgDTO.getImgDetailPath())
	            .build();

	    // 이미지 리스트 생성 및 추가
	    List<ProdImg> inImgList = new ArrayList<>();
	    inImgList.add(inImg);

	    // ProdInfo 엔티티 생성 (이미지 리스트 설정)
	    ProdInfo inProd = ProdInfo.builder()
	    		.prodType(inProdDTO.getProdType())
	            .prodNm(inProdDTO.getProdNm())
	            .prodCd(inProdDTO.getProdCd())
	            .prodPrice(inProdDTO.getProdPrice())
	            .produtQuantity(inProdDTO.getProductQuantity())
	            .prodImgList(inImgList)  // 이미지 리스트 추가
	            .build();

	    // ProdImg에 ProdInfo 설정 (양방향 관계를 위해)
	    inImg.setProdInfo(inProd);

	    // 상품 및 이미지 모두 저장 (cascade 옵션이 설정되어 있다면 이미지도 함께 저장됨)
	    prodRepository.save(inProd);
	}
	
}
