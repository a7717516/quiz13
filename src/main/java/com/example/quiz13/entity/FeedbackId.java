package com.example.quiz13.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class FeedbackId implements Serializable{
	
	private String email;
	
	private int quizId;
	
	private int quesId;

	public FeedbackId() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FeedbackId(String email, int quizId, int quesId) {
		super();
		this.email = email;
		this.quizId = quizId;
		this.quesId = quesId;
	}

	public String getEmail() {
		return email;
	}

	public int getQuizId() {
		return quizId;
	}

	public int getQuesId() {
		return quesId;
	}

	

}
