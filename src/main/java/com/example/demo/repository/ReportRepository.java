package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Employee;
import com.example.demo.model.Report;

public interface ReportRepository extends JpaRepository<Report, Integer> {
	//idで日報を検索
	Report findReportById(Integer id);

	//従業員情報で日報を検索
	List<Report> findByEmployeeOrderByIdDesc(Employee employee);

}
