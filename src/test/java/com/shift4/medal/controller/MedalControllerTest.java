package com.shift4.medal.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.EnumMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.shift4.medal.controller.error.GlobalExceptionHandler;
import com.shift4.medal.dto.MedalRatingResponse;
import com.shift4.medal.dto.MedalRegisterRequest;
import com.shift4.medal.enums.MedalType;
import com.shift4.medal.service.MedalService;

@WebMvcTest(controllers = MedalController.class)
@Import(GlobalExceptionHandler.class)
class MedalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MedalService service;

    @TestConfiguration
    static class MockConfig {
        @Bean
        MedalService medalService() {
            return Mockito.mock(MedalService.class);
        }
    }

    @Test
    void flowFromReadme_registersMedals_thenReturnsExpectedRating() throws Exception {
        // 1. bronze
        mockMvc.perform(post("/api/medals/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"medalType\":\"bronze\"}"))
        .andExpect(status().isNoContent());

        // 2. silver
        mockMvc.perform(post("/api/medals/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"medalType\":\"silver\"}"))
        .andExpect(status().isNoContent());

        // 3. bronze
        mockMvc.perform(post("/api/medals/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"medalType\":\"bronze\"}"))
        .andExpect(status().isNoContent());

        // 4. gold
        mockMvc.perform(post("/api/medals/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"medalType\":\"gold\"}"))
        .andExpect(status().isNoContent());

        // 5. gold
        mockMvc.perform(post("/api/medals/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"medalType\":\"gold\"}"))
        .andExpect(status().isNoContent());

        // Verify service received expected calls
        ArgumentCaptor<MedalRegisterRequest> captor = ArgumentCaptor.forClass(MedalRegisterRequest.class);
        verify(service, times(5)).registerMedal(captor.capture());

        long bronzeCount = captor.getAllValues().stream().filter(r -> r.medalType() == MedalType.BRONZE).count();
        long silverCount = captor.getAllValues().stream().filter(r -> r.medalType() == MedalType.SILVER).count();
        long goldCount = captor.getAllValues().stream().filter(r -> r.medalType() == MedalType.GOLD).count();

        org.junit.jupiter.api.Assertions.assertEquals(2, bronzeCount);
        org.junit.jupiter.api.Assertions.assertEquals(1, silverCount);
        org.junit.jupiter.api.Assertions.assertEquals(2, goldCount);

        // Stub service rating response to expected snapshot
        Map<MedalType, Long> rating = new EnumMap<>(MedalType.class);
        rating.put(MedalType.BRONZE, 2L); // 1+1
        rating.put(MedalType.SILVER, 2L); // 2
        rating.put(MedalType.GOLD, 6L);   // 3+3
        when(service.getRatings()).thenReturn(new MedalRatingResponse(rating));

        // 6. GET rating
        mockMvc.perform(get("/api/medals/rating"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.rating.bronze", equalTo(2)))
        .andExpect(jsonPath("$.rating.silver", equalTo(2)))
        .andExpect(jsonPath("$.rating.gold", equalTo(6)));
    }

    @Test
    void register_invalidType_returns400WithMessage() throws Exception {
        mockMvc.perform(post("/api/medals/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"medalType\":\"platinum\"}"))
        .andExpect(status().isBadRequest())
        .andExpect(content().string(org.hamcrest.Matchers.containsString("not supported")));
    }

    @Test
    void register_missingMedalType_returns400ValidationError() throws Exception {
        mockMvc.perform(post("/api/medals/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
        .andExpect(status().isBadRequest())
        .andExpect(content().string(org.hamcrest.Matchers.containsString("must not be null")));
    }

    @Test
    void register_malformedJson_returns400ParseError() throws Exception {
        mockMvc.perform(post("/api/medals/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{"))
        .andExpect(status().isBadRequest())
        .andExpect(content().string(org.hamcrest.Matchers.containsString("JSON parse error")));
    }
}
