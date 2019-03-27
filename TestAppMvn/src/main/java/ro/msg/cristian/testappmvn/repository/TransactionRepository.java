package ro.msg.cristian.testappmvn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ro.msg.cristian.testappmvn.domain.Transaction;

import java.time.LocalDate;
import java.util.Set;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("select new ro.msg.cristian.testappmvn.domain.Transaction(t.id, t.value, t.quantity, t.date, t.transactionType, c.name, c.stockValue) " +
            "from Transaction t " +
            "join t.company c " +
            "where t.appUser.id = :userId and t.date between :startDate and :endDate")
    Set<Transaction> getTransactionsForUserInInterval(@Param("userId") Long userId,
                                                      @Param("startDate") LocalDate startDate,
                                                      @Param("endDate") LocalDate endDate);

}
