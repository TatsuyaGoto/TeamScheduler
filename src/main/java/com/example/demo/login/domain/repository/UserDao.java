package com.example.demo.login.domain.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.example.demo.login.domain.model.User;

public interface UserDao {

	//Userテーブルに1件insert
	public int insertOne(User user) throws DataAccessException;

	//Userテーブルから1件取得
	public String getUserName(String userId) throws DataAccessException;

	//Planテーブルを1件更新
	public int updateOne(User user) throws DataAccessException;

	//Planテーブルから1件削除
	public int deleteOne(String userId) throws DataAccessException;

	public User selectOne(String userId) throws DataAccessException;

	public List<User> getUserList() throws DataAccessException;
}
