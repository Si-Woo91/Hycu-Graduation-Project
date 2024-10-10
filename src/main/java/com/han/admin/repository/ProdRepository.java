package com.han.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.han.admin.domain.ProdInfo;

@Repository
public interface ProdRepository extends JpaRepository<ProdInfo, Long>{

}
