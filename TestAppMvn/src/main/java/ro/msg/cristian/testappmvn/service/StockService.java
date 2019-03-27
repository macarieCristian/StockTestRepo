package ro.msg.cristian.testappmvn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.msg.cristian.testappmvn.domain.AppUser;
import ro.msg.cristian.testappmvn.domain.Company;
import ro.msg.cristian.testappmvn.domain.Stock;
import ro.msg.cristian.testappmvn.domain.Transaction;
import ro.msg.cristian.testappmvn.domain.enums.TransactionType;
import ro.msg.cristian.testappmvn.dto.TransactionDto;
import ro.msg.cristian.testappmvn.exception.BusinessException;
import ro.msg.cristian.testappmvn.repository.AppUserRepository;
import ro.msg.cristian.testappmvn.repository.CompanyRepository;
import ro.msg.cristian.testappmvn.repository.StockRepository;
import ro.msg.cristian.testappmvn.repository.TransactionRepository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
public class StockService {
    private AppUserRepository appUserRepository;
    private StockRepository stockRepository;
    private TransactionRepository transactionRepository;
    private CompanyRepository companyRepository;

    @Autowired
    public StockService(AppUserRepository appUserRepository, StockRepository stockRepository, TransactionRepository transactionRepository, CompanyRepository companyRepository) {
        this.appUserRepository = appUserRepository;
        this.stockRepository = stockRepository;
        this.transactionRepository = transactionRepository;
        this.companyRepository = companyRepository;
    }

    @Transactional
    public Boolean buyStocks(TransactionDto transactionDto) throws BusinessException {
        Stock sellerStock = stockRepository.findByCompanyIdEqualsAndAppUserIdEquals(transactionDto.getSellerId(), transactionDto.getCompanyId());
        AppUser buyer = appUserRepository.getOne(transactionDto.getBuyerId());
        AppUser seller = appUserRepository.getOne(transactionDto.getSellerId());
        Company company = companyRepository.getOne(transactionDto.getCompanyId());

        validateTransaction(sellerStock, buyer, seller, transactionDto, company);
        Double value = transactionDto.getQuantity() * company.getStockValue();

        dealWithTransaction(buyer, seller, sellerStock, transactionDto, value, company);

        dealWithStocks(sellerStock, seller, buyer, transactionDto, value, company);

        return true;
    }

    private void validateTransaction(Stock stock, AppUser buyer, AppUser seller, TransactionDto transactionDto, Company company) throws BusinessException {
        if(company == null) {
            throw new BusinessException("Company does not exist.");
        }
        if(stock == null) {
            throw new BusinessException("Stock does not exist.");
        }
        if(buyer == null || seller == null) {
            throw new BusinessException("User does not exist.");
        }
        if (buyer.getCurrency() < transactionDto.getQuantity() * stock.getCompany().getStockValue()) {
            throw new BusinessException("No enough money");
        }
        if (transactionDto.getQuantity() > stock.getQuantity()) {
            throw new BusinessException("No enough stocks");
        }
    }

    private void dealWithTransaction(AppUser buyer, AppUser seller, Stock stock, TransactionDto transactionDto, Double value, Company company) {
        Transaction transactionBuyer = Transaction.builder()
                .appUser(buyer)
                .company(stock.getCompany())
                .date(LocalDate.now())
                .transactionType(TransactionType.BUY)
                .quantity(transactionDto.getQuantity())
                .value(value)
                .build();
        buyer.getTransactions().add(transactionBuyer);
        company.getTransactions().add(transactionBuyer);
        transactionRepository.save(transactionBuyer);

        Transaction transactionSeller = Transaction.builder()
                .appUser(seller)
                .company(stock.getCompany())
                .date(LocalDate.now())
                .transactionType(TransactionType.SELL)
                .quantity(transactionDto.getQuantity())
                .value(value)
                .build();
        seller.getTransactions().add(transactionSeller);
        company.getTransactions().add(transactionSeller);
        transactionRepository.save(transactionSeller);
    }

    private void dealWithStocks(Stock sellerStock, AppUser seller, AppUser buyer, TransactionDto transactionDto, Double value, Company company) {
        sellerStock.setQuantity(sellerStock.getQuantity() - transactionDto.getQuantity());
        if (sellerStock.getQuantity().equals(0L)) {
            stockRepository.delete(sellerStock);
        }
        seller.setCurrency(seller.getCurrency() + value);
        buyer.setCurrency(buyer.getCurrency() - value);
        Stock buyerStock = stockRepository.findByCompanyIdEqualsAndAppUserIdEquals(transactionDto.getBuyerId(), transactionDto.getCompanyId());
        if (buyerStock != null) {
            buyerStock.setQuantity(buyerStock.getQuantity() + transactionDto.getQuantity());
        } else {
            Stock newStock = Stock.builder()
                    .appUser(buyer)
                    .company(company)
                    .quantity(transactionDto.getQuantity())
                    .build();
            buyer.getStocks().add(newStock);
            company.getStocks().add(newStock);
            stockRepository.save(newStock);
        }
    }

    public Double getCurrency(Long userId) {
        return appUserRepository.getCurrency(userId);
    }

    public Set<Stock> getCurrentStock(Long userId) {
        return stockRepository.getCurrentStock(userId);
    }

    public Set<Transaction> getTransactionHistory(Long userId, LocalDate startDate, LocalDate endDate) {
        return transactionRepository.getTransactionsForUserInInterval(userId, startDate, endDate);
    }
}
