package com.emp.mgt.sys.dto;

import java.util.stream.Collectors;

import com.emp.mgt.sys.entity.Department;
import com.emp.mgt.sys.entity.Employee;

public class EmployeeMapper {
	
//	Convert Employee Entity to EmployeeDTO
	public static EmployeeDTO toDTO(Employee employee) {
		EmployeeDTO dto = new EmployeeDTO();
		dto.setId(employee.getId());
		dto.setFirstName(employee.getFirstName());
		dto.setLastName(employee.getLastName());
		dto.setEmail(employee.getEmail());
		dto.setJobTitle(employee.getJobTitle());
        dto.setPhone(employee.getPhone());
        if(employee.getDepartment()!=null) {
        	dto.setDepartmentId(employee.getDepartment().getId());
        	dto.setDepartmentName(employee.getDepartment().getName());	
        }
		return dto;		
	}
	
//	Convert EmployeeDTO to Employee Entity
	public static Employee toEntity(EmployeeDTO dto) {
		Employee employee = new Employee();
		employee.setFirstName(dto.getFirstName());
		employee.setLastName(dto.getLastName());
		employee.setEmail(dto.getEmail());
		employee.setJobTitle(dto.getJobTitle());
		employee.setPhone(dto.getPhone());
		return employee;		
	}
	
//	Convert Department Entity to DepartmentDTO
	public static DepartmentDTO toDepartmentDTO(Department department) {
		DepartmentDTO dto = new DepartmentDTO();
		dto.setId(department.getId());
		dto.setName(department.getName());
		dto.setDescription(department.getDescription());
		dto.setLocation(department.getLocation());
		if(department.getEmployees() !=null) {
			dto.setEmployees(department.getEmployees().stream().map(EmployeeMapper::toDTO).collect(Collectors.toList()));
		}
		return dto;
	}
	
}
