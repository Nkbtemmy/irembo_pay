package com.imanzi.payment_service.controllers;

import com.imanzi.payment_service.dto.InvoicePayloadDTO;
import com.imanzi.payment_service.dto.InvoiceRequestDTO;
import com.imanzi.payment_service.dto.PaymentResponseDTO;
import com.imanzi.payment_service.dto.PaymentItemDTO;
import com.imanzi.payment_service.services.IremboPayInvoiceService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/irembo/invoices")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "IREMBO Payment Invoice")
@AllArgsConstructor
public class IremboPayInvoiceController {

    private final IremboPayInvoiceService invoiceService;

    // Create an invoice
    @PostMapping
    public ResponseEntity<PaymentResponseDTO> createInvoice(@RequestBody InvoiceRequestDTO requestDTO) {
        PaymentResponseDTO response = invoiceService.createInvoice(requestDTO);
        return ResponseEntity.status(response.isSuccess() ? 200 : 400).body(response);
    }

    // Batch invoice creation
    @PostMapping("/batch")
    public ResponseEntity<PaymentResponseDTO> batchInvoice(@RequestBody InvoicePayloadDTO requestDTO) {
        PaymentResponseDTO response = invoiceService.batchInvoice(requestDTO);
        return ResponseEntity.status(response.isSuccess() ? 200 : 400).body(response);
    }

    // Get a single invoice
    @GetMapping("/{invoiceNumber}")
    public ResponseEntity<PaymentResponseDTO> getInvoice(@PathVariable String invoiceNumber) {
        PaymentResponseDTO response = invoiceService.getInvoice(invoiceNumber);
        return ResponseEntity.status(response.isSuccess() ? 200 : 400).body(response);
    }

    // Update an invoice with payment items
    @PutMapping("/{invoiceNumber}")
    public ResponseEntity<PaymentResponseDTO> updateInvoice(@PathVariable String invoiceNumber,
                                                            @RequestBody PaymentItemDTO requestDTO) {
        PaymentResponseDTO response = invoiceService.updateInvoice(invoiceNumber, requestDTO);
        return ResponseEntity.status(response.isSuccess() ? 200 : 400).body(response);
    }
}
