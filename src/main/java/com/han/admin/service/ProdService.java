package com.han.admin.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.han.admin.domain.ProdImg;
import com.han.admin.domain.ProdInfo;
import com.han.admin.dto.ProdImgDTO;
import com.han.admin.dto.ProdInfoDTO;
import com.han.admin.repository.ImgRepository;
import com.han.admin.repository.ProdRepository;
import com.han.admin.utill.CustomUtill;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProdService {
	
	private static final Logger logger = LoggerFactory.getLogger(ProdService.class);
	
	private final ProdRepository prodRepository;
	
	private final ProdImgService prodImgService;

	private final ImgRepository imgRepository;
	
	/**
	 *  상품 DTO 세팅 (id가 없을 경우)
	 * @param productType
	 * @param productName
	 * @param productCode
	 * @param productPrice
	 * @param productQuantity
	 * @return
	 */
	public ProdInfoDTO setProdDTO(String productType, String productName, String productCode
									, int productPrice, int  productQuantity) {
		
		ProdInfoDTO outDTO = new ProdInfoDTO();
		
		outDTO.setProdType(productType);
		outDTO.setProdNm(productName);
		outDTO.setProdCd(productCode);
		outDTO.setProdPrice(productPrice);
		outDTO.setProductQuantity(productQuantity);
		
		
		return outDTO;
	}
	
	public ProdInfoDTO setProdDTO2(Long id, String productType, String productName, String productCode
									, int productPrice, int  productQuantity) {

		ProdInfoDTO outDTO = new ProdInfoDTO();
		
		outDTO.setId(id);
		outDTO.setProdType(productType);
		outDTO.setProdNm(productName);
		outDTO.setProdCd(productCode);
		outDTO.setProdPrice(productPrice);
		outDTO.setProductQuantity(productQuantity);
		
		
		return outDTO;
}
	
	/**
	 * 상품 조회 service(id만으로 조회)
	 * 
	 */
	public ProdInfoDTO getProductById(Long Id) {
		
		ProdInfoDTO inDto = new ProdInfoDTO();
		ProdInfoDTO outDto = new ProdInfoDTO();
		
		inDto.setId(Id);
		
		ProdInfo inProd = ProdInfo.builder()
				.id(inDto.getId())
				.build();
		
		Optional<ProdInfo> outProd = prodRepository.findById(inProd.getId());
		
		// 상품 정보가 있을때
		if(!CustomUtill.isNullOrEmpty(outProd)) {
			
			ProdInfo ProdEntity = outProd.get();
			
			outDto.setId(ProdEntity.getId());
			outDto.setProdType(ProdEntity.getProdType());
			outDto.setProdNm(ProdEntity.getProdNm());
			outDto.setProdCd(ProdEntity.getProdCd());
			outDto.setProdPrice(ProdEntity.getProdPrc());
			outDto.setProductQuantity(ProdEntity.getProdQnt());
			
			// 이미지 존재
	        if (ProdEntity.getProdImg() != null) {
	        	
	            ProdImgDTO inImgDTO = new ProdImgDTO();
	            
	            inImgDTO.setId(ProdEntity.getProdImg().getId());
	            inImgDTO.setProdNm(ProdEntity.getProdImg().getProdNm());
	            inImgDTO.setImgName(ProdEntity.getProdImg().getDtImglNm());
	            inImgDTO.setImgPath(ProdEntity.getProdImg().getImgPath());
	            inImgDTO.setImgDetailNm(ProdEntity.getProdImg().getDtImglNm());
	            inImgDTO.setImgDetailPath(ProdEntity.getProdImg().getDtImgPath());

	            outDto.setProdImgs(inImgDTO);
	        }
			
		}
		
		
		return outDto;
		
	}
	
	
	/**
	 * 상품 조회 service(전체)
	 * 
	 */
	public List<ProdInfoDTO> getPordList() {

	    List<ProdInfoDTO> outDTOList = new ArrayList<>();
	    List<ProdInfo> inList = prodRepository.findAll();

	    for (ProdInfo inEntity : inList) {

	        ProdInfoDTO inDTO = new ProdInfoDTO();

	        // 상품 세팅
	        inDTO.setId(inEntity.getId());
	        inDTO.setProdType(inEntity.getProdType());
	        inDTO.setProdNm(inEntity.getProdNm());
	        inDTO.setProdCd(inEntity.getProdCd());
	        inDTO.setProdPrice(inEntity.getProdPrc());
	        inDTO.setProductQuantity(inEntity.getProdQnt());

	        // 각 상품의 이미지가 존재할때
	        if (!CustomUtill.isNullOrEmpty(inEntity.getProdImg())) {
	        	
	            ProdImgDTO inImgDTO = new ProdImgDTO();
	            
	            inImgDTO.setId(inEntity.getProdImg().getId());
	            inImgDTO.setProdNm(inEntity.getProdImg().getProdNm());
	            inImgDTO.setImgName(inEntity.getProdImg().getImgNm());
	            inImgDTO.setImgPath(inEntity.getProdImg().getImgPath());
	            inImgDTO.setImgDetailNm(inEntity.getProdImg().getDtImglNm());
	            inImgDTO.setImgDetailPath(inEntity.getProdImg().getDtImgPath());

	            inDTO.setProdImgs(inImgDTO);
	        }

	        
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

	    // 이미지
	    ProdImg inImg = ProdImg.builder()
	            .prodNm(inImgDTO.getProdNm())
	            .imgName(inImgDTO.getImgName())
	            .imgPath(inImgDTO.getImgPath())
	            .imgDetailNm(inImgDTO.getImgDetailNm())
	            .imgDetailPath(inImgDTO.getImgDetailPath())
	            .build();

	    // 상품
	    ProdInfo inProd = ProdInfo.builder()
	    		.prodType(inProdDTO.getProdType())
	            .prodNm(inProdDTO.getProdNm())
	            .prodCd(inProdDTO.getProdCd())
	            .prodPrc(inProdDTO.getProdPrice())
	            .prodQnt(inProdDTO.getProductQuantity())
	            .prodImg(inImg)  // 이미지 리스트 추가
	            .build();

	    // 이미지 객체에 상품 정보 저장
	    inImg.setProdInfo(inProd);

	    // 상품 및 이미지 모두 저장 (cascade 옵션이 설정되어 있다면 이미지도 함께 저장됨)
	    prodRepository.save(inProd);
	}
	
	
	/**
	 * 상품 삭제 service
	 * 
	 * @param ids
	 * @return
	 */
	public boolean deleteProds(List<Long> ids) {
	    logger.debug("상품 삭제 service");
	    
	    try {
	        // 삭제 상품 정보 모두 조회
	        List<ProdInfo> prodInfoList = prodRepository.findAllById(ids);

	        // 서버 폴더에 저장되어 있는 이미지 파일 삭제
	        for (ProdInfo prodInfo : prodInfoList) {
	        	
	            if (prodInfo.getProdImg() != null) {
	            	
	                String imgPath = prodInfo.getProdImg().getImgPath();
	                
	                File imgFile = new File(imgPath);

	                // 이미지 파일이 존재할 경우 삭제
	                if (imgFile.exists()) {
	                	
	                    imgFile.delete();
	                    
	                }

	                // 상세 이미지 파일 경로 가져오기
	                String detailImgPath = prodInfo.getProdImg().getDtImgPath();
	                
	                File detailImgFile = new File(detailImgPath);

	                // 상세 이미지 파일이 존재할 경우 삭제
	                if (detailImgFile.exists()) {
	                	
	                    detailImgFile.delete();
	                    
	                }
	            }
	        }

	        // 상품 삭제
	        prodRepository.deleteAllById(ids);
	        
	        return true;

	    } catch (Exception e) {
	        logger.error("상품 삭제 중 오류 발생: ", e);
	        return false;
	    }
	}
	
	/**
	 * 상품 수정
	 * 
	 */
	@Transactional
	public void updateProd(ProdInfoDTO inProdDTO, ProdImgDTO inImgDTO) {
		
		logger.debug("수정 service");
		
		
		// 새로 받아온 상품 정보
		ProdInfo inProdInfo = ProdInfo.builder()
				.id(inProdDTO.getId())
				.prodType(inProdDTO.getProdType())
				.prodNm(inProdDTO.getProdNm())
				.prodCd(inProdDTO.getProdCd())
				.prodPrc(inProdDTO.getProdPrice())
				.prodQnt(inProdDTO.getProductQuantity())
				.build();
		
		// 기존 이미지 정보 조회
		Optional<ProdImg> inProdImg = imgRepository.findByProdInfoId(inProdInfo.getId());
		logger.debug("기존 이미지 조회 완");
		
		// 기존 대표 이미지 경로
		String oldMainImg = inProdImg.get().getImgPath();
		
		// 기존 상세 이미지 경로
		String oldDtImg = inProdImg.get().getDtImgPath();
		
		// 새로운 이미지 수정 service
		ProdImg newProdImg = prodImgService.updateImg(inProdImg.get(), inImgDTO);
		
		// 상품 조회
		Optional<ProdInfo> getInfo = prodRepository.findById(inProdInfo.getId());
		logger.debug("기존 상품 조회 완");
		
		
		// 기존 상품을 세팅
		ProdInfo updateProdInfo = ProdInfo.builder()
				.id(getInfo.get().getId())
				.prodType(getInfo.get().getProdType())
				.prodNm(getInfo.get().getProdNm())
				.prodCd(getInfo.get().getProdCd())
				.prodPrc(getInfo.get().getProdPrc())
				.prodQnt(getInfo.get().getProdQnt())
				.prodImg(getInfo.get().getProdImg())
				.build();
		
		// 상품 정보 수정(새로운 내용을 기존 상품의 내용에 덮어씌우기)
		updateProdInfo.changeProdInfo(inProdInfo.getProdType(), inProdInfo.getProdNm(), inProdInfo.getProdCd()
									, inProdInfo.getProdPrc(), inProdInfo.getProdQnt(), newProdImg);
		
		// 수정 repository
		prodRepository.save(updateProdInfo);
		logger.debug("수정된 상품 저장 완");
		
		// 이미지 수정시 기존 이미지 파일 삭제
		prodImgService.deleteImgPath(oldMainImg, oldDtImg, newProdImg);
		
		
		
		
	}
		
		
}
	
        
	
