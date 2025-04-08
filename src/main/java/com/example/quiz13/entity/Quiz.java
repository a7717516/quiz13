package com.example.quiz13.entity;

import java.time.LocalDate;

import org.hibernate.validator.constraints.Length;

import com.example.quiz13.constans.ResMessage;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "quiz")
public class Quiz {
	
	@Id
	@Column(name = "id")	
	private int id;
	
	@NotBlank(message = ResMessage.ConstantsMessage.PARAM_QUIZ_NAME_REEOR)
	@Column(name = "name")
	private String name;
	
	@Length(max = 200, message = ResMessage.ConstantsMessage.PARAM_DESCRIPTION_LENGTH_TOO_LONG)
	@NotBlank(message = ResMessage.ConstantsMessage.PARAM_DESCRIPTION_REEOR)
	@Column(name = "description")
	private String description;
	
	@NotNull(message = ResMessage.ConstantsMessage.PARAM_START_DATE_REEOR)
	@Column(name = "start_Date")
	private LocalDate startDate;
	
	@NotNull(message = ResMessage.ConstantsMessage.PARAM_END_DATE_REEOR)
	@Column(name ="end_Date")
	private LocalDate endDate;
	
	
	@Column(name ="is_published")
	private boolean published;

	public Quiz() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Quiz(String name, String description, LocalDate startDate, LocalDate endDate, boolean published) {
		super();
		this.name = name;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.published = published;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public boolean isPublished() {
		return published;
	}

}
