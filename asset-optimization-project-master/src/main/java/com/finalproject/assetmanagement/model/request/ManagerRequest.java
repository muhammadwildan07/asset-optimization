package com.finalproject.assetmanagement.model.request;

import com.finalproject.assetmanagement.entity.UserCredential;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ManagerRequest {

    private String id;

    private String username;

    private String password;

    private String email;

    private String mobilePhone;

    private String status;

    private UserCredential userCredential;
}
