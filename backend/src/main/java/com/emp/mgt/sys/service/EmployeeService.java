package com.emp.mgt.sys.service;

import com.emp.mgt.sys.dto.EmployeeDTO;
import com.emp.mgt.sys.dto.EmployeeMapper;
import com.emp.mgt.sys.entity.Department;
import com.emp.mgt.sys.entity.Employee;
import com.emp.mgt.sys.repository.DepartmentRepository;
import com.emp.mgt.sys.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    // Get all employees
    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(EmployeeMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Get employees with pagination
    public Page<EmployeeDTO> getEmployeesWithPagination(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return employeeRepository.findAll(pageable)
                .map(EmployeeMapper::toDTO);
    }

    // Search employees
    public Page<EmployeeDTO> searchEmployees(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("firstName"));
        return employeeRepository.searchEmployees(keyword, pageable)
                .map(EmployeeMapper::toDTO);
    }

    // Get employees by department
    public List<EmployeeDTO> getEmployeesByDepartment(String departmentName) {
        return employeeRepository.findByDepartmentName(departmentName)
                .stream()
                .map(EmployeeMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Get employee by ID
    public EmployeeDTO getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
        return EmployeeMapper.toDTO(employee);
    }

    // Create new employee
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        if (employeeRepository.existsByEmail(employeeDTO.getEmail())) {
            throw new RuntimeException("Email already exists: " + employeeDTO.getEmail());
        }
        Employee employee = EmployeeMapper.toEntity(employeeDTO);
        if (employeeDTO.getDepartmentId() != null) {
            Department department = departmentRepository.findById(employeeDTO.getDepartmentId())
                    .orElseThrow(() -> new RuntimeException("Department not found with id: " + employeeDTO.getDepartmentId()));
            employee.setDepartment(department);
        }
        return EmployeeMapper.toDTO(employeeRepository.save(employee));
    }

    // Update employee
    public EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO) {
        Employee existing = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
        existing.setFirstName(employeeDTO.getFirstName());
        existing.setLastName(employeeDTO.getLastName());
        existing.setEmail(employeeDTO.getEmail());
        existing.setJobTitle(employeeDTO.getJobTitle());
        existing.setPhone(employeeDTO.getPhone());
        if (employeeDTO.getDepartmentId() != null) {
            Department department = departmentRepository.findById(employeeDTO.getDepartmentId())
                    .orElseThrow(() -> new RuntimeException("Department not found with id: " + employeeDTO.getDepartmentId()));
            existing.setDepartment(department);
        }
        return EmployeeMapper.toDTO(employeeRepository.save(existing));
    }

    // Delete employee
    public void deleteEmployee(Long id) {
        employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
        employeeRepository.deleteById(id);
    }
}