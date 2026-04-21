package com.emp.mgt.sys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import com.emp.mgt.sys.entity.Employee;
import com.emp.mgt.sys.service.EmployeeService;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins="*")

public class EmployeeController {
	@Autowired
	private EmployeeService employeeService;
	
//	Get all employees
	@GetMapping
	public ResponseEntity<List<Employee>> getAllEmployees(){
		return ResponseEntity.ok(employeeService.getAllEmployee());
	}
	
//	Get employee by Id
	@GetMapping("/{id}")
	public ResponseEntity<Employee> getEmployeebyid(@PathVariable Long id){
		return ResponseEntity.ok(employeeService.getEmployeeById(id));
	}
	
//	Post create employee
	@PostMapping
	public ResponseEntity<Employee>createEmployee(@RequestBody Employee employee){
		return new ResponseEntity<>(employeeService.createEmployee(employee),HttpStatus.CREATED);
	}
	
//	Put update employee
	@PutMapping("/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable Long id , @RequestBody Employee employee){
		return ResponseEntity.ok(employeeService.updateEmployee(id, employee));
	}
	
//	Delete employee
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteEmployee(@PathVariable Long id){
		employeeService.deleteEmployee(id);
		return ResponseEntity.ok("Employee deleted successfully");
	}
	
}
