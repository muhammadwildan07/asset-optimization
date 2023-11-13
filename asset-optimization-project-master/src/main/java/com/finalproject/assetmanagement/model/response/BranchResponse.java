package com.finalproject.assetmanagement.model.response;

import lombok.*;

@Setter
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class BranchResponse {
    private String id;
    private String branchCode;
    private String branchName;
    private String address;
    private String mobilePhone;
}
