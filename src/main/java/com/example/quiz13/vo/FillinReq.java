package com.example.quiz13.vo;

import java.time.LocalDate;
import java.util.List;

import com.example.quiz13.constans.ResMessage;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;


public class FillinReq {
	
	@Min(value = 1, message = ResMessage.ConstantsMessage.PARAM_QUIZ_ID_LIST_REEOR)
	private  int quizId;
	
	private String name;
	
	private String phone;
	
	@NotBlank(message = ResMessage.ConstantsMessage.EMAIL_IS_NECESSARY)
	private String email;
	
	private int age;
	
	
	// 此參數不用驗證，一個極端的情況是所有的問題都是非必填且都沒有作答
	private List<QuesIdAnswerVo> quesIdansewrList;

	public int getQuizId() {
		return quizId;
	}

	public void setQuizId(int quizId) {
		this.quizId = quizId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	

	public List<QuesIdAnswerVo> getQuesIdansewrList() {
		return quesIdansewrList;
	}

	public void setQuesIdansewrList(List<QuesIdAnswerVo> quesIdansewrList) {
		this.quesIdansewrList = quesIdansewrList;
	}
	
	

}
