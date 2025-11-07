package com.optimuminfosystem.pfm.budget_service.repository;

import com.optimuminfosystem.pfm.budget_service.model.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {
    List<Budget> findByUserId(Long userId);

    Optional<Budget> findByUserIdAndCategory(Long userId, String category);

}
