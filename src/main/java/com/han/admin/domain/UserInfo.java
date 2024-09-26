//package com.han.admin.domain;
//
//import lombok.AccessLevel;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//import java.util.ArrayList;
//import java.util.Collection;
//
//import org.springframework.data.jpa.domain.support.AuditingEntityListener;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.EntityListeners;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.SequenceGenerator;
//
//@Getter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@EntityListeners(AuditingEntityListener.class)
//@Entity
//public class UserInfo implements UserDetails{
//
//	@Id
//	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
//	@SequenceGenerator(name = "user_sequence", sequenceName = "user_seq", allocationSize = 1)
//	private Long id;
//	
//	@Column(nullable = false, unique = true)
//	private String userId;
//	
//	@Column(nullable = false)
//	private String passWord;
//	
//	public UserInfo(String userId, String passWord) {
//		this.userId = userId;
//		this.passWord = passWord;
//	}
//
//	@Override
//	public String getPassword() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public String getUsername() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Collection<? extends GrantedAuthority> getAuthorities() {
//		Collection<GrantedAuthority> collection = new ArrayList<>();
//
//        // 모든 회원은 user 권한을 가짐
//        collection.add(new SimpleGrantedAuthority("ROLE_USER"));
//
//        // Id가 ROLE_ADMIN인 회원은 admin 권한도 가짐
//        if (isAdmin()) {
//        	collection.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
//        }
//
//        return collection;
//    }
//	
//	// 관리자 확인
//	public boolean isAdmin() {
//        return "admin".equals(userId);
//    }
//
//
//}
