package com.example.quiz13.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.quiz13.service.ifs.FeedBackService;
import com.example.quiz13.vo.BasicRes;
import com.example.quiz13.vo.CreateReq;
import com.example.quiz13.vo.FillinReq;

import jakarta.validation.Valid;

@CrossOrigin
@RestController
public class FeedbackController {
	
	@Autowired
	private FeedBackService feedBackService;
	
	@PostMapping(value ="quiz/fillin")
	public BasicRes fillin(@Valid @RequestBody FillinReq req) {
		return feedBackService.fillin(req);
		
	}
}
