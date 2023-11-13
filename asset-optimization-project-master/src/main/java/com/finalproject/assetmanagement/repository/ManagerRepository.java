package com.finalproject.assetmanagement.repository;

import com.finalproject.assetmanagement.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, String> {

}
