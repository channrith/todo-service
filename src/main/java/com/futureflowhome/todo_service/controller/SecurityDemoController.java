package com.futureflowhome.todo_service.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Demo endpoint to show that {@code SecurityFilterChain} rules are ordered: this path is
 * explicitly {@code permitAll()} before the broader {@code /api/**} authenticated rule.
 */
@RestController
@RequestMapping("/api/security-demo")
public class SecurityDemoController {

    @GetMapping(path = "/public", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> publicPing() {
        return Map.of(
                "message", "No JWT required — matched by permitAll before /api/** authenticated()");
    }
}
