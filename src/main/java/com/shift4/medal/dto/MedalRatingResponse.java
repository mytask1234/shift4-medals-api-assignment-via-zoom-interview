package com.shift4.medal.dto;

import java.util.Map;

import com.shift4.medal.enums.MedalType;

public record MedalRatingResponse(Map<MedalType, Long> rating) {}
