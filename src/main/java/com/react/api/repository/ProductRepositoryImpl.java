package com.react.api.repository;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.react.api.dto.PageDto;
import com.react.api.dto.ProductDto;
import com.react.api.entity.Product;
import com.react.api.entity.ProductImage;
import com.react.api.entity.QProduct;
import com.react.api.entity.QProductImage;

import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;

@Repository
@Transactional(readOnly = true)
@Slf4j
public class ProductRepositoryImpl implements ProductRepository {

	private final EntityManager em;
	private final JPAQueryFactory queryFactory;

	public ProductRepositoryImpl(EntityManager em, JPAQueryFactory queryFactory) {
		this.em = em;
		this.queryFactory = new JPAQueryFactory(em);
	}
	
	@Override
	@Transactional
	public void save(ProductDto productDto) {	
		// product
		Product product = Product.builder()
				.name(productDto.getName())
				.pdesc(productDto.getPdesc())
				.price(productDto.getPrice())
				.build();
		
		em.persist(product);
		
		productDto.getUploadedFileNames().forEach(fileName -> {
			ProductImage productImage = ProductImage.builder()
			.product(product)
			.fileName(fileName)
			.build();
			
			em.persist(productImage);
		});	
	}

	@Override
	public List<ProductDto> findAll(PageDto pageDto) {
		QProduct qProduct = QProduct.product;
		QProductImage qProductImage = QProductImage.productImage;
		
		// product
		QueryResults<ProductDto> queryResults = queryFactory
				.select(Projections.constructor(ProductDto.class, qProduct.seq, qProduct.name, qProduct.price,
							ExpressionUtils.as(
									JPAExpressions.select(qProductImage.fileName.min())
												.from(qProductImage)
												.where(qProduct.seq.eq(qProductImage.product.seq))
												.limit(1),
												"file_name"		
									)
							)
						)
				.from(qProduct)
				.offset((pageDto.getPage()-1) * pageDto.getSize())
				.limit(pageDto.getSize())
				.orderBy(qProduct.seq.desc())
				.fetchResults();
		
		int amount = (int)queryResults.getTotal();
		List<ProductDto> productDtos = queryResults.getResults();
		
		// product image
		
		// page
		pageDto.setAmount(amount);
		return productDtos;
	}

	@Override
	public ProductDto findBySeq(Long seq) {
		QProduct qProduct = QProduct.product;
		QProductImage qProductImage = QProductImage.productImage;
		
		// product
		ProductDto productDto = queryFactory
			.select(Projections.fields(ProductDto.class, qProduct.seq, qProduct.name, qProduct.price, qProduct.pdesc))
			.from(qProduct)
			.where(qProduct.seq.eq(seq))
			.fetchOne();
		
		// product image
		List<String> productImageDto = queryFactory
			.select(qProductImage.fileName)
			.from(qProductImage)
			.where(qProductImage.product.seq.eq(seq))
			.fetch();
		
		productDto.setUploadedFileNames(productImageDto);
		
		return productDto;
	}

	@Override
	@Transactional
	public void modify(ProductDto productDto) {
		QProductImage qProductImage = QProductImage.productImage;
		
		// 수정
		Product product = new Product(productDto);
		
		// 1. 이미지 삭제
		log.info(productDto.getDeleteFileNames().toString());
		for(int i = 0; i < productDto.getDeleteFileNames().size(); i++) {
			queryFactory
				.delete(qProductImage)
				.where(qProductImage.product.seq.eq(productDto.getSeq()))
				.execute();
		}
		
		// 2. 이미지 추가
		for(int i = 0; i < productDto.getUploadedFileNames().size(); i++) {
			ProductImage productImage = ProductImage.builder()
												.fileName(productDto.getUploadedFileNames().get(i))
												.product(product)
												.build();
			
			em.persist(productImage);
		}
		
	}

}
