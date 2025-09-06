package com.shift4.medal.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.shift4.medal.dto.MedalRatingResponse;
import com.shift4.medal.dto.MedalRegisterRequest;
import com.shift4.medal.enums.MedalType;
import com.shift4.medal.store.MedalStore;

class MedalServiceTest {

    private MedalStore store;
    private MedalService service;

    @BeforeEach
    void setUp() {
        store = new MedalStore();
        service = new MedalService(store);
    }

    @Test
    void registerMedal_accumulatesCorrectPoints() {
        service.registerMedal(new MedalRegisterRequest(MedalType.BRONZE)); // +1
        service.registerMedal(new MedalRegisterRequest(MedalType.SILVER)); // +2
        service.registerMedal(new MedalRegisterRequest(MedalType.GOLD));   // +3

        Map<MedalType, Long> snapshot = store.snapshot();
        assertThat(snapshot, hasEntry(MedalType.BRONZE, 1L));
        assertThat(snapshot, hasEntry(MedalType.SILVER, 2L));
        assertThat(snapshot, hasEntry(MedalType.GOLD, 3L));
    }

    @Test
    void getRatings_returnsCurrentSnapshot() {
        // Prepare state via service calls
        service.registerMedal(new MedalRegisterRequest(MedalType.BRONZE));
        service.registerMedal(new MedalRegisterRequest(MedalType.BRONZE)); // total bronze: 2
        service.registerMedal(new MedalRegisterRequest(MedalType.GOLD));   // gold: 3

        MedalRatingResponse resp = service.getRatings();
        Map<MedalType, Long> expected = store.snapshot();
        assertThat(resp.rating(), equalTo(expected));
    }
}
