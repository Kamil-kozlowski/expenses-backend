package com.expenses.feature.user;

import com.expenses.shared.model.BaseEntity;
import lombok.*;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {
    private String username;
    private String password;
}
