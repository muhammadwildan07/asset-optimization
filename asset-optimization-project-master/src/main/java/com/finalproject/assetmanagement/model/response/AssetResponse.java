package com.finalproject.assetmanagement.model.response;

import com.finalproject.assetmanagement.entity.Branch;
import lombok.*;

@Setter
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class AssetResponse {

    private String id;

    private String assetCode;

    private String name;

    private Long quantity;

    private String description;

    private BranchResponse branch;

}
