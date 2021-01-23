package com.expenses.feature.expense;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expense")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    @PostMapping
    public ExpenseDTO createExpense(@RequestBody ExpenseEditDTO body) {
        return expenseService.createExpense(body);
    }

    @GetMapping
    public List<ExpenseDTO> getAllExpenses() {
        return expenseService.getAllExpenses();
    }

    @PutMapping(path = "/{id}")
    public ExpenseDTO updateExpense(@PathVariable long id, @RequestBody ExpenseEditDTO body) {
        return expenseService.updateExpense(id, body);
    }

    @DeleteMapping(path = "/{id}")
    public void removeExpense(@PathVariable long id) {
        expenseService.removeExpense(id);
    }
}
