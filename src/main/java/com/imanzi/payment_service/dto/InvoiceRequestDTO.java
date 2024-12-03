package com.imanzi.payment_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.OffsetDateTime;
import java.util.List;

@Data
public class InvoiceRequestDTO {

    @Schema(description = "Unique transaction identifier", example = "TST-10020")
    private String transactionId;

    @Schema(description = "Payment account identifier", example = "TST-RWF")
    private String paymentAccountIdentifier;

    @Schema(description = "Customer details")
    private CustomerDTO customer;

    @Schema(description = "List of payment items")
    private List<PaymentItemDTO> paymentItems;

    @Schema(description = "Invoice description", example = "Invoice description")
    private String description;

    @Schema(description = "Invoice expiration date and time", example = "2024-09-30T01:00:00+02:00")
    private OffsetDateTime expiryAt;

    @Schema(description = "Language of the invoice", example = "EN")
    private String language;

    @Data
    public static class CustomerDTO {

        @Schema(description = "Customer email address", example = "user@email.com")
        private String email;

        @Schema(description = "Customer phone number", example = "0780000001")
        private String phoneNumber;

        @Schema(description = "Customer full name", example = "Jixle Manzi")
        private String name;
    }

    @Data
    public static class PaymentItemDTO {

        @Schema(description = "Unit amount for the payment item", example = "2000")
        private int unitAmount;

        @Schema(description = "Quantity of the payment item", example = "1")
        private int quantity;

        @Schema(description = "Payment item code", example = "PI-3e5fe23f2d")
        private String code;
    }
}
