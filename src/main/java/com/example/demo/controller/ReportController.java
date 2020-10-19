package com.example.demo.controller;

import java.sql.Timestamp;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.model.Employee;
import com.example.demo.model.Report;
import com.example.demo.repository.ReportRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class ReportController {
	private final ReportRepository reportRepository;

	@Autowired
	HttpSession session;

	@GetMapping("reports/index")
	public String index(Model model) {

		model.addAttribute("reports", reportRepository.findAll());

		return "reports/index";
	}

	@GetMapping("reports/new")
	public String newReports(@ModelAttribute Report report) {


		return "reports/new";
	}

	@PostMapping("reports/create")
	public String create(@Validated @ModelAttribute Report report,
						BindingResult results,
						RedirectAttributes redirectAttributes) {
		if(results.hasErrors()) {
			return "reports/new";
		}

		report.setEmployee((Employee)session.getAttribute("login_employee"));

		Timestamp currentTime = new Timestamp(System.currentTimeMillis());
		report.setCreated_at(currentTime);
		report.setUpdated_at(currentTime);

		reportRepository.save(report);

		redirectAttributes.addFlashAttribute("falsh", "日報を作成しました");

		return "redirect:/reports/index";
	}

	@GetMapping("reports/show")
	public String show(@RequestParam(name = "id", required = true) int id,
						Model model) {
		model.addAttribute("report", reportRepository.findReportById(id));

		return "reports/show";
	}

	@GetMapping("reports/edit")
	public String edit(@RequestParam(name = "id") int id,
						Model model) {
		model.addAttribute("report", reportRepository.findReportById(id));

		return "reports/edit";
	}

	@PostMapping("/reports/update")
	public String update(@Validated @ModelAttribute Report report,
							BindingResult results,
							RedirectAttributes redirectAttributes) {
		if(results.hasErrors()) {
			return "reports/edit";
		}

		// 更新前の日報情報を取得
		Report r = reportRepository.findReportById(report.getId());

		// 値の更新
		r.setTitle(report.getTitle());
		r.setContent(report.getContent());
		r.setReport_date(report.getReport_date());

		Timestamp currentTime = new Timestamp(System.currentTimeMillis());
		r.setUpdated_at(currentTime);

		// 更新した値の保存
		reportRepository.save(r);

		redirectAttributes.addFlashAttribute("flash", "更新が完了しました");

		return "redirect:/reports/index";
	}

}
