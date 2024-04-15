package com.react.api.controller;

import java.util.List;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.react.api.dto.PageDto;
import com.react.api.dto.ProductDto;
import com.react.api.dto.ResultDto;
import com.react.api.repository.ProductRepository;
import com.react.api.util.CustomFileUtil;
import com.react.api.util.ResponseCode;
import com.react.api.util.Util;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/v1/products")
public class ProductController {

	private final CustomFileUtil fileUtil;
	private final ProductRepository productRepository;

	@PostMapping("")
	public ResultDto<ProductDto> save(ProductDto productDto) {
		try {
			List<MultipartFile> files = productDto.getFiles();
			List<String> uploadedFileNames = fileUtil.saveFiles(files);
			productDto.setUploadedFileNames(uploadedFileNames);

			productRepository.save(productDto);

			return ResultDto.success(null, ResponseCode.READ_SUCCESS.getMessage());
		} catch (Exception e) {
			log.error(Util.getPrintStackTrace(e));
			return ResultDto.fail(ResponseCode.INTERNAL_SERVER_ERROR, null);
		}
	}

	@GetMapping("/view/{fileName}")
	public ResponseEntity<FileSystemResource> viewFileGET(@PathVariable("fileName") String fileName) {
		return fileUtil.getFile(fileName);
	}

	@GetMapping("/{seq}")
	public ResultDto<ProductDto> getOne(@PathVariable(name = "seq") Long seq) {
		ProductDto productDto = productRepository.findBySeq(seq);
		return ResultDto.success(productDto, ResponseCode.READ_SUCCESS.getMessage());
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping("")
	public ResultDto<List<ProductDto>> get(PageDto pageDto) {
		try {
			List<ProductDto> productDtos = productRepository.findAll(pageDto);
			return ResultDto.success(productDtos, ResponseCode.READ_SUCCESS.getMessage(), pageDto);
		} catch (Exception e) {
			log.error(Util.getPrintStackTrace(e));
			return ResultDto.fail(ResponseCode.INTERNAL_SERVER_ERROR, null);
		}
	}

	@PutMapping("")
	public ResultDto<ProductDto> put(ProductDto productDto) {
		try {
			// image file 삭제
			if (productDto.getUploadedFileNames().size() != 0) {
				fileUtil.deleteFiles(productDto.getUploadedFileNames());
			}

			// image 추가
			log.info(productDto.getFiles());
			if (productDto.getFiles().size() != 0) {
				List<MultipartFile> files = productDto.getFiles();
				List<String> uploadedFileNames = fileUtil.saveFiles(files);
				productDto.setUploadedFileNames(uploadedFileNames);
			}

			// db 수정
			productRepository.modify(productDto);
			return ResultDto.success(null, ResponseCode.READ_SUCCESS.getMessage());
		} catch (Exception e) {
			return ResultDto.fail(ResponseCode.INTERNAL_SERVER_ERROR, null);
		}
	}

}
