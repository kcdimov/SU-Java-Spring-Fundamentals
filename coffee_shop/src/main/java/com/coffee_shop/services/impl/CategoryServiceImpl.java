package com.coffee_shop.services.impl;

import com.coffee_shop.models.entities.CategoryEntity;
import com.coffee_shop.models.entities.CategoryNameEnum;
import com.coffee_shop.repositories.CategoryRepository;
import com.coffee_shop.services.CategoryService;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    @Override
    public void initializeCategory() {
        if (this.categoryRepository.count() == 0) {
            Arrays.stream(CategoryNameEnum.values())
                    .forEach(categoryNameEnum -> {
                        CategoryEntity categoryEntity = new CategoryEntity();
                        categoryEntity.setName(categoryNameEnum);

                        switch (categoryNameEnum) {
                            case DRINK:
                                categoryEntity.setNeededTime(1);
                                break;
                            case COFFEE:
                                categoryEntity.setNeededTime(2);
                                break;
                            case OTHER:
                                categoryEntity.setNeededTime(5);
                                break;
                            case COKE:
                                categoryEntity.setNeededTime(10);
                                break;
                        }
                        categoryRepository.save(categoryEntity);
                    });

        }


    }

    @Override
    public CategoryEntity findCategoryByCategoryNameEnum(CategoryNameEnum categoryNameEnum) {
        return this.categoryRepository.findByName(categoryNameEnum).orElse(null);

    }
}
