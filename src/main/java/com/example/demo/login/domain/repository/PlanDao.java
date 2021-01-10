package com.example.demo.login.domain.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.example.demo.login.domain.model.Plan;

public interface PlanDao {

	//Planテーブルに1件insert
	public int insertOne(Plan plan) throws DataAccessException;

	//Planテーブルから1件取得
	public Plan selectOne(int planNo) throws DataAccessException;

	//Planテーブルから全件取得
	public List<Plan> selectMany() throws DataAccessException;

	//Planテーブルを1件更新
	public int updateOne(Plan plan) throws DataAccessException;

	//Planテーブルから1件削除
	public int deleteOne(int planNo) throws DataAccessException;

	public int updateStatus(int planNo) throws DataAccessException;

}
