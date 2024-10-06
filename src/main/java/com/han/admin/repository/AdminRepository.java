package com.han.admin.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.han.admin.domain.AdminInfo;

public interface AdminRepository  extends JpaRepository<AdminInfo, Long>{
	
	Optional<AdminInfo> findByAdminId(String adminId);
}
