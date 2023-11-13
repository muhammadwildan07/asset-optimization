package com.finalproject.assetmanagement.model.request;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class UpdateBranchRequest {
    private String id;

    private String branchCode;

    private String branchName;

    private String address;

    private String mobilePhone;
}
