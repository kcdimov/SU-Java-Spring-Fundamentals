package com.coffee_shop.repositories;

import com.coffee_shop.models.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsernameAndPassword(String username, String password);

    @Query("SELECT u FROM UserEntity AS u ORDER BY size(u.orders) DESC ")
    List<UserEntity> findAllByOrdersByDesc();

    Optional<UserEntity> findByUsernameIgnoreCase(String username);
}
