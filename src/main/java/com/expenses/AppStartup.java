package com.expenses;

import com.expenses.core.security.AdminProperties;
import com.expenses.feature.category.Category;
import com.expenses.feature.category.CategoryRepository;
import com.expenses.feature.expense.Expense;
import com.expenses.feature.expense.ExpenseRepository;
import com.expenses.feature.user.User;
import com.expenses.feature.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AppStartup implements ApplicationListener<ApplicationReadyEvent> {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ExpenseRepository expenseRepository;
    private final AdminProperties adminProperties;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        if (!userRepository.findFirstByUsername(adminProperties.getUsername()).isPresent()) {
            //dodawanie uzytkownika 'admin'
            final String passwordHash = passwordEncoder.encode(adminProperties.getPassword());
            final User admin = new User(adminProperties.getUsername(), passwordHash);
            userRepository.save(admin);

            //dodawanie kategorii
            Category rozrywka = categoryRepository.save(new Category("rozrywka", admin));
            Category dom = categoryRepository.save(new Category("dom", admin));
            Category dojazd = categoryRepository.save(new Category("dojazd do pracy", admin));

            //dodawanie wydatkow
            expenseRepository.save(new Expense("narty narty narty narty narty narty narty narty narty narty narty narty", 250, admin, rozrywka));
            expenseRepository.save(new Expense("kino", 30, admin, rozrywka));
            expenseRepository.save(new Expense("zakupy", 40, admin, dom));
            expenseRepository.save(new Expense("zakupy", 45.50, admin, dom));
            expenseRepository.save(new Expense("bilet", 10, admin, dojazd));
            expenseRepository.save(new Expense("bilet", 10, admin, dojazd));
        }

        //uzytkownik 'kamil'
        if (!userRepository.findFirstByUsername("kamil").isPresent()) {
            final String passwordHash = passwordEncoder.encode("kamil");
            final User kamil = new User("kamil", passwordHash);
            userRepository.save(kamil);
            Category dzieci = categoryRepository.save(new Category("dzieci", kamil));
            Category hobby = categoryRepository.save(new Category("hobby", kamil));
            expenseRepository.save(new Expense("kino", 24.30, kamil, hobby));
            expenseRepository.save(new Expense("kieszonkowe", 100, kamil, dzieci));
        }

        //uzytkownik 'karolina'
        if (!userRepository.findFirstByUsername("karolina").isPresent()) {
            final String passwordHash = passwordEncoder.encode("karolina");
            final User karolina = new User("karolina", passwordHash);
            userRepository.save(karolina);
            Category dzieci = categoryRepository.save(new Category("dzieci", karolina));
            expenseRepository.save(new Expense("kieszonkowe", 100, karolina, dzieci));
        }

    }
}
