package com.react.api.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductImage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long seq;
	private String fileName;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "seq_product")
	private Product product;

	@Builder
	public ProductImage(Long seq, String fileName, Product product) {
		super();
		this.seq = seq;
		this.fileName = fileName;
		this.product = product;
	}

}
