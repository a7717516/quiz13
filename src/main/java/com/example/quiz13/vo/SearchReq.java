package com.example.quiz13.vo;

import java.time.LocalDate;

import org.springframework.util.StringUtils;



public class SearchReq {
	
	private String quizName ;
	private LocalDate startDate ;
	private LocalDate endDate ;
	public String getQuizName() {
		return quizName;
	}
	public void setQuizName(String quizName) {
		this.quizName = quizName;
	}
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	public LocalDate getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	public SearchReq() {
		super();
		// TODO Auto-generated constructor stub
	}
	public SearchReq(String quizName, LocalDate startDate, LocalDate endDate) {
		super();
		this.quizName = quizName;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	public void init() {
		if(!StringUtils.hasText(this.quizName)) {
			this.quizName = "";
		}
		if(this.startDate == null) {
			this.startDate = LocalDate.of(1970, 1, 1);
		}
		
		if(this.endDate == null) {
			this.endDate = LocalDate.of(2999, 12, 31);
		}
	}

}
