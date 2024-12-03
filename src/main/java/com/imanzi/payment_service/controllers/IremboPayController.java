package com.imanzi.payment_service.controllers;

import com.imanzi.payment_service.dto.*;
import com.imanzi.payment_service.services.IremboPayInvoiceService;
import com.imanzi.payment_service.services.IremboPaymentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/irembo/payments")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "IREMBO Payment")
@AllArgsConstructor
public class IremboPayController {

    private final IremboPayInvoiceService invoiceService;
    private final IremboPaymentService iremboPaymentService;

    // Create an invoice
    @PostMapping("/initiate")
    public ResponseEntity<PaymentResponseDTO> initiatePayment(@RequestBody PaymentInitiationDTO requestDTO) {
        PaymentResponseDTO response = iremboPaymentService.initiatePayment(requestDTO);
        return ResponseEntity.status(response.isSuccess() ? 200 : 400).body(response);
    }

    // Batch invoice creation
    @PostMapping("/notification")
    public ResponseEntity<Boolean> notification() {
        Boolean response = iremboPaymentService.verifySignature();
        return ResponseEntity.status(response.equals(Boolean.TRUE) ? 200 : 400).body(response);
    }

}
