package bbro.tg_bot_testing.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("category")
public class CategoryController {
    @Autowired
    private CategoryRepo categoryRepo;

    @GetMapping
    public Category category(){return new Category();}

    @PostMapping
    public boolean save(@RequestBody Category category){
        categoryRepo.save(category);
        return true;
    }
    @GetMapping("all")
    public List<Category> getAll(){
        return categoryRepo.findAll();
    }
}
