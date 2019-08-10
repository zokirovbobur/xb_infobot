package bbro.tg_bot_testing.serviceType;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceTypeRepo extends JpaRepository<ServiceType, Long> {
    List<ServiceType> findAllByCategoryCategoryId(Long categoryId);

}
