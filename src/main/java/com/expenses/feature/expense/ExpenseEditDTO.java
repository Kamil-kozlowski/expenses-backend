package com.expenses.feature.expense;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseEditDTO {
    private String description;
    private double value;
    private long category;
}
