package com.finalproject.assetmanagement.controller;

import com.finalproject.assetmanagement.entity.Branch;
import com.finalproject.assetmanagement.model.request.CreateBranchRequest;
import com.finalproject.assetmanagement.model.request.UpdateBranchRequest;
import com.finalproject.assetmanagement.model.response.BranchResponse;
import com.finalproject.assetmanagement.model.response.CommonResponse;
import com.finalproject.assetmanagement.service.BranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/branch")
public class BranchController {

    private final BranchService branchService;

    @PostMapping
    public ResponseEntity<?> createNewBranch(@RequestBody CreateBranchRequest request){
        BranchResponse branch = branchService.createBranch(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.builder()
                        .data(branch)
                        .message("branch created")
                        .statusCode(HttpStatus.CREATED.value())
                        .build());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getBranchById(@PathVariable String id){
        BranchResponse branch = branchService.getBranchById(id);
        return ResponseEntity.ok(CommonResponse.builder()
                        .data(branch)
                        .message("ok")
                        .statusCode(HttpStatus.OK.value())
                        .build());
    }

    @GetMapping
    public ResponseEntity<?> getAllBranch(){
        List<BranchResponse> branches = branchService.getAllBranch();
        CommonResponse<?> response = CommonResponse.builder()
                .message("ok")
                .data(branches)
                .statusCode(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping(path = "/update")
    public ResponseEntity<?> updateBranch(@RequestBody UpdateBranchRequest request){
        BranchResponse branch = branchService.updateBranch(request);
        return ResponseEntity.ok(CommonResponse.builder()
                        .data(branch)
                        .message("branch updated")
                        .statusCode(HttpStatus.OK.value())
                        .build());
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteBranch(@PathVariable String id){
        branchService.deleteBranchById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.builder()
                        .data("OK")
                        .message("branch deleted")
                        .statusCode(HttpStatus.OK.value())
                        .build());
    }
}
