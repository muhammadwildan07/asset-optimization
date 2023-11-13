package com.finalproject.assetmanagement.model.request;

import com.finalproject.assetmanagement.entity.UserCredential;
import lombok.*;

import javax.persistence.Column;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class EmployeeRequest {

    private String id;

    private String username;

    private String password;

    private String email;

    private String mobilePhone;

    private UserCredential userCredential;
}
