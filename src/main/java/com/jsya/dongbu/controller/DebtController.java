package com.jsya.dongbu.controller;

import com.jsya.dongbu.common.response.ApiResponse;
import com.jsya.dongbu.common.response.PageResponse;
import com.jsya.dongbu.model.Debt;
import com.jsya.dongbu.model.sdo.DebtCdo;
import com.jsya.dongbu.model.sdo.DebtUdo;
import com.jsya.dongbu.service.DebtService;
import com.jsya.dongbu.service.DebtService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/debts")
@AllArgsConstructor
public class DebtController {

    private final DebtService debtService;

    @PostMapping("/register")
    public ApiResponse<Long>  registerDebt(@RequestBody DebtCdo debtCdo) {
        long id = debtService.registerDebt(debtCdo);
        return ApiResponse.ok(id);
    }

    @PostMapping("/modify")
    public ApiResponse<Long> modifyDebt(@RequestBody DebtUdo debtUdo) {
        long id = debtService.modifyDebt(debtUdo);
        return ApiResponse.ok(id);
    }

    @PostMapping("/remove")
    public ApiResponse<Long> removeDebt(@RequestBody long debtId) {
        debtService.removeDebt(debtId);
        return ApiResponse.ok(debtId);
    }

    @PostMapping("/find-by-id")
    public ApiResponse<Debt>  findDebtById(@RequestBody long debtId) {
        Debt debt = debtService.findDebtById(debtId);
        return ApiResponse.ok(debt);
    }

    @PostMapping("/find-all")
    public ApiResponse<List<Debt>>  findDebts() {
        List<Debt> debts = debtService.findDebts();
        return ApiResponse.ok(debts);
    }

    @GetMapping("/find-all-by-page")
    public ApiResponse<PageResponse<Debt>> findDebtsByPage(Pageable pageable) {
        return ApiResponse.ok(debtService.findDebtsByPage(pageable));
    }
}
