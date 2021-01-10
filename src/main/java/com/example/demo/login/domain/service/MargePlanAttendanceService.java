package com.example.demo.login.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.login.domain.model.MargePlanAttendance;
import com.example.demo.login.domain.repository.MargePlanAttendanceDao;

@Service
public class MargePlanAttendanceService {

	@Autowired
	MargePlanAttendanceDao dao;

	public List<MargePlanAttendance> getPlanList() {

		return dao.StatusCount();

	}

	public MargePlanAttendance selectOne(int planNo) {

		return dao.selectOne(planNo);

	}

}
