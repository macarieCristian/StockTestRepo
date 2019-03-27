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
public class AppUser extends BaseEntity<Long> {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String personalNumber;
    private Double currency;

    @OneToMany(mappedBy = "appUser")
    private Set<Transaction> transactions;

    @OneToMany(mappedBy = "appUser")
    private Set<Stock> stocks;
}
