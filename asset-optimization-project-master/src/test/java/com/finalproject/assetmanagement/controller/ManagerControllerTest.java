package com.finalproject.assetmanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finalproject.assetmanagement.entity.Manager;
import com.finalproject.assetmanagement.model.request.ManagerRequest;
import com.finalproject.assetmanagement.model.response.ManagerResponse;
import com.finalproject.assetmanagement.service.ManagerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ManagerControllerTest {
    @MockBean
    private ManagerService managerService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createNewManager() throws Exception{
        //data dummy admin
        ManagerResponse dummyManager = new ManagerResponse();
        dummyManager.setId("manager123");
        dummyManager.setUsername("Suryani");

        //mock behavior
        when(managerService.createNewManager(any(ManagerRequest.class))).thenReturn(dummyManager);

        //mengirimkan permintaan HTTP ke endpoint controller
        mockMvc.perform(post("/manager")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dummyManager)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.statusCode").value(201))
                .andExpect(jsonPath("$.message").value("Successfully create new manager"))
                .andExpect(jsonPath("$.data.id").value(dummyManager.getId()))
                .andExpect(jsonPath("$.data.username").value(dummyManager.getUsername()));

        verify(managerService, times(1)).createNewManager(any(ManagerRequest.class));
    }

    @Test
    void getManagerById() throws Exception {
        // Data dummy admin
        Manager dummyManager = new Manager();
        dummyManager.setId("manager123");
        dummyManager.setUsername("Dummy manager");

        // Mengatur perilaku mock adminService.getById
        when(managerService.getManagerById(eq("manager123"))).thenReturn(dummyManager);

        // Mengirimkan permintaan HTTP GET ke endpoint controller dengan ID admin
        mockMvc.perform(get("/manager/manager123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.message").value("Successfully get manager by id"))
                .andExpect(jsonPath("$.data.id").value(dummyManager.getId()))
                .andExpect(jsonPath("$.data.username").value(dummyManager.getUsername()));

        // Verifikasi pemanggilan adminService.getById
        verify(managerService, times(1)).getManagerById(eq("manager123"));
    }

    @Test
    void getAllManager() throws Exception {
        ManagerResponse dummyManager = new ManagerResponse();
        dummyManager.setId("manager123");
        dummyManager.setUsername("Farhan");

        ManagerResponse dummyManager2 = new ManagerResponse();
        dummyManager2.setId("manager456");
        dummyManager2.setUsername("Wildan");

        List<ManagerResponse> dummyManagers = Arrays.asList(dummyManager, dummyManager2);

        when(managerService.getAllManager()).thenReturn(dummyManagers);

        mockMvc.perform(get("/manager"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.message").value("Successfully get all managers"))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].id").value(dummyManager.getId()))
                .andExpect(jsonPath("$.data[0].username").value(dummyManager.getUsername()))
                .andExpect(jsonPath("$.data[0].id").value(dummyManager.getId()))
                .andExpect(jsonPath("$.data[0].username").value(dummyManager.getUsername()));

        verify(managerService, times(1)).getAllManager();
    }

    @Test
    void updateManager() throws Exception {
        // Data dummy admin yang akan diperbarui
        ManagerResponse dummyManager = new ManagerResponse();
        dummyManager.setId("manager123");
        dummyManager.setUsername("Updated Dummy Manager");

        // Mengatur perilaku mock adminService.update
        when(managerService.updateManager(any(ManagerRequest.class))).thenReturn(dummyManager);

        // Mengirimkan permintaan HTTP PUT ke endpoint controller
        mockMvc.perform(put("/manager")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dummyManager)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.message").value("Successfully updated manager"))
                .andExpect(jsonPath("$.data.id").value(dummyManager.getId()))
                .andExpect(jsonPath("$.data.username").value(dummyManager.getUsername()));

        // Verifikasi pemanggilan adminService.update
        verify(managerService, times(1)).updateManager(any(ManagerRequest.class));
    }

    @Test
    void deleteManager() throws Exception {
        // ID admin yang akan dihapus
        String managerId = "manager123";

        // Mengirimkan permintaan HTTP DELETE ke endpoint controller
        mockMvc.perform(delete("/manager/{id}", managerId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.message").value("Successfully deleted manager"));

        // Verifikasi pemanggilan adminService.deleteById
        verify(managerService, times(1)).deleteManagerById(eq(managerId));
    }
}