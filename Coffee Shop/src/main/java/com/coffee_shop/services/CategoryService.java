package com.coffee_shop.services;

import com.coffee_shop.models.entities.CategoryEntity;
import com.coffee_shop.models.entities.CategoryNameEnum;

public interface CategoryService {
    void initializeCategory();

    CategoryEntity findCategoryByCategoryNameEnum(CategoryNameEnum categoryNameEnum);
}
