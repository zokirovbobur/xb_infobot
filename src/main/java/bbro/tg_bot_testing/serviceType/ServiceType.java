package bbro.tg_bot_testing.serviceType;

import bbro.tg_bot_testing.category.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ServiceType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long serviceId;

    private String ServiceType;
    private String ServiceDefinition;

    @ManyToOne
    private Category category;
}
