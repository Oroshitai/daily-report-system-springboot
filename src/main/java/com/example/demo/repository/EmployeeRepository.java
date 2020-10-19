package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
	//従業員番号で検索
	Employee findByCode(Integer code);

	//idで検索
	Employee findEmployeeById(Integer id);

	//従業員番号とパスワードで検索
	Employee findByCodeAndPassword(Integer code, String password);


}
