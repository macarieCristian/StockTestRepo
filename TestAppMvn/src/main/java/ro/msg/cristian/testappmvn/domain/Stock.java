package ro.msg.cristian.testappmvn.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class Stock extends BaseEntity<Long> {
    private Long quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    private AppUser appUser;

    @ManyToOne(fetch = FetchType.LAZY)
    private Company company;

    public Stock(Long id, Long quantity, String companyName, Double stockValue) {
        super(id);
        this.quantity = quantity;
        this.company = Company.builder()
                .name(companyName)
                .stockValue(stockValue)
                .build();
    }
}
