package com.example.quiz13.Dao;


import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.quiz13.entity.Quiz;

import jakarta.transaction.Transactional;

@Repository
public interface QuizDao extends JpaRepository<Quiz,Integer	>{
	
	// SQL 語法中 :name 對應的變數名稱是 @Param 括號中的字串 name，其餘皆同
	@Modifying
	@Transactional
	@Query(value = "insert into quiz (name,description,start_Date, end_Date,is_published)"
			 + "values( :name, :description, :startDate, :endDate, :published)" , nativeQuery = true)
	public void insert(//			
			@Param("name") String name,//
			@Param("description")String description, //
			@Param("startDate")LocalDate startDate,//
			@Param("endDate")LocalDate endDate,//
			@Param("published")boolean published);
	
	
	@Query(value="select max(id) from quiz ",nativeQuery = true)	
	public int selectMaxQuizId();
	
	@Query(value="select * from quiz ",nativeQuery = true)
	public List<Quiz> getALL();
	
	@Query(value="select * from quiz where id = ?1",nativeQuery = true)
	public List<Quiz> getQuizID(int id);
	
	@Query(value="select * from quiz where name like %?1% and start_date >= ?2 and end_date <= ?3",nativeQuery = true)
	public List<Quiz> getALL(String quizName, LocalDate startDate, LocalDate endDate);
	
	@Query(value="select count(id) from quiz where id = ?1 ",nativeQuery = true)
	public int selectCountById(int id);
	@Modifying
	@Transactional
	@Query(value="update quiz set name = :name, description = :description,"
			+ "startDate = :startDate, endDate = :endDate, is_published = :published where id = :id "
			,nativeQuery = true)
	public void updateById(//
			@Param("id") int id, //
			@Param("name") String name,//
			@Param("description")String description, //
			@Param("startDate")LocalDate startDate,//
			@Param("endDate")LocalDate endDate,//
			@Param("published")boolean published);
	@Modifying
	@Transactional
	@Query(value="delete from quiz where id in (?1)"			
			,nativeQuery = true)
	public void delete(List<Integer> quizList);
	
	@Query(value="select count(id) from quiz where id = ?1 and ?2 >= start_date" 
			+ " and ?2 <= end_date and is_published = true",nativeQuery = true)
	public int selectCountById(int id, LocalDate now);
	
}
