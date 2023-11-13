package com.finalproject.assetmanagement.model.request;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class CreateAssetRequest {
    private String id;

    private String branchId;

    private String assetCode;

    private String name;

    private String description;

    private Long quantity;

}
