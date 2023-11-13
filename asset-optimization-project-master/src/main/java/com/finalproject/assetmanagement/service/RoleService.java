package com.finalproject.assetmanagement.service;


import com.finalproject.assetmanagement.entity.Role;
import com.finalproject.assetmanagement.entity.constant.ERole;

public interface RoleService {
    Role getOrSave(ERole role);
}

