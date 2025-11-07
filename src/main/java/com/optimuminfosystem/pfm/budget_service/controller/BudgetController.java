package com.optimuminfosystem.pfm.budget_service.controller;

import com.optimuminfosystem.pfm.budget_service.exception.BudgetServiceException;
import com.optimuminfosystem.pfm.budget_service.model.Budget;
import com.optimuminfosystem.pfm.budget_service.service.BudgetService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/budgets")
public class BudgetController {

    private final BudgetService budgetService;
    private static final Logger log = LoggerFactory.getLogger(BudgetController.class);

    public BudgetController(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    /**
     * Creates a new budget for a user.
     *
     * @param budget Budget object containing userId, category, and amount
     * @return The saved Budget object
     */
    @PostMapping
    public Budget createBudget(@RequestBody Budget budget) {
        log.info("Creating budget: {}", budget);
        Budget saved = budgetService.createBudget(budget);
        log.info("Budget created successfully: {}", saved);
        return saved;
    }

    /**
     * Retrieves all budgets for a specific user.
     *
     * @param userId User ID
     * @return List of Budget objects
     */
    @GetMapping("/{userId}")
    public List<Budget> getBudgets(@PathVariable Long userId) {
        log.info("Fetching all budgets for userId: {}", userId);
        return budgetService.getBudgetsByUserId(userId);
    }

    /**
     * Retrieves a budget for a specific user and category.
     *
     * @param userId   User ID
     * @param category Budget category
     * @return Budget object if found
     * @throws BudgetServiceException if no budget is found
     */
    @GetMapping("/{userId}/{category}")
    public Budget getBudgetByCategory(@PathVariable Long userId, @PathVariable String category) {
        log.info("Fetching budget for userId: {} and category: {}", userId, category);
        return budgetService.getBudgetByUserAndCategory(userId, category)
                .orElseThrow(() -> new BudgetServiceException("NOT FOUND", "Budget not found", HttpStatus.NO_CONTENT));
    }

    /**
     * Updates an existing budget's amount.
     *
     * @param id            Budget ID
     * @param budgetRequest Budget object containing updated amount
     * @return Updated Budget object
     * @throws BudgetServiceException if budget not found
     */
    @PutMapping("/{id}")
    public Budget updateBudget(@PathVariable Long id, @RequestBody Budget budgetRequest) {
        log.info("Updating budget with id: {}", id);
        Budget existingBudget = budgetService.getBudgetByUserAndCategory(
                        budgetRequest.getUserId(), budgetRequest.getCategory())
                .orElseThrow(() -> new BudgetServiceException("NOT FOUND", "Budget not found", HttpStatus.NO_CONTENT));

        existingBudget.setAmount(budgetRequest.getAmount());
        Budget updatedBudget = budgetService.updateBudget(existingBudget);
        log.info("Budget updated successfully: {}", updatedBudget);
        return updatedBudget;
    }
}
