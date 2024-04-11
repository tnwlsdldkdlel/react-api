package com.react.api.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

@Data
@ToString
@NoArgsConstructor
@Log4j2
public class ProductDto {

	private Long seq;

	private String name;

	private int price;

	private String pdesc;

	private boolean delFlag;

	private List<MultipartFile> files = new ArrayList<>();

	private List<String> uploadedFileNames = new ArrayList<>();
	
	private List<String> deleteFileNames = new ArrayList<>();

	@Builder
	public ProductDto(Long seq, String name, int price, String pdesc, boolean delFlag, List<MultipartFile> files,
			List<String> uploadedFileNames) {
		super();
		this.seq = seq;
		this.name = name;
		this.price = price;
		this.pdesc = pdesc;
		this.delFlag = delFlag;
		this.files = files;
		this.uploadedFileNames = uploadedFileNames;
	}

	public ProductDto(Long seq, String name, int price, String fileName) {
		super();
		this.seq = seq;
		this.name = name;
		this.price = price;
		
		this.uploadedFileNames.add(fileName);
	}

}
