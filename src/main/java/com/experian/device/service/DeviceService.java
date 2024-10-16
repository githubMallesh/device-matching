package com.experian.device.service;

import org.springframework.stereotype.Service;

import com.experian.device.model.Device;
import com.experian.device.repository.DeviceRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DeviceService {

    private final DeviceRepository repository;

    public DeviceService(DeviceRepository repository) {
        this.repository = repository;
    }

    public Device matchDevice(Device device) {
        Optional<Device> existingDevice = repository.findById(device.getDeviceId());
        if (existingDevice.isPresent()) {
            Device matchedDevice = existingDevice.get();
            matchedDevice.setHitCount(matchedDevice.getHitCount() + 1);
            repository.saveDevice(matchedDevice);
            return matchedDevice;
        }
        device.setDeviceId(UUID.randomUUID().toString());
        repository.saveDevice(device);
        return device;
    }

    public Optional<Device> getDeviceById(String id) {
        return repository.findById(id);
    }

    public void deleteDevice(String id) {
        repository.deleteById(id);
    }
    
    public List<Device> getDevicesByOsName(String osName) {
        return repository.findByOsName(osName);
    }
    
}
