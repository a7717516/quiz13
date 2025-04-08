package com.example.quiz13.vo;

import java.time.LocalDate;
import java.util.List;

import com.example.quiz13.constans.ResMessage;
import com.example.quiz13.entity.Question;
import com.example.quiz13.entity.Quiz;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

public class CreateReq extends Quiz{
	
	@Valid
	@NotEmpty(message = ResMessage.ConstantsMessage.PARAM_QUES_LIST_REEOR)
	private List<Question> questionList;
	
	
	public CreateReq() {
		super();
		
	}

	public CreateReq(String name, String description, LocalDate startDate, LocalDate endDate, //
			boolean published,List<Question> questionList) {
		super(name, description, startDate, endDate, published);
		this.questionList = questionList;
	}

	

	public List<Question> getQuestionList() {
		return questionList;
	}
	
	

}
