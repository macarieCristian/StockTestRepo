package ro.msg.cristian.testappmvn.domain;

import lombok.*;
import ro.msg.cristian.testappmvn.domain.enums.TransactionType;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class Transaction extends BaseEntity<Long> {
    private Double value;
    private Long quantity;
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @ManyToOne(fetch = FetchType.LAZY)
    private AppUser appUser;

    @ManyToOne(fetch = FetchType.LAZY)
    private Company company;

    public Transaction(Long id, Double value, Long quantity, LocalDate date, TransactionType transactionType, String companyName, Double stockValue) {
        super(id);
        this.value = value;
        this.quantity = quantity;
        this.date = date;
        this.transactionType = transactionType;
        this.company = Company.builder()
                .name(companyName)
                .stockValue(stockValue)
                .build();
    }
}
