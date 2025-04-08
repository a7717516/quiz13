package com.example.quiz13.vo;

import java.util.List;

import com.example.quiz13.entity.Quiz;

public class SearchRes extends BasicRes{
	
	private List<Quiz> quizList;

	public SearchRes() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SearchRes(int code, String message) {
		super(code, message);
		// TODO Auto-generated constructor stub
	}

	public SearchRes(int code, String message, List<Quiz> quizList) {
		super(code, message);
		this.quizList = quizList;
	}

	public List<Quiz> getQuizList() {
		return quizList;
	}
	
	

}
