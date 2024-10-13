package com.han.admin.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.han.admin.domain.ProdInfo;
import java.util.List;


@Repository
public interface ProdRepository extends JpaRepository<ProdInfo, Long>{

	Optional<ProdInfo> findById(Long id);
}
