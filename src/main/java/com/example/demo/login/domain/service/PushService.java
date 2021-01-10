package com.example.demo.login.domain.service;

import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.login.domain.model.Plan;
import com.example.demo.login.domain.repository.LineClient;
import com.linecorp.bot.client.LineMessagingClient;

@Service
public class PushService {

	@Autowired
	LineMessagingClient lineMessagingClient;
	@Autowired
	LineClient lineClient;

	public void holding(Plan plan) {

		SimpleDateFormat fmt1 = new SimpleDateFormat("yyyy'/'MM'/'dd'('E')'");
		SimpleDateFormat fmt2 = new SimpleDateFormat("k':'mm");

		String message = "次回開催のお知らせ\n"
				+ "日付：" + fmt1.format(plan.getMatchday()) + "\n"
				+ "時間：" + fmt2.format(plan.getStartTime()) + "～ (" + plan.getMatchTime() + ")\n"
				+ "場所：" + plan.getLocation() + "\n"
				+ "相手：" + plan.getOpponent() + "\n"
				+ "https://lavita-scheduler.herokuapp.com/detailPlan/" + plan.getPlanNo();

		lineClient.pushMessage(message);

	}

	public void modify(Plan plan) {

		SimpleDateFormat fmt1 = new SimpleDateFormat("yyyy'/'MM'/'dd'('E')'");
		SimpleDateFormat fmt2 = new SimpleDateFormat("k':'mm");

		String message = "計画修正のお知らせ\n"
				+ "日付：" + fmt1.format(plan.getMatchday()) + "\n"
				+ "時間：" + fmt2.format(plan.getStartTime()) + "～ (" + plan.getMatchTime() + ")\n"
				+ "場所：" + plan.getLocation() + "\n"
				+ "相手：" + plan.getOpponent() + "\n"
				+ "https://lavita-scheduler.herokuapp.com/detailPlan/" + plan.getPlanNo();

		lineClient.pushMessage(message);

	}

	public void cancel(Plan plan) {

		SimpleDateFormat fmt1 = new SimpleDateFormat("yyyy'/'MM'/'dd'('E')'");
		SimpleDateFormat fmt2 = new SimpleDateFormat("k':'mm");

		String message = "✖✖中止連絡✖✖\n"
				+ "日付：" + fmt1.format(plan.getMatchday()) + "\n"
				+ "時間：" + fmt2.format(plan.getStartTime()) + "～ (" + plan.getMatchTime() + ")";

		lineClient.pushMessage(message);

	}

	public void joinPush(Plan plan, int statusCount, String userName) {

		SimpleDateFormat fmt1 = new SimpleDateFormat("MM'/'dd'('E')'");
		SimpleDateFormat fmt2 = new SimpleDateFormat("k':'mm");

		String message = fmt1.format(plan.getMatchday()) + "　"
				+ fmt2.format(plan.getStartTime()) + "～\n"
				+ "参加登録：" + userName + "\n"
				+ "現在の参加：" + statusCount + "人";


		System.out.println(message);

		lineClient.pushMessage(message);

	}

}
