package com.han.admin.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.han.admin.domain.UserInfo;
import com.han.admin.dto.UserInfoDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	
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
}
