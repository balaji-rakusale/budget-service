package com.optimuminfosystem.pfm.budget_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.optimuminfosystem.pfm.budget_service.model.Budget;
import com.optimuminfosystem.pfm.budget_service.service.BudgetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BudgetController.class)
class BudgetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BudgetService budgetService;

    @Autowired
    private ObjectMapper objectMapper;

    private Budget budget;

    @BeforeEach
    void setUp() {
        budget = new Budget();
        budget.setId(1L);
        budget.setUserId(1L);
        budget.setCategory("Groceries");
        budget.setAmount(1000.0);
    }

    @Test
    void testCreateBudget() throws Exception {
        when(budgetService.createBudget(any(Budget.class))).thenReturn(budget);

        mockMvc.perform(post("/budgets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(budget)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.category").value("Groceries"))
                .andExpect(jsonPath("$.amount").value(1000.0));
    }

    @Test
    void testGetBudgets() throws Exception {
        when(budgetService.getBudgetsByUserId(1L)).thenReturn(Arrays.asList(budget));

        mockMvc.perform(get("/budgets/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].category").value("Groceries"));
    }

    @Test
    void testGetBudgetByCategory_Found() throws Exception {
        when(budgetService.getBudgetByUserAndCategory(1L, "Groceries"))
                .thenReturn(Optional.of(budget));

        mockMvc.perform(get("/budgets/1/Groceries"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.category").value("Groceries"));
    }

    @Test
    void testGetBudgetByCategory_NotFound() throws Exception {
        when(budgetService.getBudgetByUserAndCategory(1L, "Travel"))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/budgets/1/Travel"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testUpdateBudget() throws Exception {
        Budget updatedBudget = new Budget();
        updatedBudget.setId(1L);
        updatedBudget.setUserId(1L);
        updatedBudget.setCategory("Groceries");
        updatedBudget.setAmount(1500.0);

        when(budgetService.getBudgetByUserAndCategory(1L, "Groceries"))
                .thenReturn(Optional.of(budget));
        when(budgetService.updateBudget(any(Budget.class))).thenReturn(updatedBudget);

        mockMvc.perform(put("/budgets/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedBudget)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(1500.0));
    }
}
