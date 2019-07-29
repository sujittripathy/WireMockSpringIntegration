package com.sample.WireMockSpringIntegration.controller;

import com.sample.WireMockSpringIntegration.configuration.TimedLog;
import com.sample.WireMockSpringIntegration.model.GiftCardResponse;
import com.sample.WireMockSpringIntegration.model.IssueGiftCard;
import com.sample.WireMockSpringIntegration.model.RedeemGiftCard;
import com.sample.WireMockSpringIntegration.service.GiftCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GiftCardController {

	@Autowired
	private GiftCardService giftCardService;

	@TimedLog(requestAttributes = {"accountNumber","pin"},
			responseAttributes = {"accountNumber","pin","responseStatus","responseCode"})
	@PostMapping(path = "/issue",
			consumes = "application/json", produces = "application/json")
	public ResponseEntity<GiftCardResponse> issueGiftCard(@RequestBody IssueGiftCard issueGiftCard) {
		return new ResponseEntity<>(giftCardService.issue(issueGiftCard),
				HttpStatus.OK);
	}

	@TimedLog(requestAttributes = {"accountNumber","pin"},
			responseAttributes = {"accountNumber","responseStatus","responseCode"})
	@PostMapping(path = "/redeem" ,
			consumes = "application/json", produces = "application/json")
	public ResponseEntity<GiftCardResponse> redeemGiftCard(@RequestBody RedeemGiftCard redeemGiftCard) {
		return new ResponseEntity<>(giftCardService.redeem(redeemGiftCard), HttpStatus.OK);
	}
}
