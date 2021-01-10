package com.example.demo.login;

import java.security.Principal;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.login.domain.model.Attendance;
import com.example.demo.login.domain.model.MargePlanAttendance;
import com.example.demo.login.domain.model.Plan;
import com.example.demo.login.domain.model.PlanningForm;
import com.example.demo.login.domain.model.SignupForm;
import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.service.AttendanceService;
import com.example.demo.login.domain.service.MargePlanAttendanceService;
import com.example.demo.login.domain.service.PlanService;
import com.example.demo.login.domain.service.PushService;
import com.example.demo.login.domain.service.UserService;

@Controller
public class HomeController {

	@Autowired
	PlanService planService;
	@Autowired
	MargePlanAttendanceService mpaService;
	@Autowired
	UserService userService;
	@Autowired
	AttendanceService attendanceService;
	@Autowired
	PushService pushService;

	@GetMapping("/home")
	public String getHome(Model model) {

		model.addAttribute("contents", "login/home :: home_contents");

		List<MargePlanAttendance> mpaList = mpaService.getPlanList();

		if (mpaList == null || mpaList.size() == 0) {

			mpaList = null;

		}

		model.addAttribute("mpaList", mpaList);

		return "login/homeLayout";
	}

	@GetMapping("/plan")
	public String getPlan(@ModelAttribute PlanningForm form, Model model) {

		model.addAttribute("contents", "login/plan :: plan_contents");

		return "login/homeLayout";
	}

	@PostMapping("/plan")
	public String postPlanning(@ModelAttribute @Validated PlanningForm form, BindingResult bindingResult, Model model) {

		if(bindingResult.hasErrors()) {
			return getPlan(form, model);
		}

		System.out.println(form);

		Plan plan = new Plan();

		plan.setMatchday(form.getMatchday());
		plan.setStartTime(form.getStartTime());
		plan.setMatchTime(form.getMatchTime());
		//plan.setLocation(form.getLocation());
		plan.setOpponent(form.getOpponent());
		plan.setStatus(form.getStatus());

		String location = form.getLocation();

		if(location.equals("その他")) {

			plan.setLocation(form.getOther());

		} else {

			plan.setLocation(location);
		}


		int planNo = planService.insert(plan);

		if (planNo > 0) {
			System.out.println("insert成功 No."+ planNo);
			plan.setPlanNo(planNo);
			pushService.holding(plan);

		} else {
			System.out.println("insert失敗");
		}

		return "redirect:/home";
	}

	@GetMapping("/modifyPlan/{no}")
	public String getModifyPlan(@ModelAttribute PlanningForm form, Model model, @PathVariable("no") int planNo) throws ParseException{

		System.out.println("planNo= " + planNo);

		if (planNo > 0) {

			Plan plan = planService.select(planNo);

			form.setMatchday(plan.getMatchday());
			form.setStartTime(plan.getStartTime());
			form.setMatchTime(plan.getMatchTime());
			//form.setLocation(plan.getLocation());
			form.setOpponent(plan.getOpponent());
			form.setPlanNo(planNo);


			String location = plan.getLocation();

			if((location.equals("尼崎スポーツの森")) || (location.equals("鶴見フットメッセ")) || (location.equals("宝塚CABO"))) {

				form.setLocation(plan.getLocation());

			} else {

				form.setLocation("その他");
				form.setOther(plan.getLocation());
			}

			model.addAttribute("planningForm", form);

		}

		model.addAttribute("contents", "login/modifyPlan :: modifyPlan_contents");

		return "login/homeLayout";
	}

	@PostMapping(value="/modifyPlan", params="update")
	public String postModifyPlan(@ModelAttribute @Validated PlanningForm form, BindingResult bindingResult, Model model) {

		if(bindingResult.hasErrors()) {
			return getPlan(form, model);
		}

		//System.out.println(form);

		Plan plan = new Plan();

		plan.setMatchday(form.getMatchday());
		plan.setStartTime(form.getStartTime());
		plan.setMatchTime(form.getMatchTime());
		plan.setLocation(form.getLocation());
		plan.setOpponent(form.getOpponent());
		plan.setStatus(form.getStatus());
		plan.setPlanNo(form.getPlanNo());

		boolean result = planService.update(plan);

		if (result == true) {
			System.out.println("update成功");
			pushService.modify(plan);

		} else {
			System.out.println("update失敗");
		}

		return "redirect:/home";
	}

	@PostMapping(value="/modifyPlan", params="cancell")
	public String postCancellPlan(@ModelAttribute @Validated PlanningForm form, BindingResult bindingResult, Model model) {

		if(bindingResult.hasErrors()) {
			return getPlan(form, model);
		}

		//System.out.println(form);

		boolean result = planService.cancell(form.getPlanNo());

		if (result == true) {
			System.out.println("update成功");
			Plan plan = planService.select(form.getPlanNo());
			pushService.cancel(plan);

		} else {
			System.out.println("update失敗");
		}

		return "redirect:/home";
	}

