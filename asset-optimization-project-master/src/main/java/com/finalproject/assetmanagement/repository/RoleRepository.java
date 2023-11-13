package com.finalproject.assetmanagement.repository;

import com.finalproject.assetmanagement.entity.Role;
import com.finalproject.assetmanagement.entity.constant.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

    Optional<Role> findByRole(ERole role);
}
