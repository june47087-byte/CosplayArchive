package com.cosplay.archive.model;

import java.util.List;

public class InfoDTO {
	private int info_id;
	private String info_div;
	private String info_name;
	private String info_contents;
	private String info_url;
	private List<InfoChatDTO> comments;
	public int getInfo_id() {
		return info_id;
	}
	public void setInfo_id(int info_id) {
		this.info_id = info_id;
	}
	public String getInfo_div() {
		return info_div;
	}
	public void setInfo_div(String info_div) {
		this.info_div = info_div;
	}
	public String getInfo_name() {
		return info_name;
	}
	public void setInfo_name(String info_name) {
		this.info_name = info_name;
	}
	public String getInfo_contents() {
		return info_contents;
	}
	public void setInfo_contents(String info_contents) {
		this.info_contents = info_contents;
	}
	public String getInfo_url() {
		return info_url;
	}
	public void setInfo_url(String info_url) {
		this.info_url = info_url;
	}
	public List<InfoChatDTO> getComments() {
		return comments;
	}
	public void setComments(List<InfoChatDTO> comments) {
		this.comments = comments;
	}

}
