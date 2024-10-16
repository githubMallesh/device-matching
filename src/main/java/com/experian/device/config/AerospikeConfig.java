package com.experian.device.config;

import com.aerospike.client.AerospikeClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AerospikeConfig {

    @Bean
    AerospikeClient aerospikeClient() {
        // Replace with your Aerospike server's IP and port if running locally or in Docker.
        String aerospikeHost = "localhost";
        int aerospikePort = 3000;
        return new AerospikeClient(aerospikeHost, aerospikePort);
    }
}
