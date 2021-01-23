package com.expenses.feature.expense;

import com.expenses.feature.category.CategoryDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseDTO {
    private long id;
    private String description;
    private double value;
    private CategoryDTO category;
}

