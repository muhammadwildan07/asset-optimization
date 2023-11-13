package com.finalproject.assetmanagement.controller;

import com.finalproject.assetmanagement.entity.Asset;
import com.finalproject.assetmanagement.entity.Branch;
import com.finalproject.assetmanagement.model.request.CreateAssetRequest;
import com.finalproject.assetmanagement.model.request.UpdateAssetRequest;
import com.finalproject.assetmanagement.model.response.AssetResponse;
import com.finalproject.assetmanagement.model.response.CommonResponse;
import com.finalproject.assetmanagement.service.AssetService;
import com.finalproject.assetmanagement.service.BranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/asset")
public class AssetController {

    private final AssetService  assetService;
    @PostMapping
    public ResponseEntity<?> createNewAsset(@RequestBody CreateAssetRequest request){
        AssetResponse assetResponse = assetService.createNewAsset(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.builder()
                        .data(assetResponse)
                        .message("assert created")
                        .statusCode(HttpStatus.CREATED.value())
                        .build());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getAssetById(@PathVariable String id){
        AssetResponse assetResponse = assetService.getAssetById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.builder()
                        .message("ok")
                        .data(assetResponse)
                        .statusCode(HttpStatus.OK.value())
                        .build());
    }

    @GetMapping
    public ResponseEntity<?> getAllAsset(){
        List<AssetResponse> assetResponses = assetService.getAllAsset();
        CommonResponse<?> response = CommonResponse.builder()
                .message("ok")
                .statusCode(HttpStatus.OK.value())
                .data(assetResponses)
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<?> updateAsset(@RequestBody UpdateAssetRequest request){
        AssetResponse assetResponse = assetService.updateAsset(request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.builder()
                        .data(assetResponse)
                        .message("assert updated")
                        .statusCode(HttpStatus.OK.value())
                        .build());
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteAsset(@PathVariable String id){
        assetService.deleteAssetById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.builder()
                        .data(id)
                        .message("assert deleted")
                        .statusCode(HttpStatus.OK.value())
                        .build());
    }


}
