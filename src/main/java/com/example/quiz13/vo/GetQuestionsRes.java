package com.example.quiz13.vo;

import java.util.List;

import com.example.quiz13.entity.Question;

public class GetQuestionsRes extends BasicRes{
	
	List<Question> question;

	public GetQuestionsRes() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GetQuestionsRes(int code, String message) {
		super(code, message);
		// TODO Auto-generated constructor stub
	}

	public GetQuestionsRes(int code, String message,List<Question> question) {
		super(code, message);
		this.question = question;
	}

	public List<Question> getQuestion() {
		return question;
	}
	
	

}
