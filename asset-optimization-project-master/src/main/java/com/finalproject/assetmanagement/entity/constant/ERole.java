package com.finalproject.assetmanagement.entity.constant;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
public enum ERole {

        ROLE_EMPLOYEE,
        ROLE_MANAGER;

        public static ERole get(String value){
            for (ERole erole: ERole.values()) {
                if (erole.name().equalsIgnoreCase(value))
                    return erole;
            }
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "role is not found");
        }


}



