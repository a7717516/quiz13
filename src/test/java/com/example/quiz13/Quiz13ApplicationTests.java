package com.example.quiz13;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.quiz13.Dao.FeedbackDao;
import com.example.quiz13.Dao.QuestionDao;
import com.example.quiz13.Dao.QuizDao;
import com.example.quiz13.entity.Question;
import com.example.quiz13.service.ifs.QuizService;
import com.example.quiz13.vo.BasicRes;
import com.example.quiz13.vo.CreateReq;
import com.example.quiz13.vo.SearchReq;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
class Quiz13ApplicationTests {
	
	@Autowired
	private QuizDao quizDao;
	
	@Autowired
	private QuestionDao questionDao;
	
	@Autowired
	private FeedbackDao feedbackDao;
	
	@Autowired
	private QuizService quizService;
	@Test
	 void contextLoads() {
	}
	@Test
	public void insertTest() throws JsonProcessingException {		
		ObjectMapper mapper = new ObjectMapper();
		String str = mapper.writeValueAsString(List.of("AAA", "BBB", "CCC"));
		
		List<Question> list = new ArrayList<>();
		list.add(new Question(1,1,"A","single",true, str));
		LocalDate startDate = LocalDate.of(2025, 3, 27);
		LocalDate endDate = LocalDate.of(2025, 3, 31);
		CreateReq req = new CreateReq("AA","AAA",startDate,endDate,true,list);
		BasicRes res = quizService.create(req);
		System.out.println(res.getCode() + res.getMessage());

	}
	
	@Test
	public void selectTest() {
		
		feedbackDao.selectFeedback(1);
		
		
		
	}
}
