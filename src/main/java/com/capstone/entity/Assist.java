package com.capstone.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="assist")
public class Assist {

	@Column(name="name")
	private String name;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@Column(name="assistance_needed")
	private String assistanceNeeded;
	
	@Column(name="date")
	private String date;
	
	@Column(name="topic")
	private String topic;
	
	@Column(name="time")
	private String time;
	
	@Column(name="price")
	private String price;
	
	@OneToOne
	private Member assistMember;

	
	public Member getAssistMember() {
		return assistMember;
	}
	public void setAssistMember(Member assistMember) {
		this.assistMember = assistMember;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String getAssistanceNeeded() {
		return assistanceNeeded;
	}
	public void setAssistanceNeeded(String assistanceNeeded) {
		this.assistanceNeeded = assistanceNeeded;
	}

	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}

	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
}
