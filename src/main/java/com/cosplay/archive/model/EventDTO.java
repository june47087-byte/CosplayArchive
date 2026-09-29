package com.cosplay.archive.model;


public class EventDTO {
	private int event_id;
	private String event_name;
	private String event_region;
	private String event_day;
	private String event_place;

	public int getEvent_id() {
		return event_id;
	}
	public void setEvent_id(int event_id) {
		this.event_id = event_id;
	}
	public String getEvent_name() {
		return event_name;
	}
	public void setEvent_name(String event_name) {
		this.event_name = event_name;
	}
	public String getEvent_region() {
		return event_region;
	}
	public void setEvent_region(String event_region) {
		this.event_region = event_region;
	}
	public String getEvent_day() {
		return event_day;
	}
	public void setEvent_day(String event_day) {
		this.event_day = event_day;
	}
	public String getEvent_place() {
		return event_place;
	}
	public void setEvent_place(String event_place) {
		this.event_place = event_place;
	}

}
