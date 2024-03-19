package com.react.api.dto;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
public class PageDto {

	private int page = 1;
	private int size = 10;
	private int startPage = 1;
	private int endPage = 0;
	private int amount = 0; // 총게시물
	private Boolean prev;
	private Boolean next;
	private int allPage;

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getStartPage() {
		this.startPage = this.endPage - 9;

		return this.startPage = this.startPage < 0 ? 1 : this.startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		this.endPage = (int) (Math.ceil(this.page / 10) * 10); // 10
		
		if (getAllPage() > this.endPage) {
			this.endPage = getAllPage();
		}

		return this.endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public Boolean getPrev() {
		return getStartPage() > 1;
	}

	public void setPrev(Boolean prev) {
		this.prev = prev;
	}

	public Boolean getNext() {
		return getEndPage() < getAllPage();
	}

	public void setNext(Boolean next) {
		this.next = next;
	}

	public int getAllPage() {
		return allPage = (this.amount / this.size) + 1;
	}

	public void setAllPage(int allPage) {
		this.allPage = allPage;
	}

}
