package com.finalproject.assetmanagement.service.implementation;

import com.finalproject.assetmanagement.entity.Branch;
import com.finalproject.assetmanagement.model.request.CreateBranchRequest;
import com.finalproject.assetmanagement.model.request.UpdateBranchRequest;
import com.finalproject.assetmanagement.model.response.BranchResponse;
import com.finalproject.assetmanagement.repository.BranchRepository;
import com.finalproject.assetmanagement.service.BranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BranchServiceImplementation implements BranchService {

    private final BranchRepository branchRepository;

    @Transactional(rollbackOn = Exception.class)
    @Override
    public BranchResponse createBranch(CreateBranchRequest request) {
        Branch branch = Branch.builder()
                .id(request.getId())
                .branchCode(request.getBranchCode())
                .branchName(request.getBranchName())
                .address(request.getAddress())
                .mobilePhone(request.getMobilePhone())
                .build();

        branchRepository.save(branch);
        return branchResponse(branch);
    }

    @Override
    public BranchResponse getBranchById(String id) {
        Branch branch = branchRepository.findById(id).get();
        return branchResponse(branch);
    }

    @Override
    public List<BranchResponse> getAllBranch() {
        List<Branch> branches = branchRepository.findAll();
        List<BranchResponse> branchResponses = branches.stream()
                .map(branch -> branchResponse(branch)).collect(Collectors.toList());
        return branchResponses;
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public BranchResponse updateBranch(UpdateBranchRequest request) {
        Branch branch = branchRepository.findById(request.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "branch not found"));
        if(Objects.nonNull(branch)) {
            branch.setBranchCode(request.getBranchCode());
            branch.setBranchName(request.getBranchName());
            branch.setAddress(request.getAddress());
            branch.setMobilePhone(request.getMobilePhone());
            branchRepository.save(branch);

            return branchResponse(branch);
        }
        return null;
    }

    @Override
    public void deleteBranchById(String id) {
        branchRepository.deleteById(id);
    }
    private static BranchResponse branchResponse(Branch branch) {
        return BranchResponse.builder()
                .id(branch.getId())
                .branchName(branch.getBranchName())
                .branchCode(branch.getBranchCode())
                .mobilePhone(branch.getMobilePhone())
                .address(branch.getAddress())
                .build();
    }
}
