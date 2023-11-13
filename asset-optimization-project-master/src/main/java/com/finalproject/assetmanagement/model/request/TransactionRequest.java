package com.finalproject.assetmanagement.model.request;

import lombok.*;

import java.util.Date;

@Setter
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class TransactionRequest {
    private String id;

    private String inboundItem;

    private String outboundItem;

    private Long loanAmount;

    private String status;

    private String employeeId;

    private String assetId;
}
