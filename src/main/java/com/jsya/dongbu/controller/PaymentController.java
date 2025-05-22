package com.jsya.dongbu.controller;

import com.jsya.dongbu.common.response.ApiResponse;
import com.jsya.dongbu.common.response.PageResponse;
import com.jsya.dongbu.model.Payment;
import com.jsya.dongbu.model.sdo.PaymentCdo;
import com.jsya.dongbu.model.sdo.PaymentUdo;
import com.jsya.dongbu.service.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@AllArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/register")
    public ApiResponse<String>  registerPayment(@RequestBody PaymentCdo paymentCdo) {
        String id = paymentService.registerPayment(paymentCdo);
        return ApiResponse.ok(id);
    }

    @PostMapping("/modify")
    public ApiResponse<String> modifyPayment(@RequestBody PaymentUdo paymentUdo) {
        String id = paymentService.modifyPayment(paymentUdo);
        return ApiResponse.ok(id);
    }

    @PostMapping("/remove")
    public ApiResponse<String> removePayment(@RequestBody String paymentId) {
        paymentService.removePayment(paymentId);
        return ApiResponse.ok(paymentId);
    }

    @PostMapping("/find-by-id")
    public ApiResponse<Payment>  findPaymentById(@RequestBody String paymentId) {
        Payment payment = paymentService.findPaymentById(paymentId);
        return ApiResponse.ok(payment);
    }

    @PostMapping("/find-all")
    public ApiResponse<List<Payment>>  findPayments() {
        List<Payment> payments = paymentService.findPayments();
        return ApiResponse.ok(payments);
    }

    @GetMapping("/find-all-by-page")
    public ApiResponse<PageResponse<Payment>> findPaymentsByPage(Pageable pageable) {
        return ApiResponse.ok(paymentService.findPaymentsByPage(pageable));
    }

    @GetMapping("/find-by-history")
    public ApiResponse<List<Payment>> findPaymentsByHistory(
            @RequestParam String historyId) {
        return ApiResponse.ok(paymentService.findPaymentsByHistory(historyId));
    }

    @GetMapping("/find-by-member-by-page")
    public ApiResponse<PageResponse<Payment>> findPaymentsByMemberByPage(
            @RequestParam long memberId,
            @PageableDefault(size = 20, sort = "paymentDate") Pageable pageable) {
        return ApiResponse.ok(paymentService.findPaymentsByMemberByPage(memberId, pageable));
    }


}
