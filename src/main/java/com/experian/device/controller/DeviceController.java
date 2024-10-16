package com.experian.device.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.experian.device.model.Device;
import com.experian.device.service.DeviceService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/devices")
public class DeviceController {

    private final DeviceService service;

    public DeviceController(DeviceService service) {
        this.service = service;
    }

    @PostMapping("/match")
    public ResponseEntity<Device> matchDevice(@RequestBody Device device) {
        Device matchedDevice = service.matchDevice(device);
        return ResponseEntity.ok(matchedDevice);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Device> getDeviceById(@PathVariable String id) {
        Optional<Device> device = service.getDeviceById(id);
        return device.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDevice(@PathVariable String id) {
        service.deleteDevice(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/os/{osName}")
    public ResponseEntity<List<Device>> getDevicesByOsName(@PathVariable String osName) {
        List<Device> devices = service.getDevicesByOsName(osName);
        if (devices.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(devices);
    }
    
}
