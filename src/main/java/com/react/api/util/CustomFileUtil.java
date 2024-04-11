package com.react.api.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnails;

@Component
@Log4j2
@RequiredArgsConstructor
public class CustomFileUtil {

	@Value("${spring.servlet.multipart.location}")
	private String uploadPath;

	// 프로젝트 실행되면 생성.
	@PostConstruct
	public void init() {
		File tempFolder = new File(uploadPath);

		if (!tempFolder.exists()) {
			tempFolder.mkdir();
		}

		uploadPath = tempFolder.getAbsolutePath();

		log.info("==================================");
		log.info(uploadPath);

	}

	public List<String> saveFiles(List<MultipartFile> files) throws RuntimeException {

		if (files == null || files.size() == 0) {
			// return List.of(); // 비어있는 배열 리턴.
			return null;
		}

		List<String> uploadNames = new ArrayList<>();

		for (MultipartFile file : files) {
			String savedName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
			Path savePath = Paths.get(uploadPath, savedName); // uploadPath에 savedName으로 변경.

			try {
				Files.copy(file.getInputStream(), savePath); // 원본파일 업로드.

				String contentType = file.getContentType();
				if (contentType != null || contentType.startsWith("image")) {
					Path thumbmailPath = Paths.get(uploadPath, "s_" + savedName);

					Thumbnails.of(savePath.toFile()).size(200, 200).toFile(thumbmailPath.toFile()); // 200x200 으로 생성.
				}

				uploadNames.add(savedName);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		return uploadNames;
	}

	public ResponseEntity<FileSystemResource> getFile(String fileName) {
		FileSystemResource fileSystemResource = new FileSystemResource(uploadPath + File.separator + fileName);

		if (!fileSystemResource.isReadable()) {
			fileSystemResource = new FileSystemResource(uploadPath + File.separator + "default.jpg");
		}

		HttpHeaders httpHeaders = new HttpHeaders();
		try {
			httpHeaders.add("content-type", Files.probeContentType(fileSystemResource.getFile().toPath()));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return ResponseEntity.ok().headers(httpHeaders).body(fileSystemResource);
	}

	public void deleteFiles(List<String> fileNames) {
		if (fileNames == null || fileNames.size() == 0) {
			return;
		}

		fileNames.forEach(fileName -> {
			// 썸네일 삭제
			String thumbnailFileName = "s_" + fileName;

			Path thumbPath = Paths.get(uploadPath, thumbnailFileName);
			Path filePath = Paths.get(uploadPath + fileName);

			try {
				Files.deleteIfExists(filePath);
				Files.deleteIfExists(thumbPath);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		});
	}

}
