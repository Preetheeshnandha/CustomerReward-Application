package com.customerReward.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.customerReward.application.entity.Transaction;

/**
 * Repository interface for {@link Transaction} entity.
 * 
 * <p>Extends {@link JpaRepository} to provide CRUD operations and
 * database interaction capabilities for Transaction entities.</p>
 * 
 * <p>This interface is automatically implemented by Spring Data JPA.</p>
 * 
 * @author Preetheeshnandha
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long>{

}
