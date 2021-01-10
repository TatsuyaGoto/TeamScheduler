package com.example.demo.login.domain.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.login.domain.model.MargePlanAttendance;

@Repository
public class MargePlanAttendanceDaoJdbcImpl implements MargePlanAttendanceDao{

	@Autowired
	JdbcTemplate jdbc;

	@Override
	public List<MargePlanAttendance> StatusCount() throws DataAccessException {

		List<Map<String, Object>> getList = jdbc.queryForList("select"
				+ " plan.plan_no, plan.matchday, plan.start_time, plan.match_time, plan.location, plan.opponent, plan.status,"
				+ " count( case when attendance.status='参加' then 1 else null end ) as attendance,"
				+ " count( case when attendance.status='不参加' then 1 else null end ) as absence,"
				+ " count( case when attendance.status='調整中' then 1 else null end ) as adjust"
				+ " from plan left join attendance on plan.plan_no = attendance.plan_no"
				+ " where plan.matchday > current_date - 1 group by plan.plan_no order by plan.matchday asc, plan.start_time asc");

		List<MargePlanAttendance> mpaList = new ArrayList<>();

		for(Map<String, Object> map: getList) {

			MargePlanAttendance mpa = new MargePlanAttendance();

			mpa.setPlanNo((Integer)map.get("plan_no"));
			mpa.setMatchday((Date)map.get("matchday"));
			mpa.setStartTime((Date)map.get("start_time"));
			mpa.setMatchTime((String)map.get("match_time"));
			mpa.setLocation((String)map.get("location"));
			mpa.setOpponent((String)map.get("opponent"));
			mpa.setStatus((String)map.get("status"));
			mpa.setAttendance((long)map.get("attendance"));
			mpa.setAbsence((long)map.get("absence"));
			mpa.setAdjust((long)map.get("adjust"));

			mpaList.add(mpa);
		}

		return mpaList;
	}

	public MargePlanAttendance selectOne(int planNo) throws DataAccessException {

		Map<String, Object> map = jdbc.queryForMap("select"
				+ " plan.plan_no, plan.matchday, plan.start_time, plan.match_time, plan.location, plan.opponent, plan.status,"
				+ " count( case when attendance.status='参加' then 1 else null end ) as attendance,"
				+ " count( case when attendance.status='不参加' then 1 else null end ) as absence,"
				+ " count( case when attendance.status='調整中' then 1 else null end ) as adjust"
				+ " from plan left join attendance on plan.plan_no = attendance.plan_no"
				+ " where plan.plan_no = ? group by plan.plan_no", planNo);

		MargePlanAttendance mpa = new MargePlanAttendance();

		mpa.setPlanNo((Integer)map.get("plan_no"));
		mpa.setMatchday((Date)map.get("matchday"));
		mpa.setStartTime((Date)map.get("start_time"));
		mpa.setMatchTime((String)map.get("match_time"));
		mpa.setLocation((String)map.get("location"));
		mpa.setOpponent((String)map.get("opponent"));
		mpa.setStatus((String)map.get("status"));
		mpa.setAttendance((long)map.get("attendance"));
		mpa.setAbsence((long)map.get("absence"));
		mpa.setAdjust((long)map.get("adjust"));

		return mpa;

	}

}
