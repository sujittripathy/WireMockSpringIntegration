package com.sample.WireMockSpringIntegration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.math.BigDecimal;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static org.assertj.core.api.Assertions.assertThat;

public class WireMockTest {

	private static final ObjectMapper objectMapper = new ObjectMapper();

	@Rule
	public WireMockRule wireMockRule = new WireMockRule(9999);

	@Before
	public void setupStubs() throws JsonProcessingException {
		stubFor(get("/sample")
				.willReturn(aResponse()
						.withStatus(200)
						.withBody("sample response from Wiremock")
						.withHeader("Content-Type", "application/json")
				)
		);

		String responseBody =
				objectMapper.writeValueAsString(new Cart(100, 5, new BigDecimal("200")));
		stubFor(get("/cart")
				.willReturn(aResponse()
						.withStatus(200)
						.withBody(responseBody)
						.withHeader("Content-Type", "application/json")
				)
		);

		stubFor(get("/error")
				.willReturn(aResponse()
						.withStatus(503)
						.withBody("Server unavailable")
						.withHeader("Content-Type", "text/html")
				)
		);
	}

	@Test
	public void testResponseAsString() throws UnirestException {
		String response =
				Unirest.get("http://localhost:9999/sample").asString().getBody();
		assertThat(response).contains("sample response from Wiremock");
	}

	@Test
	public void testResponseAsJson() throws UnirestException, JsonProcessingException {
		String responseBody =
				objectMapper.writeValueAsString(new Cart(100, 5, new BigDecimal("200")));

		String response =
				Unirest.get("http://localhost:9999/cart").asString().getBody();
		assertThat(response).isEqualTo(responseBody);

	}

	@Test
	public void testResponse503() throws UnirestException {
		HttpResponse response =
				Unirest.get("http://localhost:9999/error").asString();
		assertThat(response.getStatus()).isEqualTo(503);
		assertThat(response.getBody()).isEqualTo("Server unavailable");
	}
}


class Cart {
	private int id;
	private int numberOfItems;
	private BigDecimal totalAmount;

	Cart(int id, int numberOfItems, BigDecimal totalAmount) {
		this.id = id;
		this.numberOfItems = numberOfItems;
		this.totalAmount = totalAmount;
	}

	public int getId() {
		return id;
	}

	public int getNumberOfItems() {
		return numberOfItems;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
}
