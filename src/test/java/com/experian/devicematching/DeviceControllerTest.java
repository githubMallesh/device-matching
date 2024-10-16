package com.experian.devicematching;

import com.experian.device.controller.DeviceController;
import com.experian.device.model.Device;
import com.experian.device.service.DeviceService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DeviceController.class)
class DeviceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DeviceService service;

    @Test
    void testGetDeviceById() throws Exception {
        Device device = new Device("device123", "Windows", "10", "Chrome", "89.0", 1);

        when(service.getDeviceById("device123")).thenReturn(Optional.of(device));

        mockMvc.perform(get("/api/devices/device123"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.osName").value("Windows"));
    }
}
