package com.react.api.entity;

import java.util.ArrayList;
import java.util.List;

import com.react.api.dto.ProductDto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = "imageList") // 데이블 2번조회. 기본적으로 lazy로딩이기 때문에 한번에 가져오지않음. toString으로 설정 시 다시 가져옴.
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long seq;

	private String name;

	private String pdesc;

	private boolean delFlag;

	private int price;

	// 값 타입 컬렉션
	// 항상 부모와 함께 저장되고 삭제됨.
	// 식별자가 없으므로 값 변경 시 전체 삭제 후 새로 추가.
	// FetchType.LAZY
	// 상품과 상품이미지 같이 관리하기 위해서.

	// onetomany는 다른 엔티티에 의해 관리될 수 있고 id만으로 연관관계를 맺음.
//	@ElementCollection
//	@CollectionTable(name = "product_image", joinColumns = @JoinColumn(name = "seq_product"))
//	private List<ProductImage> imageList = new ArrayList<>();
	
	@OneToMany(mappedBy = "product")
	private List<ProductImage> imageList = new ArrayList<>();

	public void changePrice(int price) {
		this.price = price;
	}

	public void changePdesc(String pdesc) {
		this.pdesc = pdesc;
	}

	public void changeName(String name) {
		this.name = name;
	}

	public void addImage(ProductImage image) {
		imageList.add(image);
	}

	public void addImageString(String fileName) {
		ProductImage productImage = ProductImage.builder().fileName(fileName).build();

		addImage(productImage);
	}

	public void clearList() {
		this.imageList.clear();
	}

	@Builder
	public Product(Long seq, String name, String pdesc, boolean delFlag, int price, List<ProductImage> imageList) {
		this.seq = seq;
		this.name = name;
		this.pdesc = pdesc;
		this.delFlag = delFlag;
		this.price = price;
		this.imageList = imageList;
	}
	
	public Product(ProductDto productDto) {
		this.name = productDto.getName();
		this.pdesc = productDto.getPdesc();
		this.delFlag = productDto.isDelFlag();
		this.price = productDto.getPrice();
	}

}
