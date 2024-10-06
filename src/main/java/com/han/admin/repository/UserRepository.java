package com.han.admin.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.han.admin.domain.UserInfo;

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
                   "WHERE user_nm LIKE %:userNm% " +
                   "ORDER BY user_id " +
                   ") u " +
                   "WHERE ROWNUM <= :endRow) " +
                   "WHERE rnum > :startRow", nativeQuery = true)
    List<UserInfo> findByUserNmContainingWithPagination(@Param("userNm") String userNm,
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
            "ORDER BY user_id " +
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
     * 회원 정보 삭제
     * 
     */
    void deleteAllById(Iterable<? extends Long> ids); 
	



	
}
