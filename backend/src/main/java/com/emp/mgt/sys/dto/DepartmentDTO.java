package com.emp.mgt.sys.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class DepartmentDTO {

	private Long id;
	private String name;
	private String description;
	private String location;
	private List<EmployeeDTO> employees;
}
