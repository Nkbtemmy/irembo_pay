package com.imanzi.payment_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Setter
@Getter
public class InvoicePayloadDTO {

    @Schema(description = "List of invoice numbers", example = "[\"880523335346\", \"880522008336\"]")
    private List<String> invoiceNumbers;

    @Schema(description = "Unique transaction identifier", example = "TST-10010")
    private String transactionId;

    @Schema(description = "Description of the invoice batch", example = "Batch description")
    private String description;

}
