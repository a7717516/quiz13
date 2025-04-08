package com.example.quiz13.vo;

import java.time.LocalDate;
import java.util.List;

public class FeedbackVo {

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public void setQuesAnswerList(List<QuesAnswervo> quesAnswerList) {
		this.quesAnswerList = quesAnswerList;
	}

	public void setFillinDate(LocalDate fillinDate) {
		this.fillinDate = fillinDate;
	}

	private String username;

	private String phone;

	private String email;

	private int age;

	private List<QuesAnswervo> quesAnswerList;
	
	private LocalDate fillinDate;

	public FeedbackVo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FeedbackVo(String username, String phone, String email, int age, List<QuesAnswervo> quesAnswerList,
			LocalDate fillinDate) {
		super();
		this.username = username;
		this.phone = phone;
		this.email = email;
		this.age = age;
		this.quesAnswerList = quesAnswerList;
		this.fillinDate = fillinDate;
	}

	public String getUsername() {
		return username;
	}

	public String getPhone() {
		return phone;
	}

	public String getEmail() {
		return email;
	}

	public int getAge() {
		return age;
	}

	public List<QuesAnswervo> getQuesAnswerList() {
		return quesAnswerList;
	}

	public LocalDate getFillinDate() {
		return fillinDate;
	}

	

}
