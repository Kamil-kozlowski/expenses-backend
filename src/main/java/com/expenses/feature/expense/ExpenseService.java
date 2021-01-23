package com.expenses.feature.expense;

import com.expenses.feature.category.CategoryDTO;
import com.expenses.feature.category.CategoryService;
import com.expenses.feature.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;
    private final CategoryService categoryService;

    public ExpenseDTO createExpense(ExpenseEditDTO body) {
        Expense expense = new Expense(
                body.getDescription(),
                body.getValue(),
                userRepository.findFirstByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).get(),
                categoryService.getById(body.getCategory())
        );
        Expense savedExpense = expenseRepository.save(expense);
        return new ExpenseDTO(
                savedExpense.getId(),
                savedExpense.getDescription(),
                savedExpense.getValue(),
                new CategoryDTO(savedExpense.getCategory().getId(), savedExpense.getCategory().getName())
        );
    }

    public List<ExpenseDTO> getAllExpenses() {
        return this.expenseRepository.getAllForUser(
                userRepository.findFirstByUsername(
                        SecurityContextHolder.getContext().getAuthentication().getName()).get().getId())
                .stream()
                .map(expense -> new ExpenseDTO(
                        expense.getId(),
                        expense.getDescription(),
                        expense.getValue(),
                        new CategoryDTO(expense.getCategory().getId(), expense.getCategory().getName()))
                )
                .collect(Collectors.toList());
    }

    public ExpenseDTO updateExpense(long id, ExpenseEditDTO body) {
        Expense expense = findExpenseByIdAndUser(id);
        expense.setDescription(body.getDescription());
        expense.setCategory(categoryService.getById(body.getCategory()));
        expense.setValue(body.getValue());
        expense.setUser(userRepository.findFirstByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).get());
        Expense savedExpense = expenseRepository.save(expense);
        return new ExpenseDTO(
                savedExpense.getId(),
                savedExpense.getDescription(),
                savedExpense.getValue(),
                new CategoryDTO(savedExpense.getCategory().getId(), savedExpense.getCategory().getName())
        );
    }

    public void removeExpense(long id) {
        Expense expense = findExpenseByIdAndUser(id);
        expenseRepository.delete(expense);
    }

    private Expense findExpenseByIdAndUser(long id) {
        return this.expenseRepository.findByIdAndUser(
                id,
                userRepository.findFirstByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).get().getId()
        ).orElseThrow(() -> new RuntimeException("Expense not found"));
    }
}
