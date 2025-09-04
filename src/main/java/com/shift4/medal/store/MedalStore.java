package com.shift4.medal.store;

import java.util.EnumMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Component;

import com.shift4.medal.enums.MedalType;

@Component
public class MedalStore {
    private final EnumMap<MedalType, AtomicLong> totals = new EnumMap<>(MedalType.class);

    public MedalStore() {
        for (MedalType t : MedalType.values()) totals.put(t, new AtomicLong(0));
    }

    public void addPoints(MedalType medalType, int points) {
        totals.get(medalType).addAndGet(points);
    }

    public Map<MedalType, Long> snapshot() {
        EnumMap<MedalType, Long> copy = new EnumMap<>(MedalType.class);
        totals.forEach((k, v) -> copy.put(k, v.get()));
        return copy;
    }
}
