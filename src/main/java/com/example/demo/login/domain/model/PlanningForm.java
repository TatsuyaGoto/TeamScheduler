package com.example.demo.login.domain.model;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class PlanningForm {

	@NotNull(message = "{require_check}")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private Date matchday;

	@DateTimeFormat(pattern = "HH:mm")
	private Date startTime;

	private String matchTime = "2h";

	private String location = "尼崎スポーツの森";

	private String other;

	private String opponent = "身内メイン";

	private String status = "開催";

	private int planNo;

}