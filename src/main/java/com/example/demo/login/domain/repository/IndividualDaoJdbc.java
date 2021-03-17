package com.example.demo.login.domain.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class IndividualDaoJdbc {

	@Autowired
	JdbcTemplate jdbc;

	public int insertMember(String userid)  throws DataAccessException {


		int rowNumber = jdbc.update("insert into line_member("
				+ " userid)"
				+ " values(?) on conflict do nothing"
				, userid);

		return rowNumber;

	}

	public int deleteMember(String userid)  throws DataAccessException {


		int rowNumber = jdbc.update("delete from line_member where userid=?", userid);

		return rowNumber;

	}

	public List<String> getLineMember() throws DataAccessException {

		List<Map<String, Object>> getList = jdbc.queryForList("select userid from line_member");

		List<String> memberList = new ArrayList<>();

		for(Map<String, Object> map: getList) {

			String userId = (String)map.get("userid");

			memberList.add(userId);
		}

		return memberList;


	}

}
