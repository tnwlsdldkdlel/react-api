package com.react.api.repository;

import java.util.List;

import com.react.api.dto.PageDto;
import com.react.api.dto.ProductDto;

public interface ProductRepository {

	public void save(ProductDto productDto);

	public List<ProductDto> findAll(PageDto pageDto);
	
	public ProductDto findBySeq(Long seq);

	public void modify(ProductDto productDto);
}
