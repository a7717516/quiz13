package com.example.quiz13.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.quiz13.Dao.QuestionDao;
import com.example.quiz13.Dao.QuizDao;
import com.example.quiz13.constans.QuesType;
import com.example.quiz13.constans.ResMessage;
import com.example.quiz13.entity.Question;
import com.example.quiz13.entity.Quiz;
import com.example.quiz13.service.ifs.QuizService;
import com.example.quiz13.vo.BasicRes;
import com.example.quiz13.vo.CreateReq;
import com.example.quiz13.vo.DeleteReq;
import com.example.quiz13.vo.GetQuestionsRes;
import com.example.quiz13.vo.SearchIdReq;
import com.example.quiz13.vo.SearchReq;
import com.example.quiz13.vo.SearchRes;
import com.example.quiz13.vo.UpdateReq;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;

@Service
public class QuizServiceImpl implements QuizService {

	@Autowired
	private QuizDao quizDao;

	@Autowired
	private QuestionDao questionDao;
	
	// @Transactional 只有錯誤發生在 RuntimeException(或其子類別)時才會將資料回朔
	// rollbackFor = Exception.class 是將資料回朔層級指定到只要發生 Exception 時，就會將資料回朔
	// 因為 Exception 是所有 XXXException 的父類別，只要是發生 XXXException 時，資料回朔都會有效
	// rollbackOn : import 的 library 是 org.springframework....
	// rollbackOn : import jakarta.transaction.Transactional
	@Transactional(rollbackOn = Exception.class)
	@Override
	public BasicRes create(CreateReq req) {
		// 參數檢查:req 的參數已透過@Valid 做基本檢查
		// 開始時間不能比結束時間晚
		if (req.getStartDate().isAfter(req.getEndDate())) {
			return new BasicRes(ResMessage.PARAM_DATE_ERROR.getCode(), //
					ResMessage.PARAM_DATE_ERROR.getMessage());
		}
		// 檢查問題內容
		BasicRes checkRes = checkQuestions(req.getQuestionList());
		if (checkRes != null) {
			return checkRes;
		}
		// 將資料寫進 DB	
		try {quizDao.insert(req.getName(), req.getDescription(),//
				req.getStartDate(), req.getEndDate(), req.isPublished());
		//取得最新一筆資料ID
		int quizId = quizDao.selectMaxQuizId();
		//將資料寫進 QUESTION	
		 for(Question item : req.getQuestionList()) {
			 //將quizId設定到每個Question的quizId的屬性上			 
			 questionDao.insert(quizId,item.getQuesId(), item.getName(), //
					 item.getType(), item.isMust(), item.getOptions());
		 }
			
		} catch (Exception e) {
			// 錯誤要拋(throw) 出去@Transactional 才會生效(將資料回朔)
			 throw e;
		}
		
		
		 return new BasicRes(ResMessage.SUCCESS.getCode(), //
					ResMessage.SUCCESS.getMessage());
	}

