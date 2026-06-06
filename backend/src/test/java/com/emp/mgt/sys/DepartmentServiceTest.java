package com.emp.mgt.sys;

import com.emp.mgt.sys.entity.Department;
import com.emp.mgt.sys.repository.DepartmentRepository;
import com.emp.mgt.sys.service.DepartmentService;
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
class DepartmentServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private DepartmentService departmentService;

    private Department department1;
    private Department department2;

    @BeforeEach
    void setUp() {
        department1 = new Department();
        department1.setId(1L);
        department1.setName("IT");
        department1.setDescription("Information Technology");
        department1.setLocation("Pune");

        department2 = new Department();
        department2.setId(2L);
        department2.setName("HR");
        department2.setDescription("Human Resources");
        department2.setLocation("Mumbai");
    }

    // Test 1 — Get all departments
    @Test
    void shouldReturnAllDepartments() {
        // Arrange
        when(departmentRepository.findAll())
                .thenReturn(Arrays.asList(department1, department2));

        // Act
        List<Department> result = departmentService.getAllDepartments();

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getName()).isEqualTo("IT");
        assertThat(result.get(1).getName()).isEqualTo("HR");
        verify(departmentRepository, times(1)).findAll();
    }

    // Test 2 — Get department by ID success
    @Test
    void shouldReturnDepartmentById() {
        // Arrange
        when(departmentRepository.findById(1L))
                .thenReturn(Optional.of(department1));

        // Act
        Department result = departmentService.getDepartmentById(1L);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("IT");
        assertThat(result.getLocation()).isEqualTo("Pune");
    }

    // Test 3 — Get department by ID not found
    @Test
    void shouldThrowExceptionWhenDepartmentNotFound() {
        // Arrange
        when(departmentRepository.findById(999L))
                .thenReturn(Optional.empty());

        // Assert
        assertThatThrownBy(() -> departmentService.getDepartmentById(999L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Department not found with id: 999");
    }

    // Test 4 — Create department successfully
    @Test
    void shouldCreateDepartmentSuccessfully() {
        // Arrange
        when(departmentRepository.existsByName("Finance"))
                .thenReturn(false);
        when(departmentRepository.save(any(Department.class)))
                .thenReturn(department1);

        Department newDept = new Department();
        newDept.setName("Finance");
        newDept.setDescription("Finance Department");
        newDept.setLocation("Bangalore");

        // Act
        Department result = departmentService.createDepartment(newDept);

        // Assert
        assertThat(result).isNotNull();
        verify(departmentRepository, times(1)).save(any(Department.class));
    }

    // Test 5 — Create duplicate department
    @Test
    void shouldThrowExceptionWhenDepartmentNameExists() {
        // Arrange
        Department newDept = new Department();
        newDept.setName("IT");

        when(departmentRepository.existsByName("IT"))
                .thenReturn(true);

        // Assert
        assertThatThrownBy(() -> departmentService.createDepartment(newDept))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Department already exists with name: IT");
    }

    // Test 6 — Delete department successfully
    @Test
    void shouldDeleteDepartmentSuccessfully() {
        // Arrange
        when(departmentRepository.findById(1L))
                .thenReturn(Optional.of(department1));
        doNothing().when(departmentRepository).deleteById(1L);

        // Act
        departmentService.deleteDepartment(1L);

        // Assert
        verify(departmentRepository, times(1)).deleteById(1L);
    }

    // Test 7 — Delete department not found
    @Test
    void shouldThrowExceptionWhenDeletingNonExistentDepartment() {
        // Arrange
        when(departmentRepository.findById(999L))
                .thenReturn(Optional.empty());

        // Assert
        assertThatThrownBy(() -> departmentService.deleteDepartment(999L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Department not found with id: 999");

        verify(departmentRepository, never()).deleteById(any());
    }

    // Test 8 — Update department successfully
    @Test
    void shouldUpdateDepartmentSuccessfully() {
        // Arrange
        when(departmentRepository.findById(1L))
                .thenReturn(Optional.of(department1));
        when(departmentRepository.save(any(Department.class)))
                .thenReturn(department1);

        Department updatedDept = new Department();
        updatedDept.setName("IT Updated");
        updatedDept.setDescription("Updated Description");
        updatedDept.setLocation("Delhi");

        // Act
        Department result = departmentService.updateDepartment(1L, updatedDept);

        // Assert
        assertThat(result).isNotNull();
        verify(departmentRepository, times(1)).save(any(Department.class));
    }
}