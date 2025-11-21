package com.fiap.gs2025.IncludIA_Java.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/health")
public class HealthController {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping
    public ResponseEntity<Map<String, Object>> checkHealth() {
        Map<String, Object> status = new HashMap<>();
        boolean allUp = true;

        try (Connection conn = dataSource.getConnection()) {
            status.put("database", conn.isValid(2) ? "UP" : "DOWN");
        } catch (Exception e) {
            status.put("database", "DOWN: " + e.getMessage());
            allUp = false;
        }

        try {
            redisTemplate.opsForValue().set("health-check", "ok");
            String value = redisTemplate.opsForValue().get("health-check");
            status.put("redis", "ok".equals(value) ? "UP" : "DOWN");
        } catch (Exception e) {
            status.put("redis", "DOWN: " + e.getMessage());
            allUp = false;
        }

        try {
            rabbitTemplate.execute(channel -> {
                return channel.isOpen() ? "UP" : "DOWN";
            });
            status.put("rabbitmq", "UP");
        } catch (Exception e) {
            status.put("rabbitmq", "DOWN: " + e.getMessage());
            allUp = false;
        }

        try {
            String aiUrl = "https://app-includia-iot-2771.azurewebsites.net/health";
            ResponseEntity<String> response = restTemplate.getForEntity(aiUrl, String.class);
            status.put("external_ai_api", response.getStatusCode().is2xxSuccessful() ? "UP" : "DOWN");
        } catch (Exception e) {
            status.put("external_ai_api", "DOWN: " + e.getMessage());
        }

        status.put("overall_status", allUp ? "HEALTHY" : "UNHEALTHY");
        return ResponseEntity.status(allUp ? 200 : 503).body(status);
    }
}