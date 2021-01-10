package com.example.demo.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.login.domain.model.SignupForm;
import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.service.UserService;

@Controller
public class SignupController {

	@Autowired
	private UserService userService;

	@GetMapping("/signup")
	public String getSignUp(@ModelAttribute SignupForm form, Model model) {

		return "login/signup";
	}

	@PostMapping("/signup")
	public String postSignUp(@ModelAttribute @Validated SignupForm form, BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {

			return getSignUp(form, model);
		}

		System.out.println(form);

		User user = new User();

		user.setUserId(form.getUserId());
		user.setPassword(form.getPassword());
		user.setFirstName(form.getFirstName());
		user.setLastName(form.getLastName());

		boolean result = userService.insert(user);

		if(result == true) {
			System.out.println("insert成功");
		} else {
			System.out.println("insert失敗");
		}


		return "redirect:/login";
	}

}
