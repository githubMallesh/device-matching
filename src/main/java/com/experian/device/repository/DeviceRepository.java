package com.experian.device.repository;

import com.aerospike.client.*;
import com.aerospike.client.Record;
import com.aerospike.client.query.Filter;
import com.aerospike.client.query.RecordSet;
import com.aerospike.client.query.Statement;
import com.experian.device.model.Device;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class DeviceRepository {

    private final AerospikeClient client;
    private final String namespace;

    public DeviceRepository(AerospikeClient client, @Value("${aerospike.namespace}") String namespace) {
        this.client = client;
        this.namespace = namespace;
    }

    public void saveDevice(Device device) {
        Key key = new Key(namespace, "devices", device.getDeviceId());
        Bin[] bins = {
                new Bin("osName", device.getOsName()),
                new Bin("osVersion", device.getOsVersion()),
                new Bin("browserName", device.getBrowserName()),
                new Bin("browserVersion", device.getBrowserVersion()),
                new Bin("hitCount", device.getHitCount())
        };
        client.put(null, key, bins);
    }

    public Optional<Device> findById(String deviceId) {
        Key key = new Key(namespace, "devices", deviceId);
        Record record = client.get(null, key);
        if (record == null) return Optional.empty();
        return Optional.of(mapRecordToDevice(deviceId, record));
    }

    public void deleteById(String deviceId) {
        Key key = new Key(namespace, "devices", deviceId);
        client.delete(null, key);
    }

    public List<Device> findByOsName(String osName) {
        List<Device> devices = new ArrayList<>();
        Statement stmt = new Statement();
        stmt.setNamespace(namespace);
        stmt.setSetName("devices");
        stmt.setFilter(Filter.equal("osName", osName));

        try (RecordSet rs = client.query(null, stmt)) {
            while (rs.next()) {
                Key key = rs.getKey();
                String deviceId = (key.userKey != null) ? key.userKey.toString() : "unknown";  // Null-safe
                Record record = rs.getRecord();
                devices.add(mapRecordToDevice(deviceId, record));
            }
        }
        return devices;
    }
    
    private Device mapRecordToDevice(String deviceId, Record record) {
        return new Device(
                deviceId,
                record.getString("osName"),
                record.getString("osVersion"),
                record.getString("browserName"),
                record.getString("browserVersion"),
                record.getInt("hitCount")
        );
    }
}
