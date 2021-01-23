package com.expenses.feature.expense;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    @Query("select e from Expense e where e.user.id = ?1")
    List<Expense> getAllForUser(long userId);

    @Query("select e from Expense e where e.id = ?1 and e.user.id = ?2")
    Optional<Expense> findByIdAndUser(long id, long userId);
}
