package com.example.crocotask.repository;

import com.example.crocotask.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {
    boolean existsByEmail(String email);
    @Query("SELECT COUNT(c) FROM Customer c WHERE c.preferences.emailEnabled = true")
    Long countEmailOptIn();

    @Query("SELECT COUNT(c) FROM Customer c WHERE c.preferences.smsEnabled = true")
    Long countSmsOptIn();

    @Query("SELECT COUNT(c) FROM Customer c WHERE c.preferences.promotionalEnabled = true")
    Long countPromotionalOptIn();
}
