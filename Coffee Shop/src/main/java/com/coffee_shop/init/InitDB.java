package com.coffee_shop.init;

import com.coffee_shop.services.CategoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InitDB implements CommandLineRunner  {

    private final CategoryService categoryService;

    public InitDB(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @Override
    public void run(String... args) throws Exception {
        categoryService.initializeCategory();
    }
}
