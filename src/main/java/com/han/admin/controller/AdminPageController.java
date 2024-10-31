package com.han.admin.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.han.admin.dto.ProdImgDTO;
import com.han.admin.dto.ProdInfoDTO;
import com.han.admin.dto.ResponseMessageDTO;
import com.han.admin.dto.UserInfoDTO;
import com.han.admin.service.ConvertImgFormatService;
import com.han.admin.service.ProdService;
import com.han.admin.service.UserService;
import com.han.admin.utill.CustomUtill;

import org.springframework.ui.Model;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class AdminPageController {

	private static final Logger logger = LoggerFactory.getLogger(AdminPageController.class);

	private final UserService userService;

	private final ConvertImgFormatService convertImgFormatService;

	private final ProdService prodService;

	/* getMapping Start */

	/**
	 * 사용자 관리 페이지
	 * 
	 * @param keyword
	 * @param page
	 * @param size
	 * @param tab
	 * @param model
	 * @return
	 */
	@GetMapping(value = "/userList")
	public String getUsersMNG(@RequestParam(name = "keyword", required = false) String keyword,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "10") int size,
			Model model) {

		// 페이징 처리 관련
		Pageable pageable = PageRequest.of(page, size);
		Page<UserInfoDTO> userInfoPage = userService.getCustInfoPage(keyword, pageable);

		// 사용자 정보가 없을 경우 페이지 처리를 위한 추가 로직
		boolean noUsers;
		
		if (CustomUtill.isNullOrEmpty(userInfoPage.getContent())) 
		{
		
			noUsers = true;

		} 
		else 
		{

			noUsers = false;

		}

		logger.debug("noUsers :: " + noUsers);

		model.addAttribute("noUsers", noUsers);
		model.addAttribute("userInfoPage", userInfoPage.getContent());
		model.addAttribute("currentPage", userInfoPage.getNumber());
		model.addAttribute("totalPages", userInfoPage.getTotalPages());
		model.addAttribute("keyword", keyword);

		return "admin/userMNG";
	}
	
	/**
	 * 회원 검색 기능
	 * 
	 * @param keyword
	 * @param page
	 * @param size
	 * @param model
	 * @return
	 */
	@GetMapping(value = "/searchUsers")
	public String getSearchUsers(@RequestParam(name = "keyword", required = false) String keyword,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "10") int size,
			Model model) {

		logger.debug("검색 기능");
		logger.debug("키워드  ::: " + keyword);
		
		// 페이징 처리
		Pageable pageable = PageRequest.of(page, size);
		logger.debug("pageable :: " + pageable.getPageSize());
		
		Page<UserInfoDTO> userInfoPage = userService.getCustInfoPage(keyword, pageable);

		logger.debug(" userInfoPage.getTotalPages() :: " +  userInfoPage.getTotalPages());

		// 사용자 정보가 없을 경우 페이지 처리를 위한 추가 로직
		boolean noUsers;
		
		if (CustomUtill.isNullOrEmpty(userInfoPage.getContent())) 
		{
			
			noUsers = true;

		} 
		else 
		{

			noUsers = false;
			
		}

		logger.debug("noUsers :: " + noUsers);

		model.addAttribute("noUsers", noUsers);
		model.addAttribute("userInfoPage", userInfoPage.getContent());
		model.addAttribute("currentPage", userInfoPage.getNumber());
		model.addAttribute("totalPages", userInfoPage.getTotalPages());
		model.addAttribute("keyword", keyword);

		return "admin/userMNG :: #content";
	}

	/**
	 * 상품관리 페이지
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping(value = "/prodList")
	public String getProdMNG(@RequestParam(name = "keyword", required = false) String keyword,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "5") int size,
			Model model) {

		Pageable pageable = PageRequest.of(page, size);
		logger.debug("pageable :: " + pageable.getPageSize());
		
		Page<ProdInfoDTO> prodInfoPage = prodService.getProdInfoPage(keyword, pageable);

		// 상품 정보가 없을 경우 페이지 처리를 위한 추가 로직
		boolean noProds;
		
		if (CustomUtill.isNullOrEmpty(prodInfoPage.getContent())) 
		{
			
			noProds = true;
		
		}
		else 
		{
		
			noProds = false;	
		
		}

		logger.debug("noUsers :: " + noProds);

		logger.debug("prodInfoPage.getContent() :: " + prodInfoPage.getContent().get(0).getProdNm());
		logger.debug("prodInfoPage.getContent() :: " + prodInfoPage.getContent().get(0).getProdCd());
		logger.debug("prodInfoPage.getContent() :: " + prodInfoPage.getContent().get(0).getProdType());

		model.addAttribute("noProds", noProds);
		model.addAttribute("prodInfoPage", prodInfoPage.getContent());
		model.addAttribute("currentPage", prodInfoPage.getNumber());
		model.addAttribute("totalPages", prodInfoPage.getTotalPages());
		model.addAttribute("keyword", keyword);

		return "admin/prodMNG";
	}
	
	/**
	 * 상품 검색
	 * 
	 * @param keyword
	 * @param page
	 * @param size
	 * @param model
	 * @return
	 */
	@GetMapping(value = "/searchProds")
	public String getSearchPrdos(@RequestParam(name = "keyword", required = false) String keyword,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "5") int size,
			Model model) {

		logger.debug("검색 기능");
		logger.debug("키워드  ::: " + keyword);
		
		Pageable pageable = PageRequest.of(page, size);
		logger.debug("pageable :: " + pageable.getPageSize());
		
		Page<ProdInfoDTO> prodInfoPage = prodService.getProdInfoPage(keyword, pageable);

		logger.debug(" prodInfoPage.getTotalPages() :: " +  prodInfoPage.getTotalPages());

		boolean noProds;
		
		if (CustomUtill.isNullOrEmpty(prodInfoPage.getContent())) 
		{
			noProds = true;

		} 
		else 
		{
			noProds = false;
		}

		logger.debug("noUsers :: " + noProds);

		model.addAttribute("noProds", noProds);
		model.addAttribute("prodInfoPage", prodInfoPage.getContent());
		model.addAttribute("currentPage", prodInfoPage.getNumber());
		model.addAttribute("totalPages", prodInfoPage.getTotalPages());
		model.addAttribute("keyword", keyword);

		return "admin/prodMNG :: #content";
	}

	/**
	 * 상품 등록 모달
	 * 
	 * @return
	 */
	@GetMapping(value = "/modal/regModal")
	public String getRegModal() {

		logger.debug("확인");
		return "modal/regModal";
	}

	/**
	 * 상품 상세보기
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@GetMapping(value = "/modal/detailModal/{id}")
	public String getDetailModal(@PathVariable("id") Long id, Model model) {

		logger.debug("확인");

		ProdInfoDTO inProdInfo = prodService.getProductById(id);

		logger.debug("이미지 :: " + inProdInfo.getProdImgs().getImgPath());
		logger.debug("상세 :: " + inProdInfo.getProdImgs().getImgDetailPath());

		model.addAttribute("detailProdInfo", inProdInfo);

		return "modal/detailModal";
	}
	/* getMapping End */

