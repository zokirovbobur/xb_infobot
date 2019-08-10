package bbro.tg_bot_testing.serviceType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("serviceType")
public class ServiceTypeController {
    @Autowired
    private ServiceTypeRepo serviceTypeRepo;

    @GetMapping
    public ServiceType sample(){return new ServiceType();}

    @PostMapping
    public String save(@RequestBody ServiceType serviceType){
        if(serviceType.getCategory()==null){
            return "please enter categoryId";
        }
        else {
            serviceTypeRepo.save(serviceType);
            return "done";
        }
    }
    @GetMapping("all")
    public List<ServiceType> findAll(){
        return serviceTypeRepo.findAll();
    }
}