	@GetMapping("/cancelPlan/{no}")
	public String getCancelPlan(@PathVariable("no") int planNo) {

		boolean result = planService.cancell(planNo);

		if (result == true) {
			System.out.println("update成功");
			Plan plan = planService.select(planNo);
			pushService.cancel(plan);
		} else {
			System.out.println("update失敗");
		}



		return "redirect:/detailPlan/" + planNo;

	}

	@GetMapping("/detailPlan/{no}")
	public String getDetailPlan(Model model, @PathVariable("no") int planNo, Principal principal) {

		//System.out.println("planNo= " + planNo);
		//System.out.println("userId= " + principal.getName());

		String userName = userService.getName(principal.getName());

		MargePlanAttendance mpa = mpaService.selectOne(planNo);

		String userStatus = attendanceService.getStatus(planNo, principal.getName());

		Map<String, List<String>> memberList = attendanceService.getMemberList(planNo);

		if (userName == null) {

			return "redirect:/login";
		}

		model.addAttribute("userName", userName);

		model.addAttribute("mpa", mpa);

		model.addAttribute("userStatus", userStatus);

		model.addAttribute("contents", "login/detailPlan :: detailPlan_contents");

		model.addAttribute("memberList", memberList);

		return "login/homeLayout";
	}

	@PostMapping("/detailPlan")
	public String postDetailPlan(@RequestParam("action")String action, @RequestParam("planNo")int planNo,
			@RequestParam("userStatus")String currentStatus, Model model, Principal principal) {

		Attendance attendance = new Attendance();

		attendance.setNo(principal.getName() + planNo);
		attendance.setPlanNo(planNo);
		attendance.setStatus(action);
		attendance.setUserId(principal.getName());

		boolean result = attendanceService.setStatus(attendance, currentStatus);

		if (attendance.getStatus().equals("参加")){

			int statusCount = attendanceService.getStatusCount(planNo);

			Plan plan = planService.select(planNo);

			String userName = userService.getName(principal.getName());

			pushService.joinPush(plan, statusCount, userName);

		}

		if (result == true) {
			System.out.println("成功");
		} else {
			System.out.println("失敗");
		}

		return "redirect:/detailPlan/" + planNo;
	}

	@GetMapping("/userInfo")
	public String getUserInfo(@ModelAttribute SignupForm form, Model model, Principal principal) {

		String userId = principal.getName();

		if (userId != null && userId.length() > 0) {

			User user = userService.select(userId);

			form.setUserId(user.getUserId());
			form.setFirstName(user.getFirstName());
			form.setLastName(user.getLastName());

			model.addAttribute("signupForm", form);

			model.addAttribute("contents", "login/userInfo :: userInfo_contents");

			return "login/homeLayout";
		}

		return "redirect:/login";

	}

	@PostMapping("/userInfo")
	public String postUserUpdate(@ModelAttribute @Validated SignupForm form, BindingResult bindingResult,
			Model model, Principal principal) {

		if (bindingResult.hasErrors()) {

			return getUserInfo(form, model, principal);
		}

		User user = new User();

		user.setUserId(form.getUserId());
		user.setPassword(form.getPassword());
		user.setFirstName(form.getFirstName());
		user.setLastName(form.getLastName());

		boolean result = userService.update(user);

		if (result == true) {
			System.out.println("成功");
		} else {
			System.out.println("失敗");
		}

		return "redirect:/home";
	}


	@PostMapping("/logout")
	public String postLogout() {

		return "redirect:/login";

	}

	@GetMapping("/userList")
	public String getUserList(Model model) {

		List<User> userList = userService.userList();

		model.addAttribute("userList", userList);

		model.addAttribute("contents", "login/userList :: userList_contents");

		return "login/homeLayout";
	}

	@GetMapping("/userCheck/{userId}")
	public String userCheck(@ModelAttribute SignupForm form, Model model, @PathVariable("userId") String userId) {

		User user = userService.select(userId);

		form.setUserId(user.getUserId());
		form.setFirstName(user.getFirstName());
		form.setLastName(user.getLastName());

		model.addAttribute("signupForm", form);

		model.addAttribute("contents", "login/userInfo :: userInfo_contents");

		return "login/homeLayout";

	}

	@GetMapping("/userDelete/{userId}")
	public String userCheck(@PathVariable("userId") String userId) {

		boolean result = userService.delete(userId);

		if (result == true) {
			System.out.println("成功");
		} else {
			System.out.println("失敗");
		}

		return "redirect:/userList";

	}


}
