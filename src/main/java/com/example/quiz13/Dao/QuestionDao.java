package com.example.quiz13.Dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.quiz13.entity.Question;
import com.example.quiz13.entity.QuestionId;

import jakarta.transaction.Transactional;

@Repository
public interface QuestionDao extends JpaRepository<Question,QuestionId>{

	@Modifying
	@Transactional
	@Query(value = "insert into question(quiz_id,ques_id, name, type, is_must, options)"
			 + "values(:quiz_id, :ques_id , :name, :type, :is_must, :options)" , nativeQuery = true)
	public void insert(
			@Param("quiz_id")int quizId, //
			@Param("ques_id")int quesId,//			
			@Param("name")String name,//
			@Param("type")String type,//
			@Param("is_must")boolean must,//
			@Param("options")String options//
			);
	
	@Query(value = "select * from question where quiz_id = ?1" ,nativeQuery = true)
	public List<Question> getByQuizId(int quizId);
	@Modifying
	@Transactional
	@Query(value = "delete from question where quiz_id = ?1" ,nativeQuery = true)
	public void deleteByQuizId(int quizId);
	
	@Modifying
	@Transactional
	@Query(value="delete from question where quiz_id in (?1)"			
			,nativeQuery = true)
	public void delete(List<Integer> quizList);
}
