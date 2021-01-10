package com.example.demo.login.domain.model;


import java.util.Date;

import lombok.Data;

@Data
public class Plan {

	private int planNo;
	private Date matchday;
	private Date startTime;
	private String matchTime;
	private String location;
	private String opponent;
	private String status;


}
