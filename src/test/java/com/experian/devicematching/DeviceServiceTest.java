package com.experian.devicematching;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.experian.device.model.Device;
import com.experian.device.repository.DeviceRepository;
import com.experian.device.service.DeviceService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeviceServiceTest {

    private DeviceRepository repository;
    private DeviceService service;

    @BeforeEach
    void setUp() {
        repository = mock(DeviceRepository.class);  // Mock the repository
        service = new DeviceService(repository);    // Inject the mock into the service
    }

    @Test
    void testMatchDevice_NewDevice() {
        Device device = new Device(
                UUID.randomUUID().toString(), "Windows", "10", "Chrome", "89.0", 0);

        when(repository.findById(device.getDeviceId())).thenReturn(Optional.empty());

        Device matchedDevice = service.matchDevice(device);

        assertNotNull(matchedDevice.getDeviceId());
        assertEquals(0, matchedDevice.getHitCount());
        verify(repository, times(1)).saveDevice(device);  // Verify save operation
    }

    @Test
    void testMatchDevice_ExistingDevice() {
        Device existingDevice = new Device(
                "device123", "Windows", "10", "Chrome", "89.0", 1);

        when(repository.findById("device123")).thenReturn(Optional.of(existingDevice));

        Device matchedDevice = service.matchDevice(existingDevice);

        assertEquals(2, matchedDevice.getHitCount());  // Hit count incremented
        verify(repository, times(1)).saveDevice(matchedDevice);
    }

    @Test
    void testGetDeviceById_Found() {
        Device device = new Device(
                "device123", "Windows", "10", "Chrome", "89.0", 1);

        when(repository.findById("device123")).thenReturn(Optional.of(device));

        Optional<Device> result = service.getDeviceById("device123");

        assertTrue(result.isPresent());
        assertEquals("Windows", result.get().getOsName());
    }

    @Test
    void testGetDevicesByOsName() {
        Device device1 = new Device("device1", "Windows", "10", "Chrome", "89.0", 1);
        Device device2 = new Device("device2", "Windows", "11", "Firefox", "80.0", 1);

        when(repository.findByOsName("Windows")).thenReturn(List.of(device1, device2));

        List<Device> devices = service.getDevicesByOsName("Windows");

        assertEquals(2, devices.size());
        verify(repository, times(1)).findByOsName("Windows");
    }
}
