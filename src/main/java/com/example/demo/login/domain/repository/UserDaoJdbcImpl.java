package com.example.demo.login.domain.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.example.demo.login.domain.model.User;

@Repository
public class UserDaoJdbcImpl implements UserDao{

	@Autowired
	JdbcTemplate jdbc;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public int insertOne(User user) throws DataAccessException {

		String password = passwordEncoder.encode(user.getPassword());

		int rowNumber = jdbc.update("insert into account("
				+ " user_id,"
				+ " password,"
				+ " first_name,"
				+ " last_name,"
				+ " role)"
				+ " values(?,?,?,?,?)"
				, user.getUserId()
				, password
				, user.getFirstName()
				, user.getLastName()
				, user.getRole());

		return rowNumber;
	}

	@Override
	public String getUserName(String userId) throws DataAccessException {

		Map<String, Object> map = jdbc.queryForMap("select first_name, last_name from account where user_id = ?", userId);

		String firstName = (String)map.get("first_name");
		String lastName = (String)map.get("last_name");


		return (firstName + " " + lastName);
	}

	@Override
	public int updateOne(User user) throws DataAccessException {
		String password = passwordEncoder.encode(user.getPassword());

		int rowNumber = jdbc.update("update account"
				+ " set"
				+ " password=?,"
				+ " first_name=?,"
				+ " last_name=?,"
				+ " role=?"
				+ " where user_id=?"
				, password
				, user.getFirstName()
				, user.getLastName()
				, user.getRole()
				, user.getUserId());

		return rowNumber;
	}

	@Override
	public int deleteOne(String userId) throws DataAccessException {

		int rowNumber = jdbc.update("delete from account where user_id=?", userId);

		return rowNumber;
	}

	@Override
	public User selectOne(String userId) throws DataAccessException {

		Map<String, Object> map = jdbc.queryForMap("select * from account where user_id = ?", userId);

		User user = new User();

		user.setUserId((String)map.get("user_id"));
		//user.setPassword((String)map.get("password"));
		user.setFirstName((String)map.get("first_name"));
		user.setLastName((String)map.get("last_name"));

		return user;
	}

	@Override
	public List<User> getUserList() throws DataAccessException {

		List<Map<String, Object>> getList = jdbc.queryForList("select * from account");

		List<User> userList = new ArrayList<>();

		for(Map<String, Object> map: getList) {

			User user = new User();
			user.setUserId((String)map.get("user_id"));
			user.setFirstName((String)map.get("first_name"));
			user.setLastName((String)map.get("last_name"));

			userList.add(user);

		}

		return userList;
	}

}
