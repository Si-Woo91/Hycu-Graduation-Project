package com.han.admin.service;

import java.io.File;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.han.admin.dto.ProdImgDTO;
import com.han.admin.utill.CustomUtill;

import lombok.RequiredArgsConstructor;

/**
 * 이미지 파일 변환 service
 * 
 */
@RequiredArgsConstructor
@Service
public class ConvertImgFormatService {

	private static final Logger logger = LoggerFactory.getLogger(ConvertImgFormatService.class);
	
	@Value("${img.dir}")
	private String imgDir;

	/**
	 * 상품 이미지 형식에 맞게 변환(대표이미지 o , 상세이미지 o)
	 * 
	 * @param prodImg
	 * @param prodDetailImg
	 * @param productName
	 * @return
	 */
	public ProdImgDTO setAllImges(MultipartFile prodImg, MultipartFile prodDetailImg, String productName) {

		ProdImgDTO outDTO = new ProdImgDTO();

		try {
			
			logger.debug("대표, 상세 이미지 둘다 있을 경우");

			if ((prodImg != null && !CustomUtill.isNullOrEmpty(prodImg.getOriginalFilename()))
					|| (prodDetailImg != null && !CustomUtill.isNullOrEmpty(prodDetailImg.getOriginalFilename()))) {
				// 파일을 저장할 세부 경로 지정
				String path = imgDir;
				UUID uuid = UUID.randomUUID();
				File files = new File(path);

				// 디렉터리가 존재하지 않을 경우
				if (!files.exists()) {
					boolean wasSuccessful = files.mkdirs();
					// 디렉터리 생성에 실패했을 경우
					if (!wasSuccessful) {
						logger.debug("file: was not successful");
					}
				}

				// 파일명 중복 피하고자 uuid 설정
				String newImgNm;
				String newImgDetailNm;
				File imgfile;
				File imgdetailfile;

				newImgNm = uuid + "_" + prodImg.getOriginalFilename();
				newImgDetailNm = uuid + "_" + prodDetailImg.getOriginalFilename();

				outDTO.setProdNm(productName);
				outDTO.setImgName(prodImg.getOriginalFilename());
				outDTO.setImgDetailNm(prodDetailImg.getOriginalFilename());
				outDTO.setImgPath(path + newImgNm);
				outDTO.setImgDetailPath(path + newImgDetailNm);

				// 업로드 한 파일 데이터를 지정한 파일에 저장
				imgfile = new File(path, newImgNm);
				prodImg.transferTo(imgfile);

				// 파일 권한 설정(쓰기, 읽기)
				imgfile.setWritable(true);
				imgfile.setReadable(true);

				imgdetailfile = new File(path, newImgDetailNm);
				prodDetailImg.transferTo(imgdetailfile);

				// 파일 권한 설정(쓰기, 읽기)
				imgdetailfile.setWritable(true);
				imgdetailfile.setReadable(true);

			}

		} catch (Exception e) {
			logger.debug("에러 발생 :: " + e);
		}

		return outDTO;
	}
	
	/**
	 * 상품 이미지 형식에 맞게 변환(대표이미지 o , 상세이미지 x)
	 * 
	 * @param prodImg
	 * @param prodDetailImg
	 * @param productName
	 * @return
	 */
	public ProdImgDTO setImges(MultipartFile prodImg, String productName) {

		ProdImgDTO outDTO = new ProdImgDTO();

		try {
			
			logger.debug("대표 이미지만 있을 경우");

			if (prodImg != null && !CustomUtill.isNullOrEmpty(prodImg.getOriginalFilename())) {
				// 파일을 저장할 세부 경로 지정
				String path = imgDir;
				UUID uuid = UUID.randomUUID();
				File files = new File(path);

				// 디렉터리가 존재하지 않을 경우
				if (!files.exists()) {
					boolean wasSuccessful = files.mkdirs();
					// 디렉터리 생성에 실패했을 경우
					if (!wasSuccessful) {
						logger.debug("file: was not successful");
					}
				}

				// 파일명 중복 피하고자 uuid 설정
				String newImgNm;
				File imgfile;

				newImgNm = uuid + "_" + prodImg.getOriginalFilename();

				outDTO.setProdNm(productName);
				outDTO.setImgName(prodImg.getOriginalFilename());
				outDTO.setImgPath(path + newImgNm);

				// 업로드 한 파일 데이터를 지정한 파일에 저장
				imgfile = new File(path, newImgNm);
				prodImg.transferTo(imgfile);

				// 파일 권한 설정(쓰기, 읽기)
				imgfile.setWritable(true);
				imgfile.setReadable(true);

			}

		} catch (Exception e) {
			logger.debug("에러 발생 :: " + e);
		}

		return outDTO;
	}
	
	/**
	 * 상품 이미지 형식에 맞게 변환(대표이미지 x, 상세이미지 o)
	 * 
	 * @param prodImg
	 * @param prodDetailImg
	 * @param productName
	 * @return
	 */
	public ProdImgDTO setDtImges(MultipartFile prodDetailImg, String productName) {

		ProdImgDTO outDTO = new ProdImgDTO();

		try {
			
			logger.debug("상세 이미지만 있을 경우");

			if (prodDetailImg != null && !CustomUtill.isNullOrEmpty(prodDetailImg.getOriginalFilename())) {
				// 파일을 저장할 세부 경로 지정
				String path = imgDir;
				UUID uuid = UUID.randomUUID();
				File files = new File(path);

				// 디렉터리가 존재하지 않을 경우
				if (!files.exists()) {
					boolean wasSuccessful = files.mkdirs();
					// 디렉터리 생성에 실패했을 경우
					if (!wasSuccessful) {
						logger.debug("file: was not successful");
					}
				}

				// 파일명 중복 피하고자 uuid 설정
				String newImgDetailNm;
				File imgdetailfile;

				logger.debug("오리지널 이름 :: " + prodDetailImg.getOriginalFilename());
				
				newImgDetailNm = uuid + "_" + prodDetailImg.getOriginalFilename();

				outDTO.setProdNm(productName);
				outDTO.setImgDetailNm(prodDetailImg.getOriginalFilename());
				outDTO.setImgDetailPath(path + newImgDetailNm);

				imgdetailfile = new File(path, newImgDetailNm);
				prodDetailImg.transferTo(imgdetailfile);
				
				// 파일 권한 설정(쓰기, 읽기)
				imgdetailfile.setWritable(true);
				imgdetailfile.setReadable(true);
			}

		} catch (Exception e) {
			logger.debug("에러 발생 :: " + e);
		}

		return outDTO;
	}

}
