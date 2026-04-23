package com.emp.mgt.sys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emp.mgt.sys.dto.EmployeeDTO;
import com.emp.mgt.sys.service.EmployeeService;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins="*")

public class EmployeeController {
	@Autowired
	private EmployeeService employeeService;
	
//	Get all employees
	@GetMapping
	public ResponseEntity<List<EmployeeDTO>> getAllEmployees(){
		return ResponseEntity.ok(employeeService.getAllEmployees());
	}
	
//	Get employee by Id
	@GetMapping("/{id}")
	public ResponseEntity<EmployeeDTO> getEmployeebyid(@PathVariable Long id){
		return ResponseEntity.ok(employeeService.getEmployeeById(id));
	}
	
//	Post create employee
	@PostMapping
	public ResponseEntity<EmployeeDTO>createEmployee(@RequestBody EmployeeDTO employeeDTO){
		return new ResponseEntity<>(employeeService.createEmployee(employeeDTO),HttpStatus.CREATED);
	}
	
	 // PUT update employee
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable Long id,
                                                      @RequestBody EmployeeDTO employeeDTO) {
        return ResponseEntity.ok(employeeService.updateEmployee(id, employeeDTO));
    }
    
//	Delete employee
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteEmployee(@PathVariable Long id){
		employeeService.deleteEmployee(id);
		return ResponseEntity.ok("Employee deleted successfully");
	}
	
}
