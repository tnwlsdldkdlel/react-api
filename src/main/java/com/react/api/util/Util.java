package com.react.api.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Util {
	
	public static String date() {
		LocalDate now = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		String formatedNow = now.format(formatter);

		return formatedNow;
	}

	public static String time() {
		LocalTime now = LocalTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		String formatedTime = now.format(formatter);

		return formatedTime;
	}

	public static String getPrintStackTrace(Exception e) {
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));

		return errors.toString();
	}

	public static int createdAt() {
		return (int) (new Date().getTime() / 1000);
	}

	public static int getStringToIntDate(String value) {
		return (int) (new Date(value).getTime() / 1000);
	}
	
	public static String getIntToStringDate(int value) {
		return new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(new Date(value * 1000L));
	}

	public static String date(int day) {
		LocalDate now = LocalDate.now();

		if (day >= 0) {
			now = now.plusDays(day);
		} else {
			day = -day;
			now = now.minusDays(day);
		}

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		String formatedNow = now.format(formatter);

		return formatedNow;
	}

//	public static Map<String, Object> setPageMap(PageDto pageDto) {
//		Map<String, Object> pageMap = new HashMap<>();
//		pageMap.put("totalCount", pageDto.getTotalCount());
//		pageMap.put("hasPrevious", pageDto.hasPrevious());
//		pageMap.put("hasNext", pageDto.hasNext());
//		pageMap.put("startPage", pageDto.startPage());
//		pageMap.put("endPage", pageDto.endPage());
//		pageMap.put("pageRange", pageDto.pageRange());
//
//		return pageMap;
//	}

	/*
	 * set sha256
	 */
	public static String sha256(String value) throws NoSuchAlgorithmException {
		MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
		messageDigest.update(value.getBytes());

		return bytesToHex(messageDigest.digest());
	}

	public static String bytesToHex(byte[] bytes) {
		StringBuilder builder = new StringBuilder();

		for (byte b : bytes) {
			builder.append(String.format("%02x", b));
		}

		return builder.toString();
	}
}
