package ro.msg.cristian.testappmvn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.msg.cristian.testappmvn.dto.TransactionDto;
import ro.msg.cristian.testappmvn.exception.BusinessException;
import ro.msg.cristian.testappmvn.service.StockService;

@RequestMapping("/stock")
@RestController
public class StockController {
    private StockService stockService;

    @Autowired
    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @PostMapping("/buy")
    public ResponseEntity<Boolean> buyStocks(@RequestBody final TransactionDto transactionDto) throws BusinessException {
        return ResponseEntity.ok(stockService.buyStocks(transactionDto));
    }
}
