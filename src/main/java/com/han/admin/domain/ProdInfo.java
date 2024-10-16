package com.han.admin.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ProdInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@NotNull
	private String prodType;

	@NotNull
	@Column(unique = true)
	private String prodNm;

	@NotNull
	@Column(unique = true)
	private String prodCd;

	@NotNull
	private int prodPrc; // 상품 가격

	@NotNull
	private int prodQnt; // 상품 수량

	@OneToOne(mappedBy = "prodInfo", cascade = CascadeType.ALL)
	private ProdImg prodImg;

	@Builder
	public ProdInfo(Long id, String prodType, String prodNm, String prodCd, int prodPrc, int prodQnt,
			ProdImg prodImg) {

		this.id = id;
		this.prodType = prodType;
		this.prodNm = prodNm;
		this.prodCd = prodCd;
		this.prodPrc = prodPrc;
		this.prodQnt = prodQnt;
		this.prodImg = prodImg;
	}

	public void addIntmImg(ProdImg prodImg) {
	    this.prodImg = prodImg;  // 단일 이미지 설정
	    prodImg.setProdInfo(this);  // 양방향 관계 설정
	}

	
	/**
	 * 상품 수정
	 * 
	 */
	public void changeProdInfo(String prodType, String prodNm, String prodCd, 
			int prodPrc, int prodQnt, ProdImg prodImg) {
		
		/**
		 * 기존의 값과 새로 들어온 값을 비교해서 저장
		 * 
		 */
	    if (!this.prodType.equals(prodType)) {
	        this.prodType = prodType;
	    }

	    if (!this.prodNm.equals(prodNm)) {
	        this.prodNm = prodNm;
	    }

	    if (!this.prodCd.equals(prodCd)) {
	        this.prodCd = prodCd;
	    }

	    if (this.prodPrc != prodPrc) {
	        this.prodPrc = prodPrc;
	    }

	    if (this.prodQnt != prodQnt) {
	        this.prodQnt = prodQnt;
	    }

	    if (this.prodImg != prodImg) {
	        this.prodImg = prodImg;
	    }
		
	}
	
}
