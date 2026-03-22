package com.Rim.Account_Book.controller;

import com.Rim.Account_Book.domain.AccountBook;
import com.Rim.Account_Book.service.AccountBookService;
import com.Rim.Account_Book.domain.CategorySumDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/records")
@RequiredArgsConstructor
public class AccountBookController {

    private final AccountBookService accountBookService;

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody AccountBook record) {
        Long id = accountBookService.saveRecord(record);
        return ResponseEntity.ok(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountBook> getRecord(@PathVariable Long id) {
        AccountBook record = accountBookService.getRecord(id);
        return ResponseEntity.ok(record);
    }

    @GetMapping
    public ResponseEntity<List<AccountBook>> getAllRecords() {
        List<AccountBook> records = accountBookService.getAllRecords();
        return ResponseEntity.ok(records);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody AccountBook record) {
        accountBookService.updateRecord(id, record);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        accountBookService.deleteRecord(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/statistics")
    public ResponseEntity<List<CategorySumDto>> getStatistics() {
        return ResponseEntity.ok(accountBookService.getCategoryStatistics());
    }

    @GetMapping("/stats/total")
    public ResponseEntity<Long> getTotalAmount (
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        return ResponseEntity.ok(accountBookService.getTotalAmountByPeriod(startDate, endDate));
    }

    @GetMapping("/stats/today")
    public ResponseEntity<List<CategorySumDto>> getTodayStats() {
        return ResponseEntity.ok(accountBookService.getTodayCategoryStats());
    }

    @GetMapping("/stats/period")
    public ResponseEntity<List<CategorySumDto>> getPeriodCategoryStats(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false, defaultValue = "amount_desc") String sort) {

        return ResponseEntity.ok(accountBookService.getCategoryStatsByPeriod(startDate, endDate, sort));
    }

    @GetMapping("/search")
    public ResponseEntity<List<AccountBook>> searchRecords(@RequestParam String keyword) {
        return ResponseEntity.ok(accountBookService.searchByMemo(keyword));
    }

    @GetMapping("/search/category")
    public ResponseEntity<List<AccountBook>> searchByCategory(@RequestParam String category) {
        return ResponseEntity.ok(accountBookService.searchByCategory(category));
    }

    @GetMapping("/search/amount")
    public ResponseEntity <List<AccountBook>> searchByAmount (
            @RequestParam Long min,
            @RequestParam Long max,
            @RequestParam(required = false, defaultValue = "desc") String sort) {
        return ResponseEntity.ok(accountBookService.searchByAmount(min, max, sort));
    }
}


