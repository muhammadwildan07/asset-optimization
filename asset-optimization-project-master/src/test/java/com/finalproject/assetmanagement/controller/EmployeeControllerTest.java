package com.finalproject.assetmanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finalproject.assetmanagement.entity.Employee;
import com.finalproject.assetmanagement.model.request.EmployeeRequest;
import com.finalproject.assetmanagement.model.response.EmployeeResponse;
import com.finalproject.assetmanagement.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeControllerTest {
    @MockBean
    private EmployeeService employeeService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createNewEmployee() throws Exception {
        //data dummy employee
        EmployeeResponse dummyEmployee = new EmployeeResponse();
        dummyEmployee.setId("employee123");
        dummyEmployee.setUsername("Suryani");

        //mock behavior
        when(employeeService.createNewEmployee(any(EmployeeRequest.class))).thenReturn(dummyEmployee);

        //mengirimkan permintaan HTTP ke endpoint controller
        mockMvc.perform(post("/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dummyEmployee)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.statusCode").value(201))
                .andExpect(jsonPath("$.message").value("Successfully create new employee"))
                .andExpect(jsonPath("$.data.id").value(dummyEmployee.getId()))
                .andExpect(jsonPath("$.data.username").value(dummyEmployee.getUsername()));

        verify(employeeService, times(1)).createNewEmployee(any(EmployeeRequest.class));
    }

    @Test
    void getEmployeeById() throws Exception {
        // Data dummy employee
        Employee dummyEmployee = new Employee();
        dummyEmployee.setId("employee123");
        dummyEmployee.setUsername("Dummy Employee");

        // Mengatur perilaku mock employeeService.getById
        when(employeeService.getEmployeeById(eq("employee123"))).thenReturn(dummyEmployee);

        // Mengirimkan permintaan HTTP GET ke endpoint controller dengan ID employee
        mockMvc.perform(get("/employee/employee123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.message").value("Successfully get employee by id"))
                .andExpect(jsonPath("$.data.id").value(dummyEmployee.getId()))
                .andExpect(jsonPath("$.data.username").value(dummyEmployee.getUsername()));

        // Verifikasi pemanggilan employeeService.getById
        verify(employeeService, times(1)).getEmployeeById(eq("employee123"));
    }

    @Test
    void getAllEmployee() throws Exception {
        EmployeeResponse dummyEmployee = new EmployeeResponse();
        dummyEmployee.setId("employee123");
        dummyEmployee.setUsername("Farhan");

        EmployeeResponse dummyEmployee2 = new EmployeeResponse();
        dummyEmployee2.setId("employee456");
        dummyEmployee2.setUsername("Wildan");

        List<EmployeeResponse> dummyEmployees = Arrays.asList(dummyEmployee, dummyEmployee2);

        when(employeeService.getAllEmployee()).thenReturn(dummyEmployees);

        mockMvc.perform(get("/employee"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.message").value("Successfully get all employee"))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].id").value(dummyEmployee.getId()))
                .andExpect(jsonPath("$.data[0].username").value(dummyEmployee.getUsername()))
                .andExpect(jsonPath("$.data[1].id").value(dummyEmployee2.getId()))
                .andExpect(jsonPath("$.data[1].username").value(dummyEmployee2.getUsername()));

        verify(employeeService, times(1)).getAllEmployee();
    }

    @Test
    void updateEmployee() throws Exception {
        // Data dummy employee yang akan diperbarui
        EmployeeResponse dummyEmployee = new EmployeeResponse();
        dummyEmployee.setId("employee123");
        dummyEmployee.setUsername("Updated Dummy Employee");

        // Mengatur perilaku mock employeeService.update
        when(employeeService.updateEmployee(any(EmployeeRequest.class))).thenReturn(dummyEmployee);

        // Mengirimkan permintaan HTTP PUT ke endpoint controller
        mockMvc.perform(put("/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dummyEmployee)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.message").value("Successfully update employee"))
                .andExpect(jsonPath("$.data.id").value(dummyEmployee.getId()))
                .andExpect(jsonPath("$.data.username").value(dummyEmployee.getUsername()));

        // Verifikasi pemanggilan employeeService.update
        verify(employeeService, times(1)).updateEmployee(any(EmployeeRequest.class));
    }

    @Test
    void deleteEmployee() throws Exception {
        // ID employee yang akan dihapus
        String employeeId = "employee123";

        // Mengirimkan permintaan HTTP DELETE ke endpoint controller
        mockMvc.perform(delete("/employee/{id}", employeeId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.message").value("Successfully delete employee"));

        // Verifikasi pemanggilan employeeService.deleteById
        verify(employeeService, times(1)).deleteEmployeeById(eq(employeeId));
    }
}