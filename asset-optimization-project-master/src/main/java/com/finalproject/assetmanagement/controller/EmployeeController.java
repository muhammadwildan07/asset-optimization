package com.finalproject.assetmanagement.controller;

import com.finalproject.assetmanagement.entity.Employee;
import com.finalproject.assetmanagement.model.request.EmployeeRequest;
import com.finalproject.assetmanagement.model.response.CommonResponse;
import com.finalproject.assetmanagement.model.response.EmployeeResponse;
import com.finalproject.assetmanagement.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

//    @PostMapping
//    public ResponseEntity<?> createNewEmployee(@RequestBody EmployeeRequest employee) {
//        EmployeeResponse employeeResponse = employeeService.createNewEmployee(employee);
//        return ResponseEntity
//                .status(HttpStatus.CREATED)
//                .body(CommonResponse.<EmployeeResponse>builder()
//                        .statusCode(HttpStatus.CREATED.value())
//                        .message("Successfully create new employee")
//                        .data(employeeResponse)
//                        .build());
//    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable String id) {
        Employee employee = employeeService.getEmployeeById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CommonResponse.<EmployeeResponse>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully get employee by id")
                        .data(EmployeeResponse.builder()
                                .id(employee.getId())
                                .username(employee.getUsername())
                                .email(employee.getEmail())
                                .password(employee.getPassword())
                                .mobilePhone(employee.getMobilePhone())
                                .build())
                        .build());
    }

    @GetMapping
    public ResponseEntity<?> getAllEmployee() {
        List<EmployeeResponse> employees = employeeService.getAllEmployee();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CommonResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully get all employee")
                        .data(employees)
                        .build());
    }


    @PutMapping
    public ResponseEntity<?> updateEmployee(@RequestBody EmployeeRequest employee) {
        EmployeeResponse employeeResponse = employeeService.updateEmployee(employee);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(CommonResponse.<EmployeeResponse>builder()
                        .statusCode(HttpStatus.ACCEPTED.value())
                        .message("Successfully update employee")
                        .data(employeeResponse)
                        .build());
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable String id) {
        employeeService.deleteEmployeeById(id);
        Employee employee = new Employee();
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(CommonResponse.<String>builder()
                        .statusCode(HttpStatus.NO_CONTENT.value())
                        .message("Successfully delete employee")
                        .data(String.valueOf(employee))
                        .build());
    }
}
