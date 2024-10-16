package com.han.admin.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.han.admin.domain.ProdInfo;
import java.util.List;

/**
 * 상품 repository
 * 
 */
@Repository
public interface ProdRepository extends JpaRepository<ProdInfo, Long>{

	/**
	 * 상품 조회
	 * 
	 */
	Optional<ProdInfo> findById(Long id);
	
	/**
	 * 키워드로 검색하고 페이징 처리
	 * 
	 * @param keyword
	 * @param startRow
	 * @param endRow
	 * @return
	 */
    @Query(value = "SELECT * FROM ( " +
            "SELECT p.*, ROWNUM AS rnum " +
            "FROM ( " +
            "SELECT * FROM prod_info " +
            "WHERE prod_nm LIKE %:keyword% " +
            "ORDER BY id " +
            ") p " +
            "WHERE ROWNUM <= :endRow) " +
            "WHERE rnum > :startRow", nativeQuery = true)
	List<ProdInfo> findByProdNmContainingWithPagination(@Param("keyword") String keyword,
											            @Param("startRow") int startRow,
											            @Param("endRow") int endRow);
												
	/**
	 * 상품조회 및 페이징 처리
	 * 
	 * @param startRow
	 * @param endRow
	 * @return
	 */
    @Query(value = "SELECT * FROM ( " +
            "SELECT p.*, ROWNUM AS rnum " +
            "FROM ( " +
            "SELECT * FROM prod_info " +
            "ORDER BY id " +
            ") p " +
            "WHERE ROWNUM <= :endRow) " +
            "WHERE rnum > :startRow", nativeQuery = true)
    List<ProdInfo> findProdInfoWithPagination(@Param("startRow") int startRow, @Param("endRow") int endRow);
    
    /**
     * 상품 갯 수 조회
     * 
     * @return
     */
    @Query("SELECT COUNT(p) FROM ProdInfo p")
    long countTotalUsers();
    
    /**
     * 검색어에 해당하는 사용자 수 카운트
     * @param keyword
     * @return
     */
    @Query("SELECT COUNT(p) FROM ProdInfo p WHERE p.prodNm LIKE %:keyword%")
    long countByUserNmContaining(@Param("keyword")String keyword);
    
    
}
