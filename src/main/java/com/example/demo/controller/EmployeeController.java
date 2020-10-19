package com.example.demo.controller;

import java.sql.Timestamp;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class EmployeeController {
	private final EmployeeRepository employeeRepository;

	@GetMapping("employees/index")
	public String index(Model model) {
		model.addAttribute("employees", employeeRepository.findAll());

		if(model.getAttribute("flash") != null) {
			model.addAttribute("flash", model.getAttribute("flash"));
		}

		return "employees/index";
	}

	@GetMapping("employees/new")
	public String employeeNew(@ModelAttribute Employee employee) {

		return "employees/new";
	}

	@PostMapping("employees/create")
	public String create(@Validated @ModelAttribute Employee employee,
						RedirectAttributes redirectAttributes,
						BindingResult results) {
		if(results.hasErrors()) {
			return "employees/new";
		}

		Timestamp currentTime = new Timestamp(System.currentTimeMillis());
		employee.setCreated_at(currentTime);
		employee.setUpdated_at(currentTime);

		employee.setDelete_flag(0);

		employeeRepository.save(employee);

		redirectAttributes.addFlashAttribute("flash", "登録が完了しました");

		return "redirect:/employees/index";
	}

	@GetMapping("employees/show")
	private String show(@RequestParam(name="code", required = true) int code,
						Model model) {
		//model.addAttribute("employee", repository.findById(id));
		Employee e = employeeRepository.findByCode(code);
		model.addAttribute("employee", e);

		return "employees/show";
	}

	@GetMapping("employees/edit")
	private String edit(@RequestParam(name = "id", required = true) int id,
						Model model) {
		Employee e = employeeRepository.findEmployeeById(id);
		model.addAttribute("employee", e);
		return "employees/edit";
	}

	@PostMapping("employees/update")
	private String update(@Validated @ModelAttribute Employee employee, RedirectAttributes redirectAttributes, BindingResult results) {
		if(results.hasErrors()) {
			return "employees/edit";
		}

		// 変更前の従業員情報を取得
		Employee e = employeeRepository.findEmployeeById(employee.getId());

		// 値の更新
		e.setName(employee.getName());
		e.setCode(employee.getCode());
		e.setPassword(employee.getPassword());
		e.setAdmin_flag(employee.getAdmin_flag());

		Timestamp currentTime = new Timestamp(System.currentTimeMillis());
		e.setUpdated_at(currentTime);

		// 更新した値の保存
		employeeRepository.save(e);

		redirectAttributes.addFlashAttribute("flash", "更新が完了しました");

		return "redirect:/employees/index";
	}

	@GetMapping("employees/delete/{id}")
	private String delete(@PathVariable int id, RedirectAttributes redirectAttributes) {
		Employee e = employeeRepository.findEmployeeById(id);

		// 削除フラッグの更新
		e.setDelete_flag(1);

		employeeRepository.save(e);

		redirectAttributes.addFlashAttribute("flash", "削除が完了しました");

		return "redirect:/employees/index";
	}


}
