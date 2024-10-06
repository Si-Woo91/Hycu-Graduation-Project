package com.han.admin.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserInfoDTO {

	Long id;

	// 유저id
	String userId;
	
	// 비밀번호
	String passWord;
	
	// 유저 이름
	String userNm;
	
	// 생년월일
	String birth;
	
	// 성별
	String userSex;
	
	// 주소
	String userAddress;
	
	// 가입일자
	LocalDateTime createDate;
}
