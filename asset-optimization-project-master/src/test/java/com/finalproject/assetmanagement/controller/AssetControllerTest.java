package com.finalproject.assetmanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finalproject.assetmanagement.model.request.AssetRequest;
import com.finalproject.assetmanagement.model.request.CreateAssetRequest;
import com.finalproject.assetmanagement.model.request.EmployeeRequest;
import com.finalproject.assetmanagement.model.request.UpdateAssetRequest;
import com.finalproject.assetmanagement.model.response.AssetResponse;
import com.finalproject.assetmanagement.model.response.EmployeeResponse;
import com.finalproject.assetmanagement.service.AssetService;
import com.finalproject.assetmanagement.service.EmployeeService;
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
class AssetControllerTest {
    @MockBean
    private AssetService assetService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createNewAsset() throws Exception {
        //data dummy asset
        AssetResponse dummyAsset = new AssetResponse();
        dummyAsset.setId("asset123");
        dummyAsset.setName("Printer");

        //mock behavior
        when(assetService.createNewAsset(any(CreateAssetRequest.class))).thenReturn(dummyAsset);

        //mengirimkan permintaan HTTP ke endpoint controller
        mockMvc.perform(post("/asset")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dummyAsset)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.statusCode").value(201))
                .andExpect(jsonPath("$.message").value("assert created"))
                .andExpect(jsonPath("$.data.id").value(dummyAsset.getId()))
                .andExpect(jsonPath("$.data.name").value(dummyAsset.getName()));

        verify(assetService, times(1)).createNewAsset(any(CreateAssetRequest.class));
    }

    @Test
    void getAssetById() throws Exception {
        // Data dummy asset
        AssetResponse dummyAsset = new AssetResponse();
        dummyAsset.setId("asset123");
        dummyAsset.setName("Dummy Asset");

        // Mengatur perilaku mock assetService.getById
        when(assetService.getAssetById(eq("asset123"))).thenReturn(dummyAsset);

        // Mengirimkan permintaan HTTP GET ke endpoint controller dengan ID asset
        mockMvc.perform(get("/asset/asset123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.message").value("ok"))
                .andExpect(jsonPath("$.data.id").value(dummyAsset.getId()))
                .andExpect(jsonPath("$.data.name").value(dummyAsset.getName()));

        // Verifikasi pemanggilan assetService.getById
        verify(assetService, times(1)).getAssetById(eq("asset123"));
    }

    @Test
    void getAllAsset() throws Exception {
        AssetResponse dummyAsset = new AssetResponse();
        dummyAsset.setId("asset123");
        dummyAsset.setName("Printer");

        AssetResponse dummyAsset2 = new AssetResponse();
        dummyAsset2.setId("asset456");
        dummyAsset2.setName("Laptop");

        List<AssetResponse> dummyAssets = Arrays.asList(dummyAsset, dummyAsset2);

        when(assetService.getAllAsset()).thenReturn(dummyAssets);

        mockMvc.perform(get("/asset"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.message").value("ok"))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].id").value(dummyAsset.getId()))
                .andExpect(jsonPath("$.data[0].name").value(dummyAsset.getName()))
                .andExpect(jsonPath("$.data[1].id").value(dummyAsset2.getId()))
                .andExpect(jsonPath("$.data[1].name").value(dummyAsset2.getName()));

        verify(assetService, times(1)).getAllAsset();
    }

    @Test
    void updateAsset() throws Exception {
        // Data dummy asset yang akan diperbarui
        AssetResponse dummyAsset = new AssetResponse();
        dummyAsset.setId("asset123");
        dummyAsset.setName("Updated Dummy Asset");

        // Mengatur perilaku mock assetService.update
        when(assetService.updateAsset(any(UpdateAssetRequest.class))).thenReturn(dummyAsset);

        // Mengirimkan permintaan HTTP PUT ke endpoint controller
        mockMvc.perform(put("/asset")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dummyAsset)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.message").value("assert updated"))
                .andExpect(jsonPath("$.data.id").value(dummyAsset.getId()))
                .andExpect(jsonPath("$.data.name").value(dummyAsset.getName()));

        // Verifikasi pemanggilan assetService.update
        verify(assetService, times(1)).updateAsset(any(UpdateAssetRequest.class));
    }

    @Test
    void deleteAsset() throws Exception {
        // ID asset yang akan dihapus
        String assetId = "asset123";

        // Mengirimkan permintaan HTTP DELETE ke endpoint controller
        mockMvc.perform(delete("/asset/{id}", assetId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.message").value("assert deleted"));

        // Verifikasi pemanggilan customerService.deleteById
        verify(assetService, times(1)).deleteAssetById(eq(assetId));
    }
}