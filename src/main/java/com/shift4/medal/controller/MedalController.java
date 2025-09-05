package com.shift4.medal.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shift4.medal.dto.MedalRatingResponse;
import com.shift4.medal.dto.MedalRegisterRequest;
import com.shift4.medal.service.MedalService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/medals")
public class MedalController {
    private final MedalService service;

    public MedalController(MedalService service) { this.service = service; }

    @PostMapping("/register")
    public ResponseEntity<Void> registerMedal(@Valid @RequestBody MedalRegisterRequest requestDto) {
        log.debug("inside registerMedal(MedalRgisterRequest) ; requestDto={}", requestDto);
        service.registerMedal(requestDto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/rating")
    public ResponseEntity<MedalRatingResponse> getRating() {
        log.debug("inside getRating()");
        return ResponseEntity.ok(service.getRatings());
    }
}
