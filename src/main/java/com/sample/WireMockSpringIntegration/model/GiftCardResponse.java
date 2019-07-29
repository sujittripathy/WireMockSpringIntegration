package com.sample.WireMockSpringIntegration.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GiftCardResponse {
	private String accountNumber;
	private String responseStatus;
	private String responseCode;
}
