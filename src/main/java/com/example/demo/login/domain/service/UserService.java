package com.example.demo.login.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.repository.UserDao;

@Service
public class UserService {

	@Autowired
	UserDao dao;

	public boolean insert(User user) {

		int rowNumber = dao.insertOne(user);

		boolean result = false;

		if(rowNumber > 0) {
			result = true;
		}

		return result;
	}

	public String getName(String userId) {

		return dao.getUserName(userId);

	}

	public User select(String userId) {

		return dao.selectOne(userId);

	}

	public boolean update(User user) {

		int rowNumber = dao.updateOne(user);

		boolean result = false;

		if(rowNumber > 0) {
			result = true;
		}

		return result;
	}

	public List<User> userList() {

		return dao.getUserList();

	}

	public boolean delete(String userId) {

		int rowNumber = dao.deleteOne(userId);

		boolean result = false;

		if(rowNumber > 0) {
			result = true;
		}

		return result;

	}

}
