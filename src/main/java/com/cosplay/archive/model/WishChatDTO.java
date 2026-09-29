package com.cosplay.archive.model;

public class WishChatDTO {
	private int wish_chat_id;
	private int wish_id;
	private String wish_chat_url;
	private String wish_chat_contents;

	public int getWish_chat_id() {
		return wish_chat_id;
	}
	public void setWish_chat_id(int wish_chat_id) {
		this.wish_chat_id = wish_chat_id;
	}
	public int getWish_id() {
		return wish_id;
	}
	public void setWish_id(int wish_id) {
		this.wish_id = wish_id;
	}
	public String getWish_chat_url() {
		return wish_chat_url;
	}
	public void setWish_chat_url(String wish_chat_url) {
		this.wish_chat_url = wish_chat_url;
	}
	public String getWish_chat_contents() {
		return wish_chat_contents;
	}
	public void setWish_chat_contents(String wish_chat_contents) {
		this.wish_chat_contents = wish_chat_contents;
	}

}
