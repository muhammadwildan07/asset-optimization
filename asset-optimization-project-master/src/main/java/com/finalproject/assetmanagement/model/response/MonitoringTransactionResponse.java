package com.finalproject.assetmanagement.model.response;

import lombok.*;

@Setter
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class MonitoringTransactionResponse {

    private String id;

    private String username;

    private String password;

    private String email;

    private String mobilePhone;

    private TransactionResponse transaction;

}
