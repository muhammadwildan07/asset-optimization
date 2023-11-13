package com.finalproject.assetmanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finalproject.assetmanagement.model.request.*;
import com.finalproject.assetmanagement.model.response.AssetResponse;
import com.finalproject.assetmanagement.model.response.BranchResponse;
import com.finalproject.assetmanagement.service.AssetService;
import com.finalproject.assetmanagement.service.BranchService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BranchControllerTest {

    @MockBean
    private BranchService branchService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createNewBranch() throws Exception {
        //data dummy branch
        BranchResponse dummyBranch = new BranchResponse();
        dummyBranch.setId("branch123");
        dummyBranch.setBranchName("Cabang Medan");

        //mock behavior
        when(branchService.createBranch(any(CreateBranchRequest.class))).thenReturn(dummyBranch);

        //mengirimkan permintaan HTTP ke endpoint controller
        mockMvc.perform(post("/branch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dummyBranch)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.statusCode").value(201))
                .andExpect(jsonPath("$.message").value("branch created"))
                .andExpect(jsonPath("$.data.id").value(dummyBranch.getId()))
                .andExpect(jsonPath("$.data.branchName").value(dummyBranch.getBranchName()));

        verify(branchService, times(1)).createBranch(any(CreateBranchRequest.class));
    }

    @Test
    void getBranchById() throws Exception {
        // Data dummy branch
        BranchResponse dummyBranch = new BranchResponse();
        dummyBranch.setId("branch123");
        dummyBranch.setBranchName("Dummy Branch");

        // Mengatur perilaku mock branchService.getById
        when(branchService.getBranchById(eq("branch123"))).thenReturn(dummyBranch);

        // Mengirimkan permintaan HTTP GET ke endpoint controller dengan ID asset
        mockMvc.perform(get("/branch/branch123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.message").value("ok"))
                .andExpect(jsonPath("$.data.id").value(dummyBranch.getId()))
                .andExpect(jsonPath("$.data.branchName").value(dummyBranch.getBranchName()));

        // Verifikasi pemanggilan assetService.getById
        verify(branchService, times(1)).getBranchById(eq("branch123"));
    }

    @Test
    void getAllBranch() throws Exception {
        BranchResponse dummyBranch = new BranchResponse();
        dummyBranch.setId("branch123");
        dummyBranch.setBranchName("Cabang Bandung");

        BranchResponse dummyBranch2 = new BranchResponse();
        dummyBranch2.setId("branch456");
        dummyBranch2.setBranchName("Cabang NTT");

        List<BranchResponse> dummyBranches = Arrays.asList(dummyBranch, dummyBranch2);

        when(branchService.getAllBranch()).thenReturn(dummyBranches);

        mockMvc.perform(get("/branch"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.message").value("ok"))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].id").value(dummyBranch.getId()))
                .andExpect(jsonPath("$.data[0].branchName").value(dummyBranch.getBranchName()))
                .andExpect(jsonPath("$.data[1].id").value(dummyBranch2.getId()))
                .andExpect(jsonPath("$.data[1].branchName").value(dummyBranch2.getBranchName()));

        verify(branchService, times(1)).getAllBranch();
    }

    @Test
    void updateBranch() throws Exception {
        // Data dummy branch yang akan diperbarui
        BranchResponse dummyBranch = new BranchResponse();
        dummyBranch.setId("branch123");
        dummyBranch.setBranchName("Updated Dummy Branch");

        // Mengatur perilaku mock branchService.update
        when(branchService.updateBranch(any(UpdateBranchRequest.class))).thenReturn(dummyBranch);

        // Mengirimkan permintaan HTTP PUT ke endpoint controller
        mockMvc.perform(put("/branch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dummyBranch)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.message").value("branch updated"))
                .andExpect(jsonPath("$.data.id").value(dummyBranch.getId()))
                .andExpect(jsonPath("$.data.branchName").value(dummyBranch.getBranchName()));

        // Verifikasi pemanggilan branchService.update
        verify(branchService, times(1)).updateBranch(any(UpdateBranchRequest.class));
    }

    @Test
    void deleteBranch() throws Exception {
        // ID branch yang akan dihapus
        String branchId = "branch123";

        // Mengirimkan permintaan HTTP DELETE ke endpoint controller
        mockMvc.perform(delete("/branch/{id}", branchId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.message").value("branch deleted"));

        // Verifikasi pemanggilan branchService.deleteById
        verify(branchService, times(1)).deleteBranchById(eq(branchId));
    }
}