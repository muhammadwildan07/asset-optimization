package com.finalproject.assetmanagement.model.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class RegisterResponse {

    private String email;

}

