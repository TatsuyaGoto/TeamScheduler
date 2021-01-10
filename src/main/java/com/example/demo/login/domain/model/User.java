package com.example.demo.login.domain.model;
import lombok.Data;

@Data
public class User {

	private String userId;
	private String password;
	private String firstName;
	private String lastName;
	private String role="general";

}
