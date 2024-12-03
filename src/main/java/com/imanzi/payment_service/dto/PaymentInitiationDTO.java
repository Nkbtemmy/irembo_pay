package com.imanzi.payment_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PaymentInitiationDTO {

    @Schema(description = "Telephone number of the account holder", example = "0781110011", defaultValue = "0781110011")
    private String telephoneNumber;

    @Schema(description = "Payment provider (e.g., MTN, Airtel)", example = "MTN", defaultValue = "MTN")
    private String paymentProvider;

    @Schema(description = "Invoice number for the transaction", example = "880310977877", defaultValue = "880310977877")
    private String invoiceNumber;

    @Schema(description = "Transaction reference identifier", example = "MTN_001", defaultValue = "MTN_001")
    private String transactionReference;

}