//	---------------------------------------------------------------------------------------

	/* postMapping Start */
	
	/**
	 * 회원 삭제
	 * 
	 * @param reqInDto
	 * @return
	 */
	@PostMapping(value = "/deleteUsers")
	public ResponseEntity<?> deleteUsers(@RequestBody List<UserInfoDTO> reqInDto) {

		logger.debug("사용자 삭제 컨트롤러");

		// 리스트에 받아온 id값을 저장
		List<Long> ids = reqInDto.stream().map(UserInfoDTO::getId).toList();

		if (ids == null || ids.isEmpty()) 
		{
			return ResponseEntity.badRequest().body(new ResponseMessageDTO(false, "사용자 ID가 필요합니다."));
		}

		boolean success = userService.deleteUsers(ids);

		if (success) 
		{

			return ResponseEntity.ok(new ResponseMessageDTO(true, "사용자가 성공적으로 삭제되었습니다."));

		} 
		else 
		{

			return ResponseEntity.status(500).body(new ResponseMessageDTO(false, "사용자 삭제에 실패했습니다."));

		}
	}

	/**
	 * 상품등록
	 * 
	 * @param productName
	 * @param productCode
	 * @param productPrice
	 * @param prodImg
	 * @param prodDetailImg
	 * @return
	 */
	@PostMapping("/saveProduct")
	public ResponseEntity<String> saveProduct(@RequestParam("productType") String productType,
			@RequestParam("productName") String productName, @RequestParam("productCode") String productCode,
			@RequestParam("productPrice") int productPrice, @RequestParam("productQuantity") int productQuantity,
			@RequestParam("prodImg") MultipartFile prodImg,
			@RequestParam("prodDetailImg") MultipartFile prodDetailImg) {

		// 이미지 파일 세팅
		ProdImgDTO inImgDTO = convertImgFormatService.setAllImges(prodImg, prodDetailImg, productName);

		// 상품 dto 세팅
		ProdInfoDTO inProdDTO = prodService.setProdDTO(productType, productName, productCode, productPrice,
				productQuantity);

		// 상품 및 이미지 저장
		prodService.saveProd(inProdDTO, inImgDTO);

		return ResponseEntity.ok("상품이 등록되었습니다.");
	}

	/**
	 * 상품 삭제
	 * 
	 * @param reqInDto
	 * @return
	 */
	@PostMapping(value = "/deleteProd")
	public ResponseEntity<?> deleteProds(@RequestBody List<ProdInfoDTO> reqInDto) {

		logger.debug("상품 삭제 controller");

		List<Long> ids = reqInDto.stream().map(ProdInfoDTO::getId).toList();

		if (ids == null || ids.isEmpty())
		{
			return ResponseEntity.badRequest().body(new ResponseMessageDTO(false, "상품 ID가 필요합니다."));
		}

		boolean success = prodService.deleteProds(ids);

		if (success)
		{

			return ResponseEntity.ok(new ResponseMessageDTO(true, "상품이 성공적으로 삭제되었습니다."));

		} 
		else
		{

			return ResponseEntity.status(500).body(new ResponseMessageDTO(false, "상품 삭제에 실패했습니다."));

		}

	}

	/**
	 * 상품 수정
	 * 
	 * @param prodId
	 * @param prodType
	 * @param prodNm
	 * @param prodCd
	 * @param prodPrice
	 * @param productQuantity
	 * @param prodImg
	 * @param prodDetailImg
	 * @return
	 */
	@PostMapping("/updateProd")
	public ResponseEntity<String> updateProd(@RequestParam("prodId") Long prodId
											, @RequestParam("prodType") String prodType
											, @RequestParam("prodNm") String prodNm
											, @RequestParam("prodCd") String prodCd
											, @RequestParam("prodPrice") int prodPrice
											, @RequestParam("productQuantity") int productQuantity
											, @RequestParam(value = "prodImg", required = false) MultipartFile prodImg
											, @RequestParam(value = "prodDetailImg", required = false) MultipartFile prodDetailImg) {

		try {
			
			logger.debug("상품 업데이트 controller");
			
			// dto 상품 정보 세팅
			ProdInfoDTO inProdDTO = prodService.setProdDTO2(prodId, prodType, prodNm, prodCd, prodPrice, productQuantity);
			
			// 이미지 dto 세팅
			ProdImgDTO inImgDTO = new ProdImgDTO();
			
			// prodImg은 바꿈 prodDetailImg은 안바꿈
			if(!CustomUtill.isNullOrEmpty(prodImg) && CustomUtill.isNullOrEmpty(prodDetailImg)) 
			{
				
				inImgDTO = convertImgFormatService.setImges(prodImg, prodNm);
				
			}
			
			// prodImg은 안바꿈 prodDetailImg은 바꿈
			else if (CustomUtill.isNullOrEmpty(prodImg) && !CustomUtill.isNullOrEmpty(prodDetailImg)) 
			{
				
				inImgDTO = convertImgFormatService.setDtImges(prodDetailImg, prodNm);
				
			}
			
			// 둘다 바꿀 경우
			else if(!CustomUtill.isNullOrEmpty(prodImg) && !CustomUtill.isNullOrEmpty(prodDetailImg))
			{
				
				inImgDTO = convertImgFormatService.setAllImges(prodImg, prodDetailImg, prodNm);
				
			}
			
			prodService.updateProd(inProdDTO, inImgDTO);
			

			return ResponseEntity.ok("상품 정보가 업데이트되었습니다.");
			
		} 
		catch (Exception e) 
		{
			return ResponseEntity.status(500).body("상품 정보 업데이트 중 오류가 발생했습니다.");
		}

	}

	/* postMapping End */

}