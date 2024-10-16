package com.han.admin.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.han.admin.domain.AdminInfo;

/**
 * 관리자 repository
 * 
 */
@Repository
public interface AdminRepository  extends JpaRepository<AdminInfo, Long>{
	
	Optional<AdminInfo> findByAdminId(String adminId);
}
