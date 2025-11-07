package com.optimuminfosystem.pfm.budget_service.service;

import com.optimuminfosystem.pfm.budget_service.model.Budget;
import com.optimuminfosystem.pfm.budget_service.repository.BudgetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BudgetServiceTest {

    private BudgetRepository budgetRepository;
    private BudgetService budgetService;

    private Budget budget;

    @BeforeEach
    void setUp() {
        budgetRepository = mock(BudgetRepository.class);
        budgetService = new BudgetService(budgetRepository);

        budget = new Budget();
        budget.setId(1L);
        budget.setUserId(1L);
        budget.setCategory("Groceries");
        budget.setAmount(1000.0);
    }

    @Test
    void testCreateBudget() {
        when(budgetRepository.save(any(Budget.class))).thenReturn(budget);

        Budget saved = budgetService.createBudget(budget);
        assertNotNull(saved);
        assertEquals(1L, saved.getId());
        assertEquals("Groceries", saved.getCategory());
        assertEquals(1000.0, saved.getAmount());
    }

    @Test
    void testGetBudgetsByUserId() {
        when(budgetRepository.findByUserId(1L)).thenReturn(Arrays.asList(budget));

        List<Budget> budgets = budgetService.getBudgetsByUserId(1L);
        assertEquals(1, budgets.size());
        assertEquals("Groceries", budgets.get(0).getCategory());
    }

    @Test
    void testGetBudgetByUserAndCategory_Found() {
        when(budgetRepository.findByUserIdAndCategory(1L, "Groceries")).thenReturn(Optional.of(budget));

        Optional<Budget> result = budgetService.getBudgetByUserAndCategory(1L, "Groceries");
        assertTrue(result.isPresent());
        assertEquals("Groceries", result.get().getCategory());
    }

    @Test
    void testGetBudgetByUserAndCategory_NotFound() {
        when(budgetRepository.findByUserIdAndCategory(1L, "Travel")).thenReturn(Optional.empty());

        Optional<Budget> result = budgetService.getBudgetByUserAndCategory(1L, "Travel");
        assertFalse(result.isPresent());
    }

    @Test
    void testUpdateBudget() {
        Budget updatedBudget = new Budget();
        updatedBudget.setId(1L);
        updatedBudget.setUserId(1L);
        updatedBudget.setCategory("Groceries");
        updatedBudget.setAmount(1500.0);

        when(budgetRepository.save(updatedBudget)).thenReturn(updatedBudget);

        Budget result = budgetService.updateBudget(updatedBudget);
        assertEquals(1500.0, result.getAmount());
        assertEquals("Groceries", result.getCategory());
    }
}
