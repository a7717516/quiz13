package com.example.quiz13.vo;

import java.util.List;

public class QuesIdAnswerVo {
	//參數不用驗證有可能是非必填
	private int quesId;
	
	private List<String> answers;

	public QuesIdAnswerVo(int quesId, List<String> answers) {
		super();
		this.quesId = quesId;
		this.answers = answers;
	}

	public QuesIdAnswerVo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getQuesId() {
		return quesId;
	}

	public void setQuesId(int quesId) {
		this.quesId = quesId;
	}

	public List<String> getAnswers() {
		return answers;
	}

	public void setAnswers(List<String> answers) {
		this.answers = answers;
	}
	
	

}
