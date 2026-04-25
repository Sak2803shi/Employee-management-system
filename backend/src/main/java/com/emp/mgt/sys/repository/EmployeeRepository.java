package com.emp.mgt.sys.repository;

import com.emp.mgt.sys.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // Search by first name or last name
    List<Employee> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
            String firstName, String lastName);

    // Search by department name
    List<Employee> findByDepartmentName(String departmentName);

    // Find by email
    boolean existsByEmail(String email);

    // Pagination — get all employees with pagination
    Page<Employee> findAll(Pageable pageable);

    // Search with pagination
    @Query("SELECT e FROM Employee e WHERE " +
           "LOWER(e.firstName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(e.lastName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(e.jobTitle) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Employee> searchEmployees(@Param("keyword") String keyword, Pageable pageable);
}