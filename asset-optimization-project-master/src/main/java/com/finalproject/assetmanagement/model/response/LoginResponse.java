package com.finalproject.assetmanagement.model.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class LoginResponse {

    private String Token;
    private String email;
    private List<String> roles;

}

