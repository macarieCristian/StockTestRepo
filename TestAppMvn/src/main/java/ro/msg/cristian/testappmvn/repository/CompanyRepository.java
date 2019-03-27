package ro.msg.cristian.testappmvn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.msg.cristian.testappmvn.domain.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
