package ro.msg.cristian.testappmvn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.msg.cristian.testappmvn.domain.Stock;
import ro.msg.cristian.testappmvn.domain.Transaction;
import ro.msg.cristian.testappmvn.service.StockService;

import java.time.LocalDate;
import java.util.Set;

@RequestMapping("/users")
@RestController
public class AppUserController {
    private StockService stockService;

    @Autowired
    public AppUserController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping("/current-currency/{userId}")
    public ResponseEntity<Double> getCurrency(@PathVariable Long userId) {
        return ResponseEntity.ok(stockService.getCurrency(userId));
    }

    @GetMapping("/current-stock/{userId}")
    public ResponseEntity<Set<Stock>> getCurrentStock(@PathVariable Long userId) {
        return ResponseEntity.ok(stockService.getCurrentStock(userId));
    }

    @GetMapping("/transaction-history")
    public ResponseEntity<Set<Transaction>> getTransactionHistory(@RequestParam Long userId,
                                                                  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam LocalDate startDate,
                                                                  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam LocalDate endDate) {
        return ResponseEntity.ok(stockService.getTransactionHistory(userId, startDate, endDate));
    }


}
