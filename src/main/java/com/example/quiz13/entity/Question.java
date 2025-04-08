package com.example.quiz13.entity;

import com.example.quiz13.constans.ResMessage;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "question")
@IdClass(value = QuestionId.class)
public class Question {
	
	@Min(value = 1, message = ResMessage.ConstantsMessage.PARAM_QUES_ID_REEOR)
	@Id
	@Column(name = "ques_id")
	private int quesId;
	

	@Id
	@Column(name = "quiz_id")
	private int quizId;
	
	@NotBlank(message = ResMessage.ConstantsMessage.PARAM_QUES_NAME_REEOR)
	@Column(name = "name")
	private String name;
	
	@NotBlank(message = ResMessage.ConstantsMessage.PARAM_QUES_TYPE_REEOR)
	@Column(name = "type")
	private String type;
	
	
	@Column(name = "is_must")
	private boolean must;
	
	//不用檢查，因為當 question type 是簡答，沒有選項
	@Column(name = "options")
	private String options;

	public Question() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Question(int quizId,int quesId, String name, String type, boolean must, String options) {
		super();
		this.quesId = quesId;
		this.quizId = quizId;
		this.name = name;
		this.type = type;
		this.must = must;
		this.options = options;
	}

	public int getQuesId() {
		return quesId;
	}

	public int getQuizId() {
		return quizId;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public boolean isMust() {
		return must;
	}

	public String getOptions() {
		return options;
	}

	public void setQuizId(int quizId) {
		this.quizId = quizId;
	}

	
	

}
