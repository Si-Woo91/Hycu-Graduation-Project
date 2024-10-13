package com.han.admin.service;

import java.io.File;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.han.admin.domain.ProdImg;
import com.han.admin.dto.ProdImgDTO;
import com.han.admin.repository.ImgRepository;
import com.han.admin.utill.CustomUtill;

import lombok.RequiredArgsConstructor;

/**
 * 상품 이미지 service
 * 
 */
@RequiredArgsConstructor
@Service
public class ProdImgService {

	private static final Logger logger = LoggerFactory.getLogger(ProdImgService.class);
	
	private final ImgRepository imgRepository;
	
	
	/**
	 * 이미지 경로 service
	 * 
	 */

	public Optional<ProdImg> findByProdInfo(Long id){
		
		return imgRepository.findByProdInfoId(id);
		
	}
	
	/**
	 * 이미지 수정 service
	 * 
	 * @param inImg
	 * @param inImgDTO
	 * @return
	 */
	public ProdImg updateImg(ProdImg inImg, ProdImgDTO inImgDTO) {
		
		logger.debug("이미지 수정을 위해 세팅");
		
		// ImgName은 바꿈 ImgDetailNm은 안바꿈
		if(!CustomUtill.isNullOrEmpty(inImgDTO.getImgName()) && CustomUtill.isNullOrEmpty(inImgDTO.getImgDetailNm())) {
			
			logger.debug("대표 이미지만 세팅");
			
			inImg.changeProdInfo(inImg.getProdNm(), inImgDTO.getImgName(), inImg.getDtImglNm(), inImgDTO.getImgPath(), inImg.getDtImgPath());
			
			
		}
		// ImgName은 안바꿈 ImgDetailNm은 바꿈
		else if (CustomUtill.isNullOrEmpty(inImgDTO.getImgName()) && !CustomUtill.isNullOrEmpty(inImgDTO.getImgDetailNm())) {

			logger.debug("상세 이미지만 세팅");
			
			inImg.changeProdInfo(inImg.getProdNm(), inImg.getImgNm(), inImgDTO.getImgDetailNm(), inImg.getImgPath(), inImgDTO.getImgDetailPath());
			
		}
		// 둘 다 바꿀 경우
		else if(!CustomUtill.isNullOrEmpty(inImgDTO.getImgName()) && !CustomUtill.isNullOrEmpty(inImgDTO.getImgDetailNm())) {

			logger.debug("이미지 모두 세팅");
			
			inImg.changeProdInfo(inImg.getProdNm(), inImgDTO.getImgName(), inImgDTO.getImgDetailNm(), inImgDTO.getImgPath(), inImgDTO.getImgDetailPath());
			
		}
		// 둘 다 안 바꿀 경우
		else
		{
			
			logger.debug("이미지 없을 경우");
			
			inImg.changeProdInfo(inImg.getProdNm(), inImg.getImgNm(), inImg.getDtImglNm(), inImg.getImgPath(), inImg.getDtImgPath());
		}
		
		
		return inImg;
	}

	/**
	 * 
	 * @param oldMainImg	기존 대표이미지 경로
	 * @param oldDtImg		기존 상세이미지 경로
	 * @param inImg			새로운 이미지
	 */
	public void deleteImgPath(String oldMainImg, String oldDtImg, ProdImg inImg) {
		
		// 기존 대표이미지 경로와 새로운 대표 이미지 경로가 다를 경우 기존 이미지 파일 삭제
		if(!oldMainImg.equals(inImg.getImgPath())) {
			
			File imgFile = new File(oldMainImg);
			
            // 이미지 파일이 존재할 경우 삭제
            if (imgFile.exists()) {
            	
                imgFile.delete();
                
            }
			
		}
		
		// 기존 상세이미지 경로와 새로운 상세 이미지 경로가 다를 경우 기존 이미지 파일 삭제
		if(!oldDtImg.equals(inImg.getDtImgPath())) {
			
            File detailImgFile = new File(oldDtImg);

            // 상세 이미지 파일이 존재할 경우 삭제
            if (detailImgFile.exists()) {
            	
                detailImgFile.delete();
                
            }
		}
		
		
		
		
	}
	
}
