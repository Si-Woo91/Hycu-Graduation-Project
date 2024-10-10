package com.han.admin.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
	private String prodNm;

	@NotNull
	private String prodCd;

	@NotNull
	private int prdPrc; // 상품 가격

	@NotNull
	private int prdQnt; // 상품 수량

	@OneToMany(mappedBy = "prodInfo", cascade = CascadeType.ALL)
	private List<ProdImg> prodImgList;

	@Builder
	public ProdInfo(Long id, String prodType, String prodNm, String prodCd, int prodPrice, int produtQuantity,
			List<ProdImg> prodImgList) {

		this.id = id;
		this.prodType = prodType;
		this.prodNm = prodNm;
		this.prodCd = prodCd;
		this.prdPrc = prodPrice;
		this.prdQnt = produtQuantity;
		this.prodImgList = prodImgList;
	}

	public void addIntmImg(ProdImg prodImg) {
		if (prodImgList == null) {
			prodImgList = new ArrayList<>();
		}
		prodImgList.add(prodImg);
		prodImg.setProdInfo(this);
	}

}
