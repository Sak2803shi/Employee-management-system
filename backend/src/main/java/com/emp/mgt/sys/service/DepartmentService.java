package com.emp.mgt.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emp.mgt.sys.entity.Department;
import com.emp.mgt.sys.repository.DepartmentRepository;

@Service
public class DepartmentService {

	@Autowired
	private DepartmentRepository departmentRepository;
	
	// Get all departments
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }
	
//	Get department by ID
	public Department getDepartmentById(Long id) {
		return departmentRepository.findById(id).orElseThrow(()-> new RuntimeException("Department not found with id:" + id));
	}
	
//	Create new department
	public Department createDepartment(Department department) {
		if(departmentRepository.existsByName(department.getName())) {
			throw new RuntimeException("Department already exists with name:" +department.getName());
		}
		return departmentRepository.save(department);
	}
	
//	Update department
	public Department updateDepartment(Long id,Department updatedDepartment) {
		Department existing =getDepartmentById(id);
		existing.setName(updatedDepartment.getName());
		existing.setDescription(updatedDepartment.getDescription());
		existing.setLocation(updatedDepartment.getLocation());
		return departmentRepository.save(existing);
	}
	
//	Delete department
	public void deleteDepartment(Long id) {
		getDepartmentById(id);
		departmentRepository.deleteById(id);
	}
		
}
