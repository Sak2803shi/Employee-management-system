package com.emp.mgt.sys.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emp.mgt.sys.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department,Long>{
	
//	Check if department name already exixts
	boolean existsByName(String name);

}
