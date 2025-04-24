package com.example.quiz13.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.quiz13.Dao.FeedbackDao;
import com.example.quiz13.Dao.QuestionDao;
import com.example.quiz13.Dao.QuizDao;
import com.example.quiz13.constans.QuesType;
import com.example.quiz13.constans.ResMessage;
import com.example.quiz13.entity.Feedback;
import com.example.quiz13.entity.Question;
import com.example.quiz13.service.ifs.FeedBackService;
import com.example.quiz13.vo.BasicRes;
import com.example.quiz13.vo.FeedbackDto;
import com.example.quiz13.vo.FeedbackRes;
import com.example.quiz13.vo.FeedbackVo;
import com.example.quiz13.vo.FillinReq;
import com.example.quiz13.vo.OptionCountVo;
import com.example.quiz13.vo.QuesAnswervo;
import com.example.quiz13.vo.QuesIdAnswerVo;
import com.example.quiz13.vo.SearchRes;
import com.example.quiz13.vo.StatisticsRes;
import com.example.quiz13.vo.StatisticsVo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class FeedBackServiceImpl implements FeedBackService{
	
	private ObjectMapper mapper = new ObjectMapper();
	
	@Autowired
	private FeedbackDao feedbackDao ;
	
	@Autowired
	private QuizDao quizDao;
	
	@Autowired	
	private QuestionDao questionDao;

	@Override
	public BasicRes fillin(FillinReq req) {
		int quizId = req.getQuizId();
		// 1. 檢查 quiz
		//  是否已發布以及當前的時間是否介於
		int count = quizDao.selectCountById(quizId, LocalDate.now());
		if(count !=1) {
			return new SearchRes(ResMessage.OUT_OF_FILLIN_DATE_RANGE.getCode(), //
					ResMessage.OUT_OF_FILLIN_DATE_RANGE.getMessage());
		}
		//2. 檢查回饋資料
		//同一個 email 只能填寫同一張問卷一次
		count = feedbackDao.selectCount(quizId, req.getEmail());
		if(count != 0) { // 0表示同一個EMAIL沒有填寫過同一張問卷
			return new SearchRes(ResMessage.EMAIL_DUPLICATED.getCode(), //
					ResMessage.EMAIL_DUPLICATED.getMessage());
		}
		//3.  檢查答案
		List<QuesIdAnswerVo> quesIdAnsewrList = req.getQuesIdansewrList();
		//3.1 從DB撈問題
		List<Question> questionList = questionDao.getByQuizId(quizId);
		
		//3.2比對問題選項與答案
		for(Question quesItem : questionList) {
			int quesId = quesItem.getQuesId();
			String type = quesItem.getType();
			boolean must = quesItem.isMust();
			List<String> optionsList = new ArrayList<>();			
				try {
					//只轉換 type 非 text 的，因為 text 沒有選項可以轉換
					if(!type.equalsIgnoreCase(QuesType.TEXT.getType())) {					
					optionsList = mapper.readValue(quesItem.getOptions(), new TypeReference<>() {
					});
					}
					} catch (Exception e) {
						return new SearchRes(ResMessage.OPTIONS_PARSE_REEOR.getCode(), //
								ResMessage.OPTIONS_PARSE_REEOR.getMessage());
				}
				
			
			for(QuesIdAnswerVo voItem : quesIdAnsewrList) {
				List<String> answerList = new ArrayList<>();
				if(quesId == voItem.getQuesId()) {
					answerList = voItem.getAnswers();
					//經過上面的 IF判斷式，answerList 可能會有答案，也可能沒有答案(因為沒有填)
					//排除:必填且沒有答案
					if(must && answerList.isEmpty() ) { // must 等同於 must == true
						return new SearchRes(ResMessage.ANSWER_REQUIRED.getCode(), //
								ResMessage.ANSWER_REQUIRED.getMessage());
					}
					//跳過type 是 text 的，因為沒有選項可以轉換
					if(type.equalsIgnoreCase(QuesType.TEXT.getType())) {
						continue;
					}
					//單選時答案不能有多個
					if(type.equalsIgnoreCase(QuesType.SINGLE.getType())
							&& answerList.size() > 1) {
						return new SearchRes(ResMessage.ONE_OPTION_IS_ALLOWED.getCode(), //
								ResMessage.ONE_OPTION_IS_ALLOWED.getMessage());
					}
						
					// 比對同一題的答案是否都在選項中
					boolean checkRes = checkAnswer(optionsList, answerList);
					if(!checkRes) {
						return new BasicRes(ResMessage.ANSWER_OPTION_MISMATCH.getCode(), //
								ResMessage.ANSWER_OPTION_MISMATCH.getMessage());
					}
				}
				
			}
			
		};
		// 存資料
		
		for(QuesIdAnswerVo voItem : quesIdAnsewrList) {
			//將List<String> answerList 轉換成 String
			try {
				String answerStr = mapper.writeValueAsString(voItem.getAnswers());
				feedbackDao.insert(quizId, voItem.getQuesId(), req.getName(), req.getPhone(),//
						req.getEmail(), req.getAge(), answerStr);
			} catch (JsonProcessingException e) {
				return new SearchRes(ResMessage.ANSWER_OPTION_MISMATCH.getCode(), //
						ResMessage.ANSWER_OPTION_MISMATCH.getMessage());
			} catch(Exception e) {
				throw e;
			}
			
		}
		return new SearchRes(ResMessage.SUCCESS.getCode(), //
				ResMessage.SUCCESS.getMessage());
	}
	private boolean checkAnswer(List<String> optionsList,List<String> answerList) {
		// 切串接的字串
		List<String> newOptionsList = new ArrayList<>();
		for(String item : optionsList) {
			String[] strArray = item.split(":");
			newOptionsList.addAll(List.of(strArray));
		}
		//比對選項和答案
		for(String answer : answerList) {
			// 假設一種情況， optionList = ["A","B","C","D"], answerList = ["A", "a"]
			// 若下方的 if 是沒有驚嘆號的判斷式， if(optionsList.contains(answer)),且回傳 true
			// 當 answerList中第一個答案A判斷後就會回傳true，後面的a就會沒判斷到
			if(!optionsList.contains(answer)) {
				return false;
			}
		}
		return true;
	}
	@Override
	 public FeedbackRes feedback(int quizId) {
	  // 檢查參數
	  if (quizId <= 0) {
	   return new FeedbackRes(ResMessage.PARAM_QUIZ_ID_ERROR.getCode(), //
	     ResMessage.PARAM_QUIZ_ID_ERROR.getMessage());
	  }
	  // 撈資料
	  List<FeedbackDto> res = feedbackDao.selectFeedback(quizId);
	  // 整理資料：透過相同的 email 把問題和回答整理在一起
	  // MAP<String, List<QuesAnswerVo>> 中的key 是放email
	  Map<String, List<QuesAnswervo>> map = new HashMap<>();
	  String quizName = "";
	  String description = "";
	  for (FeedbackDto dto : res) {
	   quizName = dto.getQuizName();
	   description = dto.getDescription();
	   String email = dto.getEmail();
	   List<QuesAnswervo> voList = new ArrayList<>();
	   if (map.containsKey(email)) {
	    // 若 map 中已存在相同的 key(email)，就從 map 中把對應的 value 取出
	    voList = map.get(email);
	   }
	   QuesAnswervo vo = new QuesAnswervo();
	   vo.setQuesId(dto.getQuesId());
	   vo.setQuesName(dto.getQuesName());
	   try {
	    // 將DB 中的 answer String 轉成 List<String>
	    List<String> answerList = mapper.readValue(dto.getAnswer(), new TypeReference<>() {
	    });
	    vo.setAnswers(answerList);
	   } catch (Exception e) {
	    return new FeedbackRes(ResMessage.ANSWER_PARSE_REEOR.getCode(), //
	      ResMessage.ANSWER_PARSE_REEOR.getMessage());
	   }
	   voList.add(vo);
	   map.put(email, voList);
	  }

	  List<FeedbackVo> feedbackList = new ArrayList<>();
	  a: for (FeedbackDto dto : res) {
	   String email = dto.getEmail();
	   for (FeedbackVo vo : feedbackList) {
	    if (email.equalsIgnoreCase(vo.getEmail())) {	    		    	
	    		continue a;	    	
	    }	     
	   }
	   FeedbackVo feedbackVo = new FeedbackVo();
	   feedbackVo.setUsername(dto.getUsername());
	   feedbackVo.setPhone(dto.getPhone());
	   feedbackVo.setEmail(dto.getEmail());
	   feedbackVo.setAge(dto.getAge());
	   feedbackVo.setFillinDate(dto.getfillinDate());
	   feedbackVo.setQuesAnswerList(map.get(dto.getEmail()));
	   feedbackList.add(feedbackVo);
	  }
	  return new FeedbackRes(ResMessage.SUCCESS.getCode(), //
	    ResMessage.SUCCESS.getMessage(), quizName, description, feedbackList);
	 }

	 @Override
	 public StatisticsRes statistics(int quizId) {
	  // 從 question 找選項:不直接從答案找主要是要預防可能會有某個選項都沒人選
	  List<Question> questionList = questionDao.getByQuizId(quizId);
	  List<StatisticsVo> statisticsVoList = new ArrayList<>();
	  // Map<問題編號, 多個選項>
	  Map<Integer, List<String>> quesIdOptionsMap = new HashMap<>();
	  for (Question item : questionList) {
	   // 將 DB 中的 question 相關資訊設定到 StatisticsVo 中
	   StatisticsVo vo = new StatisticsVo(item.getQuesId(), item.getName(), //
	     item.getType(), item.isMust());
	   statisticsVoList.add(vo);
	   // 找出每一題的非 text 的選項 +! 意思= 是 text 的選項
	   if (!item.getType().equalsIgnoreCase(QuesType.TEXT.getType())) {
//	    // type 是 text 時，跳過當次
//	    continue;
	    // 將選項字串轉換成 List<String>
	    try {
	     List<String> optionList = mapper.readValue(item.getOptions(), new TypeReference<>() {
	     });
	     quesIdOptionsMap.put(item.getQuesId(), optionList);
	    } catch (Exception e) {
	     return new StatisticsRes(ResMessage.OPTIONS_PARSE_REEOR.getCode(), //
	       ResMessage.OPTIONS_PARSE_REEOR.getMessage());
	    }
	   } else {
	    quesIdOptionsMap.put(item.getQuesId(), null);
	   }
	  }
	  Map<Integer, List<String>> quesIdAnswersMap = getAnswers(quizId);
	  if (quesIdAnswersMap == null) {
	   return new StatisticsRes(ResMessage.ANSWER_PARSE_REEOR.getCode(), //
	     ResMessage.ANSWER_PARSE_REEOR.getMessage());
	  }
	  // 計算選項的回答次數
	  Map<Integer, List<OptionCountVo>> map = computeAnswerCount(quesIdOptionsMap, quesIdAnswersMap);
	  for (StatisticsVo vo : statisticsVoList) {
	   int quesId = vo.getQuesId();
	   vo.setOptionCountVoList(map.get(quesId));
	  }
	  return new StatisticsRes(ResMessage.SUCCESS.getCode(), //
	    ResMessage.SUCCESS.getMessage(), statisticsVoList);
	 }

	 private Map<Integer, List<OptionCountVo>> computeAnswerCount(Map<Integer, List<String>> quesIdOptionsMap, //
	   Map<Integer, List<String>> quesIdAnswersMap) {
	  // Map<問題編號, List<OptionCountVo>>
	  Map<Integer, List<OptionCountVo>> map = new HashMap<>();
	  for (Entry<Integer, List<String>> mapItem : quesIdOptionsMap.entrySet()) {
	   List<OptionCountVo> optionCountVoList = new ArrayList<>();
	   int quesId = mapItem.getKey();
	   List<String> optionsList = mapItem.getValue();
	   // 答案包含了: 1. 單選的選項 2. 多選的選項 3. 簡答內容
	   if (quesIdAnswersMap.containsKey(quesId) && optionsList != null) {
	    List<String> answersList = quesIdAnswersMap.get(quesId);
	    int size = answersList.size();
	    for (String option : optionsList) {
	     answersList.removeAll(List.of(option));
	     int optionSize = size - answersList.size();
	     OptionCountVo vo = new OptionCountVo(option, optionSize);
	     // 改變 answersList size
	     size = size - optionSize;
	     optionCountVoList.add(vo);
	    }
	   }
	   map.put(quesId, optionCountVoList);
	  }
	  return map;
	 }

	 private Map<Integer, List<String>> getAnswers(int quizId) {
	  // 取答案
	  List<Feedback> feedbackList = feedbackDao.selectByQuizId(quizId);
	  // Map<問題編號, 多個答案>
	  Map<Integer, List<String>> quesIdAnswersMap = new HashMap<>();
	  for (Feedback item : feedbackList) {
	   if (StringUtils.hasText(item.getAnswer())) {
	    int quesId = item.getQuesId();
	    // 將答案字串轉換成 List<String>
	    try {
	     List<String> answersList = mapper.readValue(item.getAnswer(), new TypeReference<>() {
	     });
	     if (quesIdAnswersMap.containsKey(quesId)) {
	      // 取出已存在 map 中的 answer List
	      List<String> list = quesIdAnswersMap.get(quesId);
	      // 把兩個 List 相加
	      list.addAll(answersList);
	      // 把結果放回到 map 中
	      quesIdAnswersMap.put(quesId, list);
	     } else {
	      quesIdAnswersMap.put(quesId, answersList);
	     }
	    } catch (Exception e) {
	     return null;
	    }
	   }
	  }
	  return quesIdAnswersMap;
	 }

	}
