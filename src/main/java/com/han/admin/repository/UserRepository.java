package com.han.admin.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.han.admin.domain.UserInfo;

/**
 * 사용자 repository
 * 
 */
@Repository
public interface UserRepository extends JpaRepository<UserInfo, Long>, PagingAndSortingRepository<UserInfo, Long>{
	
	/**
	 * 유저 조회
	 * 
	 * @param userId
	 * @return
	 */
	Optional<UserInfo> findByuserId(String userId);
	
	
    /**
     * 
     *  키워드로 검색하고 페이징 처리
     * @param userNm
     * @param startRow
     * @param endRow
     * @return
     */
    @Query(value = "SELECT * FROM ( " +
                   "SELECT u.*, ROWNUM AS rnum " +
                   "FROM ( " +
                   "SELECT * FROM user_info " +
                   "WHERE user_nm LIKE %:keyword% " +
                   "ORDER BY id " +
                   ") u " +
                   "WHERE ROWNUM <= :endRow) " +
                   "WHERE rnum > :startRow", nativeQuery = true)
    List<UserInfo> findByUserNmContainingWithPagination(@Param("keyword") String keyword,
                                                       @Param("startRow") int startRow,
                                                       @Param("endRow") int endRow);
	
    /**
     * 회원 조회
     * 
     * @param startRow
     * @param endRow
     * @return
     */
    @Query(value = "SELECT * FROM ( " +
            "SELECT u.*, ROWNUM AS rnum " +
            "FROM ( " +
            "SELECT * FROM user_info " +
            "ORDER BY id " +
            ") u " +
            "WHERE ROWNUM <= :endRow) " +
            "WHERE rnum > :startRow", nativeQuery = true)
    List<UserInfo> findUserInfoWithPagination(@Param("startRow") int startRow, @Param("endRow") int endRow);
    
    
    /**
     * 회원 총 수
     * 
     * @return
     */
    @Query("SELECT COUNT(u) FROM UserInfo u")
    long countTotalUsers();
    
    
    /**
     * 검색어에 해당하는 사용자 수 카운트
     * @param keyword
     * @return
     */
    @Query("SELECT COUNT(u) FROM UserInfo u WHERE u.userNm LIKE %:keyword%")
    long countByUserNmContaining(@Param("keyword")String keyword);
    
    
    /**
     * 회원 정보 삭제
     * 
     */
    void deleteAllById(Iterable<? extends Long> ids); 
	



	
}
