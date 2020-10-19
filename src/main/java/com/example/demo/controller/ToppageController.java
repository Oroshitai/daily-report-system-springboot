package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.model.Employee;
import com.example.demo.model.Report;
import com.example.demo.repository.ReportRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class ToppageController {
	private final ReportRepository reportRepository;
	@Autowired
	HttpSession session;

	@GetMapping("/")
	public String index(Model model) {
		if(!session.getAttribute("flash").equals("")) {
			model.addAttribute("flash", (String)session.getAttribute("flash"));
			session.removeAttribute("flash");
		}

		//ログインしているユーザーの日報を取得
		List<Report> reports = new ArrayList<Report>();
		Employee e = (Employee)session.getAttribute("login_employee");

		if((reportRepository.findByEmployeeOrderByIdDesc(e)).size() != 0) {
			reports = reportRepository.findByEmployeeOrderByIdDesc(e);
		}

		model.addAttribute("reports", reports);

		return "toppage/index";
	}

}
