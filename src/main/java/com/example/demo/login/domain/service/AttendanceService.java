package com.example.demo.login.domain.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.login.domain.model.Attendance;
import com.example.demo.login.domain.repository.AttendanceDao;

@Service
public class AttendanceService {

	@Autowired
	AttendanceDao dao;

	public String getStatus(int planNo, String userId) {

		Attendance attendance = dao.getStatus(planNo, userId);

		String status = "未回答";

		if ( attendance != null) {

			status = attendance.getStatus();

		}
		return status;
	}

	public boolean setStatus(Attendance attendance, String currentStatus) {

		boolean result = false;
		int rowNumber = 0;

		if (currentStatus.equals("未回答")) {

			rowNumber = dao.insertOne(attendance);

		} else {

			rowNumber = dao.updateOne(attendance);

		}

		if (rowNumber > 0) {

			result = true;
		}

		return result;

	}

	public Map<String, List<String>> getMemberList(int planNo){

		Map<String, List<String>> memberMap = new LinkedHashMap<>();

		List<String> attendance = dao.getMemberStatus(planNo, "参加");
		List<String> absence = dao.getMemberStatus(planNo, "不参加");
		List<String> adjust = dao.getMemberStatus(planNo, "調整中");

		memberMap.put("参加(" + attendance.size() +")", attendance);
		memberMap.put("不参加(" + absence.size() +")", absence);
		memberMap.put("調整中(" + adjust.size() +")", adjust);


		return memberMap;

	}

	public int getStatusCount(int planNo) {

		List<String> attendance = dao.getMemberStatus(planNo, "参加");

		return attendance.size();
	}
}
