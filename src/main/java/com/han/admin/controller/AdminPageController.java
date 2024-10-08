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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.han.admin.dto.ResponseMessageDTO;
import com.han.admin.dto.UserInfoDTO;
import com.han.admin.service.UserService;
import com.han.admin.utill.CustomUtill;

import org.springframework.ui.Model;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class AdminPageController {

	private static final Logger logger = LoggerFactory.getLogger(AdminPageController.class);

	private final UserService userService;

	
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
			@RequestParam(name = "tab", required = false) String tab, Model model) {

		Pageable pageable = PageRequest.of(page, size);
		Page<UserInfoDTO> userInfoPage = userService.getCustInfoPage(keyword, pageable);

		logger.debug("tab :: " + tab);
		
		
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
		model.addAttribute("tab", tab);
		

		return "admin/userMNG";
	}
	
	/**
	 * 상품관리 페이지
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping(value = "/prodList")
	public String getProdMNG( Model model) {

		

		return "admin/prodMNG";
	}
	
	/**
	 * qna관리 페이지
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping(value = "/qnaList")
	public String getQNAMNG( Model model) {

		return "admin/qnaMNG";
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
		
		
		if (ids == null || ids.isEmpty()) {
			return ResponseEntity.badRequest().body(new ResponseMessageDTO(false, "사용자 ID가 필요합니다."));
		}

		boolean success = userService.deleteUsers(ids);

		if (success) {
			
			return ResponseEntity.ok(new ResponseMessageDTO(true, "사용자가 성공적으로 삭제되었습니다."));
		
		} else {
		
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
	@PostMapping("/admin/saveProduct")
	public ResponseEntity<String> saveProduct(
	        @RequestParam("productName") String productName
	        , @RequestParam("productCode") String productCode
	        , @RequestParam("productPrice") Double productPrice
	        , @RequestParam("prodImg") MultipartFile prodImg
	        , @RequestParam("prodDetailImg") MultipartFile prodDetailImg) {

		
		
		
	    return ResponseEntity.ok("상품이 등록되었습니다.");
	}
	
	
	/* postMapping End */ 
	
	
}