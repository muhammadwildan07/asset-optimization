package com.finalproject.assetmanagement.service.implementation;

import com.finalproject.assetmanagement.entity.Employee;
import com.finalproject.assetmanagement.model.request.EmployeeRequest;
import com.finalproject.assetmanagement.model.response.EmployeeResponse;
import com.finalproject.assetmanagement.repository.EmployeeRepository;
import com.finalproject.assetmanagement.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceImplementation implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Transactional(rollbackOn = Exception.class)
    @Override
    public EmployeeResponse createNewEmployee(EmployeeRequest request) {
        Employee employee = Employee.builder()
                .id(request.getId())
                .username(request.getUsername())
                .password(request.getPassword())
                .email(request.getEmail())
                .mobilePhone(request.getMobilePhone())
                .build();
        employeeRepository.save(employee);

        return employeeResponse(employee);
    }
    @Override
    public Employee getEmployeeById(String id) {
        return employeeRepository.findById(id).get();
    }

    @Override
    public List<EmployeeResponse> getAllEmployee() {
        List<Employee> employees = employeeRepository.findAll();
        List<EmployeeResponse> employeeResponses = employees.stream()
                .map(employee -> employeeResponse(employee)).collect(Collectors.toList());
        return employeeResponses;
    }

    @Override
    public EmployeeResponse updateEmployee(EmployeeRequest request) {
        Employee employee = getEmployeeById(request.getId());
        if (Objects.nonNull(employee)){
            employee.setId(request.getId());
            employee.setEmail(request.getEmail());
            employee.setUsername(request.getUsername());
            employee.setPassword(request.getPassword());
            employee.setMobilePhone(request.getMobilePhone());
            employeeRepository.save(employee);

            return employeeResponse(employee);
        }
        return null;
    }


    @Override
    public void deleteEmployeeById(String id) {
        employeeRepository.deleteById(id);
    }

    private static EmployeeResponse employeeResponse(Employee employee) {
        return EmployeeResponse.builder()
                .id(employee.getId())
                .username(employee.getUsername())
                .password(employee.getPassword())
                .email(employee.getEmail())
                .mobilePhone(employee.getMobilePhone())
                .build();
    }
}
