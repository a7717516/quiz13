package com.example.quiz13.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class QuestionId implements Serializable{
	
	private int quesId;
	
	private int quizId;

	public QuestionId() {
		super();
		// TODO Auto-generated constructor stub
	}

	public QuestionId(int quesId, int quizId) {
		super();
		this.quesId = quesId;
		this.quizId = quizId;
	}

	public int getQuesId() {
		return quesId;
	}

	public int getQuizId() {
		return quizId;
	}

	
	

}