	private BasicRes checkQuestions(List<Question> list) {

		for (Question item : list) {
			// 檢查 question type
			if (!QuesType.checkType(item.getType())) {
				return new BasicRes(ResMessage.QUES_TYPE_ERROR.getCode(), //
						ResMessage.QUES_TYPE_ERROR.getMessage());
			}
			// 排除: type 不是 text (單選或複選)，選項沒有值
			if (!item.getType().equalsIgnoreCase(QuesType.TEXT.getType())//
					&& !StringUtils.hasText(item.getOptions())) {
				return new BasicRes(ResMessage.PARAM_OPTIONS_ERROR.getCode(), //
						ResMessage.PARAM_OPTIONS_ERROR.getMessage());
			}
			// 排除type 是 text 時，選項有值
			if (item.getType().equalsIgnoreCase(QuesType.TEXT.getType())//
					&& StringUtils.hasText(item.getOptions())) {
				return new BasicRes(ResMessage.PARAM_OPTIONS_ERROR.getCode(), //
						ResMessage.PARAM_OPTIONS_ERROR.getMessage());
			}
			//問卷的 type = text，options 沒資料，所以不用執行後續的選項個數檢查
			if(item.getType().equalsIgnoreCase(QuesType.TEXT.getType())) {
				return null;
			}
			// 檢查單選或複選題，選項至少要有兩個
			// 將選項字串轉成 List<String>: 要先確定當初創建問卷時，前端的多個選項是陣列，且使用 Stringify 轉成字串型態
			// 前端選項原本格式(陣列): ["aa","bb", "cc"]
			ObjectMapper mapper = new ObjectMapper();
			try {
				if(!item.getType().equalsIgnoreCase(QuesType.TEXT.getType())) {
					List<String> optionsList = mapper.readValue(item.getOptions(), new TypeReference<>() {
					});
					if (item.getType().equalsIgnoreCase(QuesType.SINGLE.getType())//
							|| item.getType().equalsIgnoreCase(QuesType.MULTI.getType())) {
						if (optionsList.size() < 2) {
							return new BasicRes(ResMessage.OPTIONS_PARSE_REEOR.getCode(), //
									ResMessage.OPTIONS_PARSE_REEOR.getMessage());
						}
					}
				}
				
			} catch (Exception e) {
				return new BasicRes(ResMessage.OPTIONS_SIZE_ERROR.getCode(), //
						ResMessage.OPTIONS_SIZE_ERROR.getMessage());
			}

		}
		
		     return null;

	}
	@Override
	public SearchRes getAll() {
		List<Quiz> list = quizDao.getALL();
		return new SearchRes(ResMessage.SUCCESS.getCode(), //
				ResMessage.SUCCESS.getMessage(), list);
	}
	@Override
	public GetQuestionsRes getQuestionsByQuizId(int quizId) {
		if(quizId <= 0) {
			return new GetQuestionsRes(ResMessage.PARAM_QUIZ_ID_ERROR.getCode(), //
					ResMessage.PARAM_QUIZ_ID_ERROR.getMessage());
	}
		List<Question> list = questionDao.getByQuizId(quizId);
		return new GetQuestionsRes(ResMessage.SUCCESS.getCode(), //
				ResMessage.SUCCESS.getMessage(), list);		
    }
	// 因為 update 的方法包含
	@Transactional(rollbackOn = Exception.class)
	@Override
	public BasicRes Update(UpdateReq req) {
		// 不用檢查 quiz 的 id 是否存在，因為最後的更新語法中條件是 quiz 的 id，不存在也不會更新成功
		// 要檢查 quiz 的 id 是否存在，因為最後是先將問題的資料刪除後再新增，而不是用更新語法
		// 不使用更新語法的考量原因是，更新前有5題，更新後變成4題，若是用更新語法，原本的第5題還會存在
		// 因 quiz 的 id 不存在，問題刪除時資料不會變更，但接著新增時就會把資料新增，且這些資料沒有對應的 quiz_id
		int count = quizDao.selectCountById(req.getId());
		if(count != 1) {
			return new BasicRes(ResMessage.ID_NOT_FOUND.getCode(), //
					ResMessage.ID_NOT_FOUND.getMessage());
		}
		// 開始時間不能比結束時間晚
		if (req.getStartDate().isAfter(req.getEndDate())) {
			return new BasicRes(ResMessage.PARAM_DATE_ERROR.getCode(), //
					ResMessage.PARAM_DATE_ERROR.getMessage());
		}		
			// 檢查問題內容
			BasicRes checkRes = checkQuestions(req.getQuestionList());
			if (checkRes != null) {
				return checkRes;
			}
			// 檢查 Question 中的 quizid 是否與 Quiz 的 id 值一樣
			for(Question item : req.getQuestionList()) {
				if(item.getQuizId() != req.getId()) {
					return new BasicRes(ResMessage.QUIZ_ID_MISMATCH.getCode(), //
							ResMessage.QUIZ_ID_MISMATCH.getMessage());
				}
	}
			try {
				//1.更新問卷
				quizDao.updateById(req.getId(), req.getName(), req.getDescription(),//
						req.getStartDate(),req.getEndDate(), req.isPublished());
				//2.刪除同張問卷的所有問題
				questionDao.deleteByQuizId(req.getId());
				//3.新增問題
				 for(Question item : req.getQuestionList()) {
					 //將quizId設定到每個Question的quizId的屬性上			 
					 questionDao.insert(req.getId(),item.getQuesId(), item.getName(), //
							 item.getType(), item.isMust(), item.getOptions());
				 }
			} catch (Exception e) {
				throw e;
			}
				return new BasicRes(ResMessage.SUCCESS.getCode(), //
						ResMessage.SUCCESS.getMessage());
			
}

	@Override
	public SearchRes getAll(SearchReq req) {
		// 轉換值
		String quizName = req.getQuizName();
		LocalDate starttDate = req.getStartDate();
		LocalDate endDate = req.getEndDate();
		if(!StringUtils.hasText(quizName)) {
			// 將quizName = null 或是 quizName = 全空白字串時，轉換成空字串
			quizName = "";
		}
		// startDate 沒有帶值表示不是條件之一，但因為 SQL 的語法，可以把 startDate 替換成很早的時間
		if(starttDate == null) {
			starttDate = LocalDate.of(1970, 1, 1);
		}
		// startDate 沒有帶值表示不是條件之一，但因為 SQL 的語法，可以把 endDate 替換成很晚的時間
		if(endDate == null) {
			endDate = LocalDate.of(2999, 12, 31);
		}
		List<Quiz> list = quizDao.getALL(quizName,starttDate,endDate);
		return new SearchRes(ResMessage.SUCCESS.getCode(), //
				ResMessage.SUCCESS.getMessage(),list);
	}
	
	@Transactional(rollbackOn = Exception.class)
	@Override
	public BasicRes Delete(DeleteReq req) {
		try {
			quizDao.delete(req.getQuizList());
			questionDao.delete(req.getQuizList());
		} catch (Exception e) {
			throw e;
		}
		
		return new SearchRes(ResMessage.SUCCESS.getCode(), //
				ResMessage.SUCCESS.getMessage());
	}

	@Override
	public SearchRes getQuiz(SearchIdReq req) {
		List<Quiz> list=quizDao.getQuizID(req.getId());
		return new SearchRes(ResMessage.SUCCESS.getCode(), //
				ResMessage.SUCCESS.getMessage(),list);
	}
}