package com.expenses.feature.expense;

import com.expenses.feature.category.Category;
import com.expenses.feature.user.User;
import com.expenses.shared.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Expense extends BaseEntity {
    private String description;
    private double value;
    @ManyToOne
    private User user;
    @ManyToOne
    private Category category;
}
