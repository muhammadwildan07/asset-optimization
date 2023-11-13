package com.finalproject.assetmanagement.model.request;

import lombok.*;

@Setter
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ApprovedTransactionRequest {

    private String id;

    private String status;
}
