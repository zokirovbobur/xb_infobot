package bbro.tg_bot_testing.serviceItem;

import bbro.tg_bot_testing.serviceType.ServiceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ServiceItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long serviceItemId;
    private String serviceItemName;
    private String serviceItemUrl;
    private String serviceItemDetail;

    @ManyToOne
    private ServiceType serviceType;
}
