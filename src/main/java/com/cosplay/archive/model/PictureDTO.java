package com.cosplay.archive.model;

public class PictureDTO {
	private int pic_id;
	private String pic_name;
	private String pic_file;
	private String photographer;
	private String pic_event;
	private int pic_year;
	public int getPic_id() {
		return pic_id;
	}
	public void setPic_id(int pic_id) {
		this.pic_id = pic_id;
	}
	public String getPic_name() {
		return pic_name;
	}
	public void setPic_name(String pic_name) {
		this.pic_name = pic_name;
	}
	public String getPic_file() {
		return pic_file;
	}
	public void setPic_file(String pic_file) {
		this.pic_file = pic_file;
	}
	public String getPhotographer() {
		return photographer;
	}
	public void setPhotographer(String photographer) {
		this.photographer = photographer;
	}
	public String getPic_event() {
		return pic_event;
	}
	public void setPic_event(String pic_event) {
		this.pic_event = pic_event;
	}
	public int getPic_year() {
		return pic_year;
	}
	public void setPic_year(int pic_year) {
		this.pic_year = pic_year;
	}
	
}
