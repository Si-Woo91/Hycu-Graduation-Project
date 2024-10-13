package com.han.admin.controller;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.han.admin.domain.ProdImg;
import com.han.admin.domain.ProdInfo;
import com.han.admin.service.ConvertImgFormatService;
import com.han.admin.service.ProdImgService;
import com.han.admin.utill.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Controller
public class ImgPathController {

	private static final Logger logger = LoggerFactory.getLogger(ImgPathController.class);
	
	private final  ProdImgService prodImgService;
	
	@Value("${img.dir}")
	private String imgDir;
	
	
	/**
	 * 상품 대표 이미지 url
	 * 
	 * @param id
	 * @return
	 * @throws IOException
	 */
	@GetMapping("/prodImg/{id}")
	@ResponseBody
	public Resource getImage(@PathVariable("id") Long id) throws IOException {
		
		logger.debug("id :: " + id);
		
		logger.debug("상품 이미지 url 컨트롤러");
		
		Optional<ProdImg> prodImgs = prodImgService.findByProdInfo(id);
		
		return new UrlResource("file:" + prodImgs.get().getImgPath());


	}
	
	/**
	 * 상품 상세 이미지 url
	 * 
	 * @param id
	 * @return
	 * @throws IOException
	 */
	@GetMapping("/prodDtImg/{id}")
	@ResponseBody
	public Resource getDtImage(@PathVariable("id") Long id) throws IOException {
		
		logger.debug("id :: " + id);
		logger.debug("상품 이미지 url 컨트롤러");
		
		Optional<ProdImg> prodDtImg = prodImgService.findByProdInfo(id);
		
		return new UrlResource("file:" + prodDtImg.get().getDtImgPath());


	}

	
}
