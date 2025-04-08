package com.example.quiz13.vo;

import java.util.List;

public class QuesAnswervo extends QuesIdAnswerVo{
	
	public void setQuesName(String quesName) {
		this.quesName = quesName;
	}

	private String quesName;

	public QuesAnswervo() {
		super();
		
	}

	public String getQuesName() {
		return quesName;
	}

	public QuesAnswervo(int quesId, String quesName,List<String> answers) {
		super(quesId, answers);
		this.quesName = quesName;
	}

	
	
	

}
