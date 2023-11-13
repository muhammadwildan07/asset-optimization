package com.finalproject.assetmanagement.service;

import com.finalproject.assetmanagement.entity.Employee;
import com.finalproject.assetmanagement.model.response.EmployeeResponse;
import com.finalproject.assetmanagement.repository.EmployeeRepository;
import com.finalproject.assetmanagement.service.implementation.EmployeeServiceImplementation;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class EmployeeServiceImplementationTest {
    private final EmployeeRepository employeeRepository = mock(EmployeeRepository.class);
    private final EmployeeService employeeService = new EmployeeServiceImplementation(employeeRepository);

//    @Test
//    void createNewEmployee() {
//        Employee dummyEmployee = new Employee();
//        dummyEmployee.setId("123");
//        dummyEmployee.setUsername("Suryani");
//
//        when(employeeRepository.save(any(Employee.class))).thenReturn(dummyEmployee);
//
//        EmployeeResponse createEmployee = employeeService.createNewEmployee(dummyEmployee);
//
//        verify(employeeRepository, times(1)).save(dummyEmployee);
//
//        assertEquals("123", createEmployee.getId());
//        assertEquals("Suryani", createEmployee.getUsername());
//    }

    @Test
    void getEmployeeById() {
        String employeeId = "1";
        Employee dummyEmployee = new Employee();
        dummyEmployee.setId(employeeId);
        dummyEmployee.setUsername("Farhan");

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(dummyEmployee));

        Employee retriviedEmployee = employeeService.getEmployeeById(employeeId);

        verify(employeeRepository, times(1)).findById(employeeId);

        assertEquals(employeeId, retriviedEmployee.getId());
        assertEquals("Farhan", retriviedEmployee.getUsername());
    }
//
//    @Test
//    void getAllEmployee() {
//        List<Employee> dummyEmployee = new ArrayList<>();
//        dummyEmployee.add(new Employee("1", "Farhan", "farhan123", "farhan@gmail.com", "0813337"));
//        dummyEmployee.add(new Employee("2", "Suryani", "suryani007", "suryanips@gmail.com", "08134345"));
//        dummyEmployee.add(new Employee("3", "Wildan", "wildan456", "wildan@gmail.com", "08987544"));
//
//        when(employeeRepository.findAll()).thenReturn(dummyEmployee);
//
//        List<EmployeeResponse> retriviedEmployee = employeeService.getAllEmployee();
//
//        verify(employeeRepository, times(1)).findAll();
//
//        assertEquals(dummyEmployee.size(), retriviedEmployee.size());
//
//        for (int i = 0; i < dummyEmployee.size(); i++){
//            assertEquals(dummyEmployee.get(i).getId(), retriviedEmployee.get(i).getId());
//            assertEquals(dummyEmployee.get(i).getUsername(), retriviedEmployee.get(i).getUsername());
//        }
//    }

//    @Test
//    void updateEmployee() {
//        String employeeId = "1";
//        Employee dummyEmployeeToUpdate = new Employee(employeeId, "suryani (Update)", "suryani007", "suryanips@gmail.com", "083787737");
//
//        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(new Employee(employeeId, "suryani (Update)", "suryani007", "suryanips@gmail.com", "083787737")));
//
//        when(employeeRepository.save(dummyEmployeeToUpdate)).thenReturn(dummyEmployeeToUpdate);
//
//        Employee updateEmployee = employeeService.updateEmployee(dummyEmployeeToUpdate);
//
//        verify(employeeRepository, times(1)).findById(employeeId);
//        verify(employeeRepository, times(1)).save(dummyEmployeeToUpdate);
//
//        assertEquals(dummyEmployeeToUpdate.getUsername(), updateEmployee.getUsername());
//    }

    @Test
    void deleteEmployeeById() {
        String employeeId = "1";
        employeeService.deleteEmployeeById(employeeId);
        verify(employeeRepository, times(1)).deleteById(employeeId);
    }
}