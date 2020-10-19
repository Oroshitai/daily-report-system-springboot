package com.example.demo.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class LoginCotroller {
	private final EmployeeRepository employeeRepository;
	@Autowired
	HttpSession session;

	@GetMapping("/login")
	public String index(@ModelAttribute Employee employee) {


		return "login/index";
	}

	@PostMapping("/login")
	public String login(@ModelAttribute Employee employee,
						Model model) {
		if((employeeRepository.findByCodeAndPassword(employee.getCode(), employee.getPassword())) == null) {
			model.addAttribute("flash", "従業員番号かパスワードが違います");
			return "login/index";
		}

		Employee e = employeeRepository.findByCodeAndPassword(employee.getCode(), employee.getPassword());

		session.setAttribute("login_employee", e);

		session.setAttribute("flash", "ログインしました");

		return "redirect:/";
	}

	@GetMapping("/logout")
	private String logout(RedirectAttributes redirectAttributes) {
		session.removeAttribute("loginEmployee");

		redirectAttributes.addFlashAttribute("flash", "ログアウトしました");

		return "redirect:/login";
	}

}
