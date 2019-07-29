package com.sample.WireMockSpringIntegration.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IssueGiftCard {
	private String accountNumber;
	private String pin;
	private String amount;
}
