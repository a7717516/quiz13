package com.example.quiz13.vo;

import java.time.LocalDate;

//DTO(Data Transfer Object)
//DTO: 命名為 DTO 的原因是這些資料主要是從資料庫來，且無法只用一個 Entity 裝載，一般是用 join 取多張表的欄位資料
//VO: 也可以命名為 VO(Value Object): 用來裝資料容器的統稱
//Entity: 也是裝資料的容器，但 Entity 可以 mapping 到資料表及其資料欄位

public class FeedbackDto {
	
	private int quizId;
	
	private String quizName;
	
	private String description;
	
	private String username;
	
	private String phone;
	
	private String email;
	
	private int age;
	
	private int quesId;
	
	private String quesName;
	
	private String answer;
	
	private LocalDate fillinDate;

	public FeedbackDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FeedbackDto(int quizId, String quizName, String description, String username, String phone, String email,
			int age, int quesId, String quesName, String answer, LocalDate fillinDate) {
		super();
		this.quizId = quizId;
		this.quizName = quizName;
		this.description = description;
		this.username = username;
		this.phone = phone;
		this.email = email;
		this.age = age;
		this.quesId = quesId;
		this.quesName = quesName;
		this.answer = answer;
		this.fillinDate = fillinDate;
	}

	public int getQuizId() {
		return quizId;
	}

	public String getQuizName() {
		return quizName;
	}

	public String getDescription() {
		return description;
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

	public int getQuesId() {
		return quesId;
	}

	public String getQuesName() {
		return quesName;
	}

	public String getAnswer() {
		return answer;
	}

	public LocalDate getfillinDate() {
		return fillinDate;
	}


}
