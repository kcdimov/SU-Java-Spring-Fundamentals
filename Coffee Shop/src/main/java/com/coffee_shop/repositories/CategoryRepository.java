package com.coffee_shop.repositories;

import com.coffee_shop.models.entities.CategoryEntity;
import com.coffee_shop.models.entities.CategoryNameEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    Optional<CategoryEntity> findByName(CategoryNameEnum name);

}
