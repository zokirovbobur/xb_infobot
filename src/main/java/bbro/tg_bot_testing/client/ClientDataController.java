package bbro.tg_bot_testing.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("clientData")
public class ClientDataController {
    @Autowired
    private ClientDataRepo clientDataRepo;

    @GetMapping
    public ClientData sample(){return new ClientData();}

    @GetMapping("all")
    public List<ClientData> getAll(){return clientDataRepo.findAll();}
}
