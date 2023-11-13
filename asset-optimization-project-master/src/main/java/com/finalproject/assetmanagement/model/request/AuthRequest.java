package com.finalproject.assetmanagement.model.request;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class AuthRequest {

    private String username;
    private String password;
    private String email;
    private String mobilePhone;

}
