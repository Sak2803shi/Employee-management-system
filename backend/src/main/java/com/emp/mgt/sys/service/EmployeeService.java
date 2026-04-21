package com.emp.mgt.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emp.mgt.sys.entity.Employee;
import com.emp.mgt.sys.repository.EmployeeRepository;

@Service
public class EmployeeService {
  
	@Autowired
    private EmployeeRepository employeeRepository;
	
//	Get all employee
	public List<Employee> getAllEmployee(){
		return employeeRepository.findAll();
	}
	
//	Get employee by ID
	public Employee getEmployeeById(Long id) {
		return employeeRepository.findById(id)
				.orElseThrow(()->new RuntimeException("Employee not found with id: "+id));
	}
	
//	Create new employee
	public Employee createEmployee(Employee employee) {
		return employeeRepository.save(employee);
	}
	
//	Update employee
	public Employee updateEmployee(Long id, Employee updateEmployee) {
		Employee existing = getEmployeeById(id);
		existing.setFirstName(updateEmployee.getFirstName());
		existing.setLastName(updateEmployee.getLastName());
		existing.setEmail(updateEmployee.getEmail());
		existing.setDepartment(updateEmployee.getDepartment());
		existing.setJobTitle(updateEmployee.getJobTitle());
		existing.setPhone(updateEmployee.getPhone());
		
		return employeeRepository.save(existing);
	}
	
//	Delete Employee
	public void deleteEmployee(Long id) {
		getEmployeeById(id);
		employeeRepository.deleteById(id);
	}

}
