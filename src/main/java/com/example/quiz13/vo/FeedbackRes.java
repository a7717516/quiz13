package com.example.quiz13.vo;

import java.util.List;

public class FeedbackRes extends BasicRes{
	
	private String quizName;
	
	private String description;
	
	private List<FeedbackVo> feedbackList;

	public FeedbackRes() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FeedbackRes(int code, String message) {
		super(code, message);
		// TODO Auto-generated constructor stub
	}

	public FeedbackRes(int code, String message,String quizName, String description, List<FeedbackVo> feedbackList) {
		super(code, message);
		this.quizName = quizName;
		this.description = description;
		this.feedbackList = feedbackList;
	}

	public String getQuizName() {
		return quizName;
	}

	public String getDescription() {
		return description;
	}

	public List<FeedbackVo> getFeedbackList() {
		return feedbackList;
	}
	
	
}
