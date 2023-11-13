package com.finalproject.assetmanagement.service;

import com.finalproject.assetmanagement.entity.Employee;
import com.finalproject.assetmanagement.model.request.EmployeeRequest;
import com.finalproject.assetmanagement.model.response.EmployeeResponse;

import java.util.List;

public interface EmployeeService {
    EmployeeResponse createNewEmployee(EmployeeRequest request);
    Employee getEmployeeById(String id);
    List<EmployeeResponse> getAllEmployee();// parameter diisi jika menggunakan seaching

    EmployeeResponse updateEmployee(EmployeeRequest request);

    void deleteEmployeeById(String id);

}
