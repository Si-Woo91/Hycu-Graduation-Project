package com.han.admin.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
public class ProdImg {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	@NotNull
	private String prodNm;

	@NotNull
	private String imgNm;

	@NotNull
	private String dtImglNm; // 상세 이미지 이름

	@NotNull
	private String imgPath;

	@NotNull
	private String dtImgPath;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "prod_info_id")
	private ProdInfo prodInfo;

	@Builder
	public ProdImg(Long id, String prodNm, String imgName, String imgDetailNm, String imgPath, String imgDetailPath,
			ProdInfo productInfo) {

		this.id = id;
		this.prodNm = prodNm;
		this.imgNm = imgName;
		this.dtImglNm = imgDetailNm;
		this.imgPath = imgPath;
		this.dtImgPath = imgDetailPath;
		this.prodInfo = productInfo;
	}

}