package com.sample.WireMockSpringIntegration.service;

import com.sample.WireMockSpringIntegration.model.GiftCardResponse;
import com.sample.WireMockSpringIntegration.model.IssueGiftCard;
import com.sample.WireMockSpringIntegration.model.RedeemGiftCard;
import org.springframework.stereotype.Service;

@Service
public class GiftCardService {

	public GiftCardResponse issue(IssueGiftCard issueGiftCard) {
		return GiftCardResponse.builder()
				.accountNumber(issueGiftCard.getAccountNumber())
				.responseCode("01")
				.responseStatus("Approval")
				.build();
	}

	public GiftCardResponse redeem(RedeemGiftCard redeemGiftCard) {
		return GiftCardResponse.builder()
				.accountNumber(redeemGiftCard.getAccountNumber())
				.responseCode("01")
				.responseStatus("Approval")
				.build();
	}
}
