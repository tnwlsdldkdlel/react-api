package com.react.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ProductImageDto {
	
	private String fileName;
	private String ord;

}
