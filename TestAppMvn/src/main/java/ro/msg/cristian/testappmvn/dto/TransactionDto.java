package ro.msg.cristian.testappmvn.dto;

import lombok.*;

import javax.persistence.Entity;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TransactionDto implements Serializable {
    private Long buyerId;
    private Long sellerId;
    private Long companyId;
    private Long quantity;
}
