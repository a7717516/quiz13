package com.example.quiz13.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.quiz13.service.ifs.FeedBackService;
import com.example.quiz13.vo.BasicRes;
import com.example.quiz13.vo.CreateReq;
import com.example.quiz13.vo.FeedbackRes;
import com.example.quiz13.vo.FillinReq;
import com.example.quiz13.vo.SearchIdReq;

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
	@PostMapping(value ="quiz/feedback")
	public FeedbackRes feedback(@RequestBody SearchIdReq req) {
		return feedBackService.feedback(req.getId());
		
	}
	@PostMapping(value ="quiz/statistics")
	public BasicRes statistics(@RequestBody SearchIdReq req) {
		return feedBackService.feedback(req.getId());
		
	}
}
