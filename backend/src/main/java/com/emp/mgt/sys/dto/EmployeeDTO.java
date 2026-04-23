package com.emp.mgt.sys.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class EmployeeDTO {
	

	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private String jobTitle;
	private String phone;
	private Long departmentId;
	private String departmentName;
	
	
}
