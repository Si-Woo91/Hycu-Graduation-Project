package com.han.admin.domain;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Entity
public class AdminInfo implements UserDetails{
	
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "adminInfo_seq_gen")
    @SequenceGenerator(name = "adminInfo_seq_gen", sequenceName = "adminInfo_seq", allocationSize = 1)
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String adminId;
	
	@Column(nullable = false)
	private String adminPw;
	
	@Builder
	public AdminInfo(String adminId, String adminPw, Collection<? extends GrantedAuthority> authorities) {
		this.adminId = adminId;
		this.adminPw = adminPw;
	}

	@Override
	public String getUsername(){
		return adminId;
	}
	
	@Override
	public String getPassword(){
		return adminPw;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collection = new ArrayList<>();

        // 모든 회원은 user 권한을 가짐
        collection.add(new SimpleGrantedAuthority("ROLE_USER"));

        // Id가 ROLE_ADMIN인 회원은 admin 권한도 가짐
        if (isAdmin()) {
        	collection.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }

        return collection;
    }
	
	// 관리자 확인
	public boolean isAdmin() {
        return "admin".equals(adminId);
    }

	
	
}
