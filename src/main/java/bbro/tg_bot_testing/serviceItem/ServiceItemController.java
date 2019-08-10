package bbro.tg_bot_testing.serviceItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("worker")
public class ServiceItemController {
    @Autowired
    private ServiceItemRepo serviceItemRepo;

    @GetMapping
    public ServiceItem sample(){return new ServiceItem();}

    @PostMapping
    public String save(@RequestBody ServiceItem serviceItem){
        if(serviceItem.getServiceType()==null){
            return "please enter service type id properly";
        }else {
            serviceItemRepo.save(serviceItem);
            return "done";
        }
    }
    @GetMapping("all")
    public List<ServiceItem> findAll(){
        return serviceItemRepo.findAll();
    }
}
