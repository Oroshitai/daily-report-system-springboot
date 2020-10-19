package com.example.demo.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Table(name = "employees")
@Getter
@Setter
@Entity
public class Employee {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@NotBlank
	@Column(name = "name")
	private String name;

	@NotNull
	@Column(name = "code")
	private Integer code;

	@Size(max = 64)
	@Column(name = "password")
	private String password;

	@NotNull
	@Column(name = "admin_flag")
	private Integer admin_flag;

	@Column(name = "created_at")
	private Timestamp created_at;

	@Column(name = "updated_at")
	private Timestamp updated_at;

	@Column(name = "delete_flag")
	private Integer delete_flag;

}
