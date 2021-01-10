package com.example.demo.login.domain.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.example.demo.login.domain.model.Attendance;

public interface AttendanceDao {

	//Attendanceテーブルに1件追加
	public int insertOne(Attendance attendance	) throws DataAccessException;

	//Attendanceテーブルからstatus情報を取得
	public Attendance getStatus(int planNo, String userId) throws DataAccessException;

	//Attendanceテーブルを1件更新
	public int updateOne(Attendance attendance) throws DataAccessException;

	public List<String> getMemberStatus(int planNo, String status) throws DataAccessException;

}
