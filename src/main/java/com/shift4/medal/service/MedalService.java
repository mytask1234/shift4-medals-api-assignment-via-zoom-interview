package com.shift4.medal.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.shift4.medal.dto.MedalRatingResponse;
import com.shift4.medal.dto.MedalRegisterRequest;
import com.shift4.medal.enums.MedalType;
import com.shift4.medal.store.MedalStore;

@Service
public class MedalService {
    private final MedalStore store;

    public MedalService(MedalStore store) { this.store = store; }

    public void registerMedal(MedalRegisterRequest requestDto) {
        MedalType medalType = requestDto.medalType(); // already deserialized/validated

        store.addPoints(medalType, medalType.getPoints());
    }

    public MedalRatingResponse getRatings() {
        Map<MedalType, Long> snapshot = store.snapshot();

        return new MedalRatingResponse(snapshot);
    }
}
