package com.finalproject.assetmanagement.model.request;

import com.finalproject.assetmanagement.entity.Branch;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class BranchRequest {

    private String id;

    private String branchCode;

    private String branchName;

    private String address;

    private String mobilePhone;

    private String assetId;

}
