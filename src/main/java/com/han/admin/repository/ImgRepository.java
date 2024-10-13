package com.han.admin.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.han.admin.domain.ProdImg;

@Repository
public interface ImgRepository extends JpaRepository<ProdImg, Long>{

	Optional<ProdImg> findByProdInfoId(Long Id);
	
}
