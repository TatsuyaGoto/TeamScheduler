package com.example.demo.login.domain.repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.login.domain.model.Plan;

@Repository
public class PlanDaoJdbcImpl implements PlanDao{

	@Autowired
	JdbcTemplate jdbc;

	@Override
	public int insertOne(Plan plan) throws DataAccessException {
		int rowNumber = jdbc.update("insert into plan("
				+ " matchday,"
				+ " start_time,"
				+ " match_time,"
				+ " location,"
				+ " opponent,"
				+ " status)"
				+ " values(?,?,?,?,?,?)"
				, plan.getMatchday()
				, plan.getStartTime()
				, plan.getMatchTime()
				, plan.getLocation()
				, plan.getOpponent()
				, plan.getStatus());

		if (rowNumber >0) {
			int plan_no = jdbc.queryForObject("select max(plan_no) from plan", Integer.class);
			return plan_no;
		} else {
			return rowNumber;
		}
	}

	@Override
	public Plan selectOne(int planNo) throws DataAccessException {

		Map<String, Object> map = jdbc.queryForMap("select * from plan where plan_no=?", planNo);

		Plan plan = new Plan();

		plan.setPlanNo((Integer)map.get("plan_no"));
		plan.setMatchday((Date)map.get("matchday"));
		plan.setStartTime((Date)map.get("start_time"));
		plan.setMatchTime((String)map.get("match_time"));
		plan.setLocation((String)map.get("location"));
		plan.setOpponent((String)map.get("opponent"));

		return plan;
	}

	@Override
	public List<Plan> selectMany() throws DataAccessException {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public int updateOne(Plan plan) throws DataAccessException {

		int rowNumber = jdbc.update("update plan"
				+ " set"
				+ " matchday=?,"
				+ " start_time=?,"
				+ " match_time=?,"
				+ " location=?,"
				+ " opponent=?,"
				+ " status=?"
				+ " where plan_no=?"
				, plan.getMatchday()
				, plan.getStartTime()
				, plan.getMatchTime()
				, plan.getLocation()
				, plan.getOpponent()
				, plan.getStatus()
				, plan.getPlanNo());

		return rowNumber;
	}

	@Override
	public int deleteOne(int planNo) throws DataAccessException {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
	}

	@Override
	public int updateStatus(int planNo) throws DataAccessException {

		int rowNumber = jdbc.update("update plan"
				+ " set status='中止'"
				+ " where plan_no=?"
				, planNo);

		return rowNumber;
	}



}
