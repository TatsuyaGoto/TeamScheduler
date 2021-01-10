package com.example.demo.login.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.login.domain.model.Plan;
import com.example.demo.login.domain.repository.PlanDao;

@Service
public class PlanService {

	@Autowired
	PlanDao dao;

	public int insert(Plan plan) {

		/*int rowNumber = dao.insertOne(plan);

		boolean result = false;

		if(rowNumber > 0) {
			result = true;
		}*/

		return dao.insertOne(plan);
	}

	public Plan select(int planNo) {

		return dao.selectOne(planNo);

	}

	public boolean update(Plan plan) {

		int rowNumber = dao.updateOne(plan);

		boolean result = false;

		if (rowNumber > 0) {
			result = true;
		}

		return result;

	}

	public boolean cancell(int planNo) {

		int rowNumber = dao.updateStatus(planNo);

		boolean result = false;

		if (rowNumber > 0) {
			result = true;
		}

		return result;

	}

}
