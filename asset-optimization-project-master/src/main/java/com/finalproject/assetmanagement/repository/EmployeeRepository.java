package com.finalproject.assetmanagement.repository;

import com.finalproject.assetmanagement.entity.Branch;
import com.finalproject.assetmanagement.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {

}
