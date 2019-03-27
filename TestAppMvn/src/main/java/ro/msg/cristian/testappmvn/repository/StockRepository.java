package ro.msg.cristian.testappmvn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ro.msg.cristian.testappmvn.domain.Stock;

import java.util.Set;

public interface StockRepository extends JpaRepository<Stock, Long> {

    @Query("select s from Stock s " +
            "where s.company.id = :companyId and s.appUser.id = :appUserId")
    Stock findByCompanyIdEqualsAndAppUserIdEquals(@Param("appUserId") Long appUserId,
                                                  @Param("companyId") Long companyId);

    @Query("select new ro.msg.cristian.testappmvn.domain.Stock(s.id, s.quantity, c.name, c.stockValue) " +
            "from Stock s " +
            "join s.company c " +
            "where s.appUser.id = :userId")
    Set<Stock> getCurrentStock(@Param("userId") Long userId);
}
