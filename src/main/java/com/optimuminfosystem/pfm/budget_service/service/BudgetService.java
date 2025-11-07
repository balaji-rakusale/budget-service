package com.optimuminfosystem.pfm.budget_service.service;

import com.optimuminfosystem.pfm.budget_service.model.Budget;
import com.optimuminfosystem.pfm.budget_service.repository.BudgetRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BudgetService {
    private final BudgetRepository budgetRepository;

    public BudgetService(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }

    public Budget createBudget(Budget budget) {
        return budgetRepository.save(budget);
    }

    public List<Budget> getBudgetsByUserId(Long userId) {
        return budgetRepository.findByUserId(userId);
    }

    public Optional<Budget> getBudgetByUserAndCategory(Long userId, String category) {
        return budgetRepository.findByUserIdAndCategory(userId, category);
    }

    public Budget updateBudget(Budget budget) {
        return budgetRepository.save(budget);
    }
}

