package com.emp.mgt.sys.controller;

import com.emp.mgt.sys.entity.Department;
import com.emp.mgt.sys.service.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
@CrossOrigin(origins = "*")
@Tag(name = "Department Management", description = "APIs for managing departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @Operation(summary = "Get all departments", description = "Returns list of all departments")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved all departments")
    @GetMapping
    public ResponseEntity<List<Department>> getAllDepartments() {
        return ResponseEntity.ok(departmentService.getAllDepartments());
    }

    @Operation(summary = "Get department by ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Department found"),
        @ApiResponse(responseCode = "404", description = "Department not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Department> getDepartmentById(
            @Parameter(description = "Department ID") @PathVariable Long id) {
        return ResponseEntity.ok(departmentService.getDepartmentById(id));
    }

    @Operation(summary = "Create new department")
    @ApiResponse(responseCode = "201", description = "Department created successfully")
    @PostMapping
    public ResponseEntity<Department> createDepartment(
            @RequestBody Department department) {
        return new ResponseEntity<>(
                departmentService.createDepartment(department), HttpStatus.CREATED);
    }

    @Operation(summary = "Update existing department")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Department updated successfully"),
        @ApiResponse(responseCode = "404", description = "Department not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Department> updateDepartment(
            @Parameter(description = "Department ID") @PathVariable Long id,
            @RequestBody Department department) {
        return ResponseEntity.ok(departmentService.updateDepartment(id, department));
    }

    @Operation(summary = "Delete department by ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Department deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Department not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDepartment(
            @Parameter(description = "Department ID") @PathVariable Long id) {
        departmentService.deleteDepartment(id);
        return ResponseEntity.ok("Department deleted successfully");
    }
}