package com.shift4.medal.controller;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class MedalControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void readmeFlow_registersMedals_andReturnsExpectedRating() throws Exception {
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

		// 6. GET rating
		mockMvc.perform(get("/api/medals/rating"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.rating.bronze", equalTo(2)))
			.andExpect(jsonPath("$.rating.silver", equalTo(2)))
			.andExpect(jsonPath("$.rating.gold", equalTo(6)));
	}

	@Test
	void register_invalidType_returns400WithUnsupportedMessage() throws Exception {
		mockMvc.perform(post("/api/medals/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"medalType\":\"platinum\"}"))
			.andExpect(status().isBadRequest())
			.andExpect(content().string(containsString("not supported")));
	}

	@Test
	void register_missingMedalType_returns400ValidationError() throws Exception {
		mockMvc.perform(post("/api/medals/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{}"))
			.andExpect(status().isBadRequest())
			.andExpect(content().string(containsString("must not be null")));
	}

	@Test
	void register_malformedJson_returns400ParseError() throws Exception {
		mockMvc.perform(post("/api/medals/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{"))
			.andExpect(status().isBadRequest())
			.andExpect(content().string(containsString("JSON parse error")));
	}
}
