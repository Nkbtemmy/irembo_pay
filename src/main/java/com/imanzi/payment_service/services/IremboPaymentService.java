package com.imanzi.payment_service.services;


import com.imanzi.payment_service.dto.PaymentResponseDTO;
import com.imanzi.payment_service.dto.PaymentInitiationDTO;
import com.imanzi.payment_service.utils.OkHttpUtil;
import lombok.RequiredArgsConstructor;
import okhttp3.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
@RequiredArgsConstructor
public class IremboPaymentService {

    private final OkHttpUtil okHttpUtil;

    @Value("${irembo.pay.api_url}")
    private String apiUrl;

    @Value("${irembo.pay.secret_key}")
    private String secretKey;

    public PaymentResponseDTO initiatePayment(PaymentInitiationDTO requestDTO) {
        String url = apiUrl + "/payments/transactions/initiate";

        JSONObject payload = new JSONObject();
        payload.put("accountIdentifier", requestDTO.getTelephoneNumber());
        payload.put("paymentProvider", requestDTO.getPaymentProvider());
        payload.put("invoiceNumber", requestDTO.getInvoiceNumber());
        payload.put("transactionReference", requestDTO.getTransactionReference());

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

    public boolean verifySignature(){
        String payload = "{\"key\": \"value\"}";
        String signatureHeader = "signature_header_here";

        boolean isValid = verifySignature(secretKey, payload, signatureHeader);
        System.out.println("Signature valid: " + isValid);
        return isValid;
    }

    public static boolean verifySignature(String secretKey, String payload, String signatureHeader) {
        // Step 1: Extract the timestamp and signatures from the header
        String[] elements = signatureHeader.split(",");
        String timestamp = null;
        String signatureHash = null;

        for (String element : elements) {
            String[] parts = element.split("=");
            String prefix = parts[0];
            String value = parts[1];
            if (prefix.equals("t")) {
                timestamp = value;
            } else if (prefix.equals("s")) {
                signatureHash = value;
            }
        }

        if (timestamp == null || signatureHash == null) {
            return false;
        }

        // Step 2: Prepare the signed_payload string
        String signedPayload = timestamp + "#" + payload;

        // Step 3: Determine the expected signature
        String expectedSignature = null;
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            mac.init(secretKeySpec);
            byte[] rawHmac = mac.doFinal(signedPayload.getBytes(StandardCharsets.UTF_8));
            expectedSignature = bytesToHex(rawHmac);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
            return false;
        }

        // Step 4: Compare the signatures
        boolean isSignatureValid = expectedSignature.equals(signatureHash);

        // Optionally check if the timestamp is not too far from the current time
        long currentTime = System.currentTimeMillis(); // Current time in milliseconds
        long timestampInt = Long.parseLong(timestamp);

        if (Math.abs(currentTime - timestampInt) > 300 * 1000) { // 5 minutes tolerance in milliseconds
            return false;
        }

        return isSignatureValid;
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }


}
