package com.emp.mgt.sys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.emp.mgt.sys.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee,Long>{

}
