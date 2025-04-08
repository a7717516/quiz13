package com.example.quiz13.vo;

import java.util.List;

import com.example.quiz13.constans.ResMessage;

import jakarta.validation.constraints.NotEmpty;

public class DeleteReq {
	
	@NotEmpty(message = ResMessage.ConstantsMessage.PARAM_QUIZ_ID_LIST_REEOR)
	private List<Integer> quizList;

	public DeleteReq(List<Integer> quizList) {
		super();
		this.quizList = quizList;
	}

	public DeleteReq() {
		super();
		// TODO Auto-generated constructor stub
	}

	public List<Integer> getQuizList() {
		return quizList;
	}
	
	

}
