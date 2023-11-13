package com.finalproject.assetmanagement.model.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Setter
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class TransactionResponse {
    private String id;

    private String inboundItem;

    private String outboundItem;

    private Long loanAmount;

    private String status;

    private EmployeeResponse employees;

    private AssetResponse assets;
}
