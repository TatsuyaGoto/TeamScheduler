package com.example.demo.login.domain.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.example.demo.login.domain.model.MargePlanAttendance;

public interface MargePlanAttendanceDao {

	//全日程のメンバーステータスを集計
	public List<MargePlanAttendance> StatusCount() throws DataAccessException;

	public MargePlanAttendance selectOne(int planNo) throws DataAccessException;

}
