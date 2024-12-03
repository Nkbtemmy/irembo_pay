package com.imanzi.payment_service.services;

import com.imanzi.payment_service.dto.InvoicePayloadDTO;
import com.imanzi.payment_service.dto.InvoiceRequestDTO;
import com.imanzi.payment_service.dto.PaymentResponseDTO;
import com.imanzi.payment_service.dto.PaymentItemDTO;
import com.imanzi.payment_service.utils.OkHttpUtil;
import lombok.RequiredArgsConstructor;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class IremboPayInvoiceService {

    private final OkHttpUtil okHttpUtil;

    @Value("${irembo.pay.api_url}")
    private String apiUrl;

    @Value("${irembo.pay.secret_key}")
    private String secretKey;

    public PaymentResponseDTO createInvoice(InvoiceRequestDTO requestDTO) {
        String url = apiUrl + "/payments/invoices";

        JSONObject payload = new JSONObject();
        payload.put("transactionId", requestDTO.getTransactionId());
        payload.put("paymentAccountIdentifier", requestDTO.getPaymentAccountIdentifier());

        JSONObject customer = new JSONObject();
        customer.put("email", requestDTO.getCustomer().getEmail());
        customer.put("phoneNumber", requestDTO.getCustomer().getPhoneNumber());
        customer.put("name", requestDTO.getCustomer().getName());
        payload.put("customer", customer);

        JSONArray paymentItems = new JSONArray();
        requestDTO.getPaymentItems().forEach(item -> {
            JSONObject paymentItem = new JSONObject();
            paymentItem.put("unitAmount", item.getUnitAmount());
            paymentItem.put("quantity", item.getQuantity());
            paymentItem.put("code", item.getCode());
            paymentItems.put(paymentItem);
        });
        payload.put("paymentItems", paymentItems);
        payload.put("description", requestDTO.getDescription());
        payload.put("expiryAt", requestDTO.getExpiryAt());
        payload.put("language", requestDTO.getLanguage());

        try {
            RequestBody body = RequestBody.create(payload.toString(), MediaType.get("application/json; charset=utf-8"));
            Response response = okHttpUtil.sendRequest(url, "POST", secretKey, body);

            PaymentResponseDTO responseDTO = new PaymentResponseDTO();
            responseDTO.setSuccess(response.isSuccessful());
            responseDTO.setMessage(response.isSuccessful() ? "Invoice created successfully" : "Invoice creation failed");
            responseDTO.setData(response.body() != null ? response.body().string() : null);
            return responseDTO;

        } catch (IOException e) {
            throw new RuntimeException("Failed to create invoice", e);
        }
    }

    public PaymentResponseDTO batchInvoice(InvoicePayloadDTO invoicePayloadDTO) {
        String url = apiUrl + "/payments/invoices/batch";

        JSONObject payload = new JSONObject();
        payload.put("invoiceNumbers", invoicePayloadDTO.getInvoiceNumbers());
        payload.put("transactionId", invoicePayloadDTO.getTransactionId());
        payload.put("description", invoicePayloadDTO.getDescription());

        try{
            RequestBody body = RequestBody.create(
                    payload.toString(),
                    MediaType.parse("application/json; charset=utf-8")
            );

            Response response = okHttpUtil.sendRequest(url, "POST", secretKey, body);

            PaymentResponseDTO responseDTO = new PaymentResponseDTO();
            responseDTO.setSuccess(response.isSuccessful());
            responseDTO.setMessage(response.isSuccessful() ? "Invoice batch created successfully" : "Invoice batch creation failed");
            responseDTO.setData(response.body() != null ? response.body().string() : null);
            return responseDTO;
        }catch (IOException e){
            throw new RuntimeException("Failed to create batch invoice", e);
        }
    }

    public PaymentResponseDTO getInvoice(String invoiceNumber) {
        String url = apiUrl + "/payments/invoices/"+ invoiceNumber;
        try{

            Response response = okHttpUtil.sendRequest(url, "GET", secretKey);

            PaymentResponseDTO responseDTO = new PaymentResponseDTO();
            responseDTO.setSuccess(response.isSuccessful());
            responseDTO.setMessage(response.isSuccessful() ? "Invoice batch created successfully" : "Invoice batch creation failed");
            responseDTO.setData(response.body() != null ? response.body().string() : null);
            return responseDTO;
        }catch (IOException e){
            throw new RuntimeException("Failed to create batch invoice", e);
        }
    }

    public PaymentResponseDTO updateInvoice(String invoiceNumber, PaymentItemDTO requestDTO) {
        String url = apiUrl + "/payments/invoices" + invoiceNumber;

//        JSONObject payload = new JSONObject();
//        payload.put("transactionId", requestDTO.getTransactionId());
//        payload.put("paymentAccountIdentifier", requestDTO.getPaymentAccountIdentifier());
//
//        JSONObject customer = new JSONObject();
//        customer.put("email", requestDTO.getCustomer().getEmail());
//        customer.put("phoneNumber", requestDTO.getCustomer().getPhoneNumber());
//        customer.put("name", requestDTO.getCustomer().getName());
//        payload.put("customer", customer);

//
//        JSONArray paymentItems = new JSONArray();
//        requestDTO.getPaymentItems().forEach(item -> {
//            JSONObject paymentItem = new JSONObject();
//            paymentItem.put("unitAmount", item.getUnitAmount());
//            paymentItem.put("quantity", item.getQuantity());
//            paymentItem.put("code", item.getCode());
//            paymentItems.put(paymentItem);
//        });
//        payload.put("paymentItems", paymentItems);
//        payload.put("description", requestDTO.getDescription());
//        payload.put("expiryAt", requestDTO.getExpiryAt());
//        payload.put("language", requestDTO.getLanguage());


        JSONObject payload = new JSONObject();
        payload.put("expiryAt", "2024-09-30T14:15:22Z");

        JSONArray paymentItems = new JSONArray();
        JSONObject paymentItem = new JSONObject();
        paymentItem.put("unitAmount", requestDTO.getUnitAmount());
        paymentItem.put("quantity", requestDTO.getQuantity());
        paymentItem.put("code", requestDTO.getCode());
        paymentItems.put(paymentItem);
        payload.put("paymentItems", paymentItems);

        try {
            RequestBody body = RequestBody.create(payload.toString(), MediaType.get("application/json; charset=utf-8"));
            Response response = okHttpUtil.sendRequest(url, "PUT", secretKey, body);

            PaymentResponseDTO responseDTO = new PaymentResponseDTO();
            responseDTO.setSuccess(response.isSuccessful());
            responseDTO.setMessage(response.isSuccessful() ? "Invoice updated successfully" : "Invoice update failed");
            responseDTO.setData(response.body() != null ? response.body().string() : null);
            return responseDTO;

        } catch (IOException e) {
            throw new RuntimeException("Failed to update invoice", e);
        }
    }



}

