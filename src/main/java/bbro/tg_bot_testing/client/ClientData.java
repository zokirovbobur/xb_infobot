package bbro.tg_bot_testing.client;

import bbro.tg_bot_testing.serviceItem.ServiceItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ClientData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long clientId;
    private String userName;
    private String clientPhone;
    private int age;
    private boolean isActive;
    private Long chatId;

    @ManyToOne
    private ServiceItem serviceItem;
}
