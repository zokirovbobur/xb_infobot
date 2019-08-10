package bbro.tg_bot_testing.serviceItem;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceItemRepo extends JpaRepository<ServiceItem, Long> {
    List<ServiceItem> findAllByServiceTypeServiceId(Long serviceId);
    ServiceItem findByServiceItemId(long workerId);
}
