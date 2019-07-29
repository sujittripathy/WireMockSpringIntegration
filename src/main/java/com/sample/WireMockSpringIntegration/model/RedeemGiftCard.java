package com.sample.WireMockSpringIntegration.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RedeemGiftCard {
	private String accountNumber;
	private String pin;
	private String amount;
}
