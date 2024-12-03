package com.imanzi.payment_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.time.OffsetDateTime;

@Setter
@Getter
public class PaymentItemDTO {

    @Schema(description = "Unit amount for the payment item", example = "2000")
    private int unitAmount;

    @Schema(description = "Quantity of the payment item", example = "1")
    private int quantity;

    @Schema(description = "Payment item code", example = "PI-3e5fe23f2d")
    private String code;

    @Schema(description = "Expiry date and time of the payment item", example = "2019-08-24T14:15:22Z")
    private OffsetDateTime expiryAt;

}
