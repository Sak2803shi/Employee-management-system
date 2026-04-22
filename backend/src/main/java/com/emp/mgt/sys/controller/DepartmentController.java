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

import com.emp.mgt.sys.entity.Department;
import com.emp.mgt.sys.service.DepartmentService;

@RestController
@RequestMapping("/api/departments")
@CrossOrigin(origins ="*")
public class DepartmentController {

	@Autowired
	private DepartmentService departmentService;
	
//	Get all departments
	 @GetMapping
	    public ResponseEntity<List<Department>> getAllDepartments() {
	        return ResponseEntity.ok(departmentService.getAllDepartments());
	    }
	
//	Get department by id
	 @GetMapping("/{id}")
	    public ResponseEntity<Department> getDepartmentById(@PathVariable Long id) {
	        return ResponseEntity.ok(departmentService.getDepartmentById(id));
	    }
	
	 // POST create department
	    @PostMapping
	    public ResponseEntity<Department> createDepartment(@RequestBody Department department) {
	        return new ResponseEntity<>(departmentService.createDepartment(department), HttpStatus.CREATED);
	    }

	    // PUT update department
	    @PutMapping("/{id}")
	    public ResponseEntity<Department> updateDepartment(@PathVariable Long id,
	                                                       @RequestBody Department department) {
	        return ResponseEntity.ok(departmentService.updateDepartment(id, department));
	    }

	    // DELETE department
	    @DeleteMapping("/{id}")
	    public ResponseEntity<String> deleteDepartment(@PathVariable Long id) {
	        departmentService.deleteDepartment(id);
	        return ResponseEntity.ok("Department deleted successfully");
	    }
	
}
