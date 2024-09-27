package com.han.admin.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import com.han.admin.domain.UserInfo;
import com.han.admin.repository.UserRepository;
import com.han.admin.utill.CustomStringUtill;


/**
 * 시큐리티 로그인시 사용
 * 
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomUserDetailsService implements UserDetailsService{

	private final UserRepository userRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);
	
	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		logger.debug("로그인 ID 확인 ::" + userId);
		
		UserInfo userData = userRepository.findByuserId(userId).orElseThrow(() -> new UsernameNotFoundException("userId(%s) not found".formatted(userId)));
		
		logger.debug("userData :: " + CustomStringUtill.toString(userData));
		logger.debug("권한 :: " + userData.getAuthorities());
		return new User(userData.getUsername(), userData.getPassword(), userData.getAuthorities());
		
	}
	
}
