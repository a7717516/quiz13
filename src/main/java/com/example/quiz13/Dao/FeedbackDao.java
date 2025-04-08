package com.example.quiz13.Dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.quiz13.entity.Feedback;
import com.example.quiz13.entity.FeedbackId;
import com.example.quiz13.vo.FeedbackDto;

import jakarta.transaction.Transactional;

@Repository
public interface FeedbackDao extends JpaRepository<Feedback,FeedbackId>{
	
	@Query(value="select count(quiz_id) from feedback where quiz_id = ?1 and email = ?2",nativeQuery = true)
	public int selectCount(int quizId, String email);
	
	@Modifying
	@Transactional
	@Query(value="insert into feedback (user_name, phone, email, age, quiz_id, ques_id, "
	+ " answer, fillin_date) values"
	+" (:name, :phone, :email, :age, :quizId, :quesId, :answer, now())",nativeQuery = true)
	public void insert(//
			@Param("quizId")int quizId, //
			@Param("quesId")int quesId, //
			@Param("name")String name, //
			@Param("phone")String phone,//
			@Param("email")String email,//
			@Param("age")int age,//
			@Param("answer")String answer//
			);
	/**
	 * 1. join 會撈多張表，所以裝資料的容器無法只用 1個entity 來裝載，只能創建新的容器(Dto)</br>
	 * 2. 新建立的 Dto 沒有被 spring boot 託管(在類別上加上 Annotation)，所以要透過 new 來新建，且要有完整路徑</br>
	 * 3. 因為是透過 new 來創建 Dto，所以 nativeQuery 就得要變成 false</br>
	 * 4. nativeQuery = false 時，SQL 語法中，表的名字要變成 Entity class 的名稱，欄位名稱就會是屬性變數名稱
	 */

	@Query(value="select new com.example.quiz13.vo.FeedbackDto("
			+ " Qz.id, Qz.name, Qz.description, F.userName, F.phone, F.email, F.age, "//
			+ " Qu.quesId, Qu.name, F.answer, F.fillinDate) " //
			+ " from Quiz as Qz " //
			+ " join Question as Qu on Qz.id = Qu.quizId " //
			+ " join Feedback as F on Qz.id = F.quizId where Qz.id = ?1 and Qu.quesId = F.quesId",
			nativeQuery = false)		
	public List<FeedbackDto> selectFeedback(int quizId);
	
	@Query(value="select * from feedback where quiz_id = ?1",
			nativeQuery = true)		
	public List<Feedback> selectByQuizId(int quizId);
}
