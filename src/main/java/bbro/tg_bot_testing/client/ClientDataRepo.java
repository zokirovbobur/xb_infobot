package bbro.tg_bot_testing.client;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientDataRepo extends JpaRepository<ClientData,Long> {
    //List<ClientData> findAllByActive(boolean isActive);
    ClientData findByUserName(String userName);
    ClientData findByClientPhone(String phone);
}
