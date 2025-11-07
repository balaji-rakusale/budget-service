package com.optimuminfosystem.pfm.budget_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Budget {
    @Id
    @GeneratedValue
    private Long id;
    private Long userId;
    private String category;
    private Double amount;

    public Budget() {
    }

    public Budget(Long id, Long userId, String category, Double amount) {
        this.id = id;
        this.userId = userId;
        this.category = category;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Budget{" +
                "id=" + id +
                ", userId=" + userId +
                ", category='" + category + '\'' +
                ", amount=" + amount +
                '}';
    }

    public static BudgetBuilder builder() {
        return new BudgetBuilder();
    }

    public static class BudgetBuilder {
        private Long id;
        private Long userId;
        private String category;
        private Double amount;

        public BudgetBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public BudgetBuilder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public BudgetBuilder category(String category) {
            this.category = category;
            return this;
        }

        public BudgetBuilder amount(Double amount) {
            this.amount = amount;
            return this;
        }

        public Budget build() {
            return new Budget(id, userId, category, amount);
        }
    }
}
