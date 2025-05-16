package com.jsya.dongbu.controller;

import com.jsya.dongbu.common.response.ApiResponse;
import com.jsya.dongbu.common.response.PageResponse;
import com.jsya.dongbu.model.History;
import com.jsya.dongbu.model.sdo.HistoryCdo;
import com.jsya.dongbu.model.sdo.HistoryRdo;
import com.jsya.dongbu.model.sdo.HistoryUdo;
import com.jsya.dongbu.service.HistoryService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/historys")
@AllArgsConstructor
public class HistoryController {

    private final HistoryService historyService;

    @PostMapping("/register")
    public ApiResponse<String>  registerHistory(@RequestBody HistoryCdo historyCdo) {
        String id = historyService.registerHistory(historyCdo);
        return ApiResponse.ok(id);
    }

    @PostMapping("/modify")
    public ApiResponse<String> modifyHistory(@RequestBody HistoryUdo historyUdo) {
        String id = historyService.modifyHistory(historyUdo);
        return ApiResponse.ok(id);
    }

    @PostMapping("/remove")
    public ApiResponse<String> removeHistory(@RequestBody String historyId) {
        historyService.removeHistory(historyId);
        return ApiResponse.ok(historyId);
    }

    @PostMapping("/find")
    public ApiResponse<History>  findHistoryById(@RequestBody String historyId) {
        History history = historyService.findHistoryById(historyId);
        return ApiResponse.ok(history);
    }

    @PostMapping("/find-all")
    public ApiResponse<List<HistoryRdo>>  findHistorys() {
        List<HistoryRdo> historys = historyService.findHistorys();
        return ApiResponse.ok(historys);
    }

    @GetMapping("/find-by-page")
    public ApiResponse<PageResponse<History>> findHistorysByPage(Pageable pageable) {
        return ApiResponse.ok(historyService.findHistorysByPage(pageable));
    }

    @GetMapping("/find-by-member-by-page")
    public ApiResponse<PageResponse<History>> findHistorysByMemberByPage(
            @RequestParam long memberId,
            @PageableDefault(size = 20, sort = "name") Pageable pageable) {
        return ApiResponse.ok(historyService.findHistorysByMember(memberId, pageable));
    }
}
