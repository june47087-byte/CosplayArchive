package com.cosplay.archive.model;

import java.util.List;

// picture를 pic_year로 묶은 연도별 그룹 하나. archive.jsp 상단 주석의 archiveGroups 계약과 맞춥니다.
public class ArchiveYearGroupDTO {
	private int year;
	private int count;
	private List<PictureDTO> pictures;
	private int page;
	private int totalPages;

	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public List<PictureDTO> getPictures() {
		return pictures;
	}
	public void setPictures(List<PictureDTO> pictures) {
		this.pictures = pictures;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
}
