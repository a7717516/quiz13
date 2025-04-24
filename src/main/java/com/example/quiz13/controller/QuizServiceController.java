package com.example.quiz13.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.quiz13.service.ifs.FeedBackService;
import com.example.quiz13.service.ifs.QuizService;
import com.example.quiz13.vo.BasicRes;
import com.example.quiz13.vo.CreateReq;
import com.example.quiz13.vo.DeleteReq;
import com.example.quiz13.vo.GetQuestionsRes;
import com.example.quiz13.vo.SearchIdReq;
import com.example.quiz13.vo.SearchReq;
import com.example.quiz13.vo.SearchRes;
import com.example.quiz13.vo.UpdateReq;

import jakarta.validation.Valid;

@CrossOrigin
@RestController
public class QuizServiceController {
	
	@Autowired
	private QuizService quizService;
	
	@Autowired
	private FeedBackService feedBackService;
	
	
	@PostMapping(value ="quiz/create")
	public BasicRes create(@Valid @RequestBody CreateReq req) {
		return quizService.create(req);
		
	}
	@GetMapping(value ="quiz/getAll")
	public SearchRes getAll() {
		return quizService.getAll();
		
	}
	// 呼叫此 API 的路徑:localhost:8080/quiz/get_by_quiz_id?quizId=編號
	// ?後面接著的是 key=value，key是要 mapping 的變數名稱，value是參數值: 多個key=value 用&串接
	@GetMapping(value ="quiz/get_by_quiz_id")
	public GetQuestionsRes getQuestionsByQuizId(//
			@RequestParam(value ="quizId")int quizId) {
		return quizService.getQuestionsByQuizId(quizId);
	}
	@PostMapping(value ="quiz/update")
	public BasicRes Update(@Valid @RequestBody UpdateReq req) {
		return quizService.Update(req);
	}
	@PostMapping(value ="quiz/search")
	public SearchRes getAll(@RequestBody SearchReq req) {
		req.init();
		return quizService.getAll(req);
	}
	@PostMapping(value ="quiz/getQuizId")
	public SearchRes getQuizId(@RequestBody SearchIdReq req) {
		return quizService.getQuiz(req);
	}
	
	@PostMapping(value ="quiz/delete")
	public BasicRes Delete(@Valid @RequestBody DeleteReq req) {
	return quizService.Delete(req);
	}
	
	
}