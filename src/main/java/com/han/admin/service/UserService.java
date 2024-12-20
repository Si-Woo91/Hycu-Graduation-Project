package com.han.admin.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.han.admin.domain.UserInfo;
import com.han.admin.dto.UserInfoDTO;
import com.han.admin.repository.UserRepository;
import com.han.admin.utill.CustomUtill;

import lombok.RequiredArgsConstructor;

/**
 * 회원 관련 service
 * 
 */
@RequiredArgsConstructor
@Service
public class UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	private final UserRepository userRepository;
	
	
    /**
     * DTO -> Entity
     * 
     * @param inDto
     * @return
     */
    public UserInfo setEntity(UserInfoDTO inDto) {
    	
    	UserInfo outUser = UserInfo.builder()
    			.userId(inDto.getUserId())
    			.passWord(inDto.getPassWord())	//	비밀번호 암호화
    			.build();
    	
    	return outUser;
    }
    
    /**
     * Entity -> DTO
     * 
     * @param userInfo
     * @return
     */
	public UserInfoDTO mapToDTO(UserInfo userInfo) {
		
		UserInfoDTO inDTO = new UserInfoDTO();
		
	    if (CustomUtill.isNullOrEmpty(userInfo)) {

	    	return null; // 또는 새로운 UserInfoDTO 객체를 반환
	    
	    }
	    else 
	    {
	    	
	    	logger.debug("userid :: " + userInfo.getUserId());
	    	
	        inDTO.setId(userInfo.getId());
	        inDTO.setUserId(userInfo.getUserId());
	        inDTO.setUserNm(userInfo.getUserNm());
	        inDTO.setBirth(userInfo.getBirth());
	        inDTO.setUserSex(userInfo.getUserSex());
	        inDTO.setUserAddress(userInfo.getUserAddress());
	        inDTO.setCreateDate(userInfo.getCreateDate());
	    }
		
		
		return inDTO;
	}
    
    
	/**
	 * 회원 관리 탭 회원조회 및 페이징 처리
	 * 
	 * @param keyword
	 * @param pageable
	 * @return
	 */
	public Page<UserInfoDTO> getCustInfoPage(String keyword, Pageable pageable) {
		
	    int page = pageable.getPageNumber(); // 현재 페이지
	    int size = pageable.getPageSize(); // 페이지당 데이터 수
	    
	    int startRow = page * size; // 시작 행
	    int endRow = startRow + size; // 종료 행
	    
	    List<UserInfo> userList;
	    
	    // 총 데이터 수
	    long totalElements;
	    
	    // 키워드가 없을 경우
	    if(CustomUtill.isNullOrEmpty(keyword)) {
	    	
	    	userList = userRepository.findUserInfoWithPagination(startRow, endRow);
	    	totalElements = userRepository.count();
	    	
	    }
	    // 키워드가 있을 경우
	    else
	    {
	    	
	    	logger.debug("키워드 o");
	    	userList = userRepository.findByUserNmContainingWithPagination(keyword, startRow, endRow);
	    	
	    	totalElements = userRepository.countByUserNmContaining(keyword);
	    	
	    }
	    
	    List<UserInfoDTO> outList = new ArrayList<>();
	    
	    
	    if (CustomUtill.isNullOrEmpty(userList)) {

	    	return null;
	    
	    }
	    else
	    {
		    for(UserInfo inUser : userList) {
		    	
		    	UserInfoDTO inDto = new UserInfoDTO();
		    	
		    	inDto.setId(inUser.getId());
		    	inDto.setUserId(inUser.getUserId());
		    	inDto.setUserNm(inUser.getUserNm());
		    	inDto.setBirth(inUser.getBirth());
		        inDto.setUserSex(inUser.getUserSex());
		        inDto.setUserAddress(inUser.getUserAddress());
		        inDto.setCreateDate(inUser.getCreateDate());
		    	
		        outList.add(inDto);
		    	
		    }
	    }
	    
	    return new PageImpl<>(outList, pageable, totalElements); // Page 객체 반환
	}
	
	
	/**
	 * 회원 삭제
	 * 
	 * @param ids
	 * @return
	 */
	public boolean deleteUsers(List<Long> ids) {
		
		logger.debug("회원 삭제 서비스");
		
        try {
        	
            userRepository.deleteAllById(ids); // ID에 해당하는 사용자 삭제
            return true;
            
        } catch (Exception e) {
            
            return false;
  
        }
		
	}
	


    
	
	
    
}
