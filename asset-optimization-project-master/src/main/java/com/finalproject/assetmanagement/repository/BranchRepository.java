package com.finalproject.assetmanagement.repository;

import com.finalproject.assetmanagement.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BranchRepository extends JpaRepository<Branch, String> {
}
