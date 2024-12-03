package com.imanzi.payment_service.dto;

import lombok.Data;

import java.time.OffsetDateTime;
import java.util.List;

@Data
public class PaymentResponseDTO {
    private String message;
    private boolean success;
    private Object data;
}


//package com.imanzi.payment_service.dto;
//
//import lombok.Data;
//import java.time.OffsetDateTime;
//import java.util.List;
//
//@Data
//public class PaymentResponseDTO {
//
//    private String message;
//    private boolean success;
//    private InvoiceDataDTO data;
//
//    @Data
//    public static class InvoiceDataDTO {
//
//        private double amount;
//        private String invoiceNumber;
//        private String transactionId;
//        private OffsetDateTime createdAt;
//        private OffsetDateTime updatedAt;
//        private OffsetDateTime expiryAt;
//        private String paymentAccountIdentifier;
//        private List<PaymentItemDTO> paymentItems;
//        private String description;
//        private String type;
//        private String paymentStatus;
//        private String currency;
//        private CustomerDTO customer;
//        private String batchNumber;
//        private List<String> childInvoices;
//        private String paymentLinkUrl;
//
//    }
//
//    @Data
//    public static class CustomerDTO {
//        private String email;
//        private String phoneNumber;
//        private String name;
//    }
//}