package com.emp.mgt.sys;

import com.emp.mgt.sys.dto.EmployeeDTO;
import com.emp.mgt.sys.entity.Department;
import com.emp.mgt.sys.entity.Employee;
import com.emp.mgt.sys.repository.DepartmentRepository;
import com.emp.mgt.sys.repository.EmployeeRepository;
import com.emp.mgt.sys.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private EmployeeService employeeService;

    private Employee employee1;
    private Employee employee2;
    private Department department;

    @BeforeEach
    void setUp() {
        department = new Department();
        department.setId(1L);
        department.setName("IT");
        department.setLocation("Pune");

        employee1 = new Employee();
        employee1.setId(1L);
        employee1.setFirstName("Sakshi");
        employee1.setLastName("Sharma");
        employee1.setEmail("sakshi@gmail.com");
        employee1.setJobTitle("Java Developer");
        employee1.setPhone("9999999999");
        employee1.setDepartment(department);

        employee2 = new Employee();
        employee2.setId(2L);
        employee2.setFirstName("Rahul");
        employee2.setLastName("Patil");
        employee2.setEmail("rahul@gmail.com");
        employee2.setJobTitle("Frontend Developer");
        employee2.setPhone("8888888888");
        employee2.setDepartment(department);
    }

    // Test 1 — Get all employees
    @Test
    void shouldReturnAllEmployees() {
        // Arrange — set up fake data
        when(employeeRepository.findAll())
                .thenReturn(Arrays.asList(employee1, employee2));

        // Act — call the method
        List<EmployeeDTO> result = employeeService.getAllEmployees();

        // Assert — verify the result
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getFirstName()).isEqualTo("Sakshi");
        assertThat(result.get(1).getFirstName()).isEqualTo("Rahul");

        // Verify repository was called exactly once
        verify(employeeRepository, times(1)).findAll();
    }

    // Test 2 — Get employee by ID — success
    @Test
    void shouldReturnEmployeeById() {
        // Arrange
        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(employee1));

        // Act
        EmployeeDTO result = employeeService.getEmployeeById(1L);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getFirstName()).isEqualTo("Sakshi");
        assertThat(result.getEmail()).isEqualTo("sakshi@gmail.com");
        assertThat(result.getDepartmentName()).isEqualTo("IT");
    }

    // Test 3 — Get employee by ID — not found
    @Test
    void shouldThrowExceptionWhenEmployeeNotFound() {
        // Arrange
        when(employeeRepository.findById(999L))
                .thenReturn(Optional.empty());

        // Assert — expect exception
        assertThatThrownBy(() -> employeeService.getEmployeeById(999L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Employee not found with id: 999");
    }

    // Test 4 — Create employee successfully
    @Test
    void shouldCreateEmployeeSuccessfully() {
        // Arrange
        EmployeeDTO dto = new EmployeeDTO();
        dto.setFirstName("Priya");
        dto.setLastName("Desai");
        dto.setEmail("priya@gmail.com");
        dto.setJobTitle("HR Manager");
        dto.setPhone("7777777777");
        dto.setDepartmentId(1L);

        when(employeeRepository.existsByEmail("priya@gmail.com"))
                .thenReturn(false);
        when(departmentRepository.findById(1L))
                .thenReturn(Optional.of(department));
        when(employeeRepository.save(any(Employee.class)))
                .thenReturn(employee1);

        // Act
        EmployeeDTO result = employeeService.createEmployee(dto);

        // Assert
        assertThat(result).isNotNull();
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    // Test 5 — Create employee with duplicate email
    @Test
    void shouldThrowExceptionWhenEmailAlreadyExists() {
        // Arrange
        EmployeeDTO dto = new EmployeeDTO();
        dto.setEmail("sakshi@gmail.com");

        when(employeeRepository.existsByEmail("sakshi@gmail.com"))
                .thenReturn(true);

        // Assert
        assertThatThrownBy(() -> employeeService.createEmployee(dto))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Email already exists");
    }

    // Test 6 — Delete employee successfully
    @Test
    void shouldDeleteEmployeeSuccessfully() {
        // Arrange
        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(employee1));
        doNothing().when(employeeRepository).deleteById(1L);

        // Act
        employeeService.deleteEmployee(1L);

        // Assert
        verify(employeeRepository, times(1)).deleteById(1L);
    }

    // Test 7 — Delete employee not found
    @Test
    void shouldThrowExceptionWhenDeletingNonExistentEmployee() {
        // Arrange
        when(employeeRepository.findById(999L))
                .thenReturn(Optional.empty());

        // Assert
        assertThatThrownBy(() -> employeeService.deleteEmployee(999L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Employee not found with id: 999");

        verify(employeeRepository, never()).deleteById(any());
    }

    // Test 8 — Update employee successfully
    @Test
    void shouldUpdateEmployeeSuccessfully() {
        // Arrange
        EmployeeDTO dto = new EmployeeDTO();
        dto.setFirstName("Sakshi Updated");
        dto.setLastName("Sharma");
        dto.setEmail("sakshi.updated@gmail.com");
        dto.setJobTitle("Senior Developer");
        dto.setPhone("9999999999");
        dto.setDepartmentId(1L);

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(employee1));
        when(departmentRepository.findById(1L))
                .thenReturn(Optional.of(department));
        when(employeeRepository.save(any(Employee.class)))
                .thenReturn(employee1);

        // Act
        EmployeeDTO result = employeeService.updateEmployee(1L, dto);

        // Assert
        assertThat(result).isNotNull();
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }
}