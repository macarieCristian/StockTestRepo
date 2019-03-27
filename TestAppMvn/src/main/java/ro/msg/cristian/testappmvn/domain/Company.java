package ro.msg.cristian.testappmvn.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"transactions", "stocks"})
@ToString(callSuper = true, exclude = {"transactions", "stocks"})
@Builder
public class Company extends BaseEntity<Long> {
    private String name;
    private String identifier;
    private Double stockValue;

    @OneToMany(mappedBy = "company")
    private Set<Transaction> transactions;

    @OneToMany(mappedBy = "company")
    private Set<Stock> stocks;
}
