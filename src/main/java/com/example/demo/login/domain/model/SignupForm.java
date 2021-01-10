package com.example.demo.login.domain.model;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class SignupForm {

	@NotBlank(message = "{require_check}")
	private String userId;
	@NotBlank(message = "{require_check}")
	@Pattern(regexp = "^[a-zA-Z0-9]+$", message = "{pattern_check}")
	private String password;
	@NotBlank(message = "{require_check}")
	private String firstName;
	@NotBlank(message = "{require_check}")
	private String lastName;

}
