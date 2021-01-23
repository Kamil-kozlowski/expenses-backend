package com.expenses.feature.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("select e from Category e where e.id = ?1 and e.user.id = ?2")
    Optional<Category> findByIdAndUser(long id, long user);

    @Query("select e from Category e where e.user.id = ?1")
    List<Category> findAllForUser(long userId);
}
