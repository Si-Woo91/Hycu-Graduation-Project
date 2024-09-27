package com.han.admin.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.han.admin.domain.UserInfo;

@Repository
public interface UserRepository extends JpaRepository<UserInfo, Long>{
	 
	Optional<UserInfo> findByuserId(String userId);
	
	
}
