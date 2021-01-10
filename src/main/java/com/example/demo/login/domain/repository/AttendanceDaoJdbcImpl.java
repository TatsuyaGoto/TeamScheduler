package com.example.demo.login.domain.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.login.domain.model.Attendance;

@Repository
public class AttendanceDaoJdbcImpl implements AttendanceDao{

	@Autowired
	JdbcTemplate jdbc;

	@Override
	public int insertOne(Attendance attendance) throws DataAccessException {

		/*int rowNumber = jdbc.update("insert into attendance("
				+ " no,"
				+ " plan_no,"
				+ " user_id,"
				+ " status)"
				+ " values(?,?,?,?)"
				, attendance.getNo()
				, attendance.getPlanNo()
				, attendance.getUserId()
				, attendance.getStatus());*/

		int rowNumber = jdbc.update("insert into attendance("
				+ " no,"
				+ " plan_no,"
				+ " user_id,"
				+ " status)"
				+ " values(?,?,?,?)"
				+ " on conflict(no)"
				+ " do update set status=?"
				, attendance.getNo()
				, attendance.getPlanNo()
				, attendance.getUserId()
				, attendance.getStatus()
				, attendance.getStatus());

		return rowNumber;
	}

	@Override
	public Attendance getStatus(int planNo, String userId) throws DataAccessException {

		try {

			Map<String, Object> map = jdbc.queryForMap("select * from attendance where plan_no = ? and user_id = ?"
					, planNo, userId);

			Attendance attendance = new Attendance();

			attendance.setNo((String)map.get("no"));
			attendance.setPlanNo((Integer)map.get("plan_no"));
			attendance.setUserId((String)map.get("user_id"));
			attendance.setStatus((String)map.get("status"));

		return attendance;

		} catch (EmptyResultDataAccessException e) {

			return null;
		}

	}

	@Override
	public List<String> getMemberStatus(int planNo, String status) throws DataAccessException {

		List<Map<String, Object>> getList = jdbc.queryForList("select attendance.*, account.first_name, account.last_name"
				+ " from attendance left join account on attendance.user_id = account.user_id"
				+ " where attendance.plan_no = ? and attendance.status = ?", planNo, status);

		//List<MargePlanAttendance> mpaList = new ArrayList<>();
		List<String> memberList = new ArrayList<>();

		for(Map<String, Object> map: getList) {

			String firstName = (String)map.get("first_name");
			String lastName = (String)map.get("last_name");

			String name = firstName + " " + lastName;

			memberList.add(name);
		}

		return memberList;
	}

	@Override
	public int updateOne(Attendance attendance) throws DataAccessException {

		int rowNumber = jdbc.update("update attendance"
				+ " set"
				+ " status=?"
				+ " where plan_no=? and user_id=?"
				, attendance.getStatus()
				, attendance.getPlanNo()
				, attendance.getUserId());

		return rowNumber;
	}

}
