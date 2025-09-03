package com.shift4.medal.dto;

import com.shift4.medal.enums.MedalType;

import jakarta.validation.constraints.NotNull;

public record MedalRgisterRequest(@NotNull(message = "medalType must not be null") MedalType medalType) {}
