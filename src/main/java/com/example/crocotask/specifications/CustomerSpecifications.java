package com.example.crocotask.specifications;

import com.example.crocotask.dto.CustomerFilterRequest;
import com.example.crocotask.entity.Customer;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class CustomerSpecifications {
    public static Specification<Customer> filter(CustomerFilterRequest request) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (request.getName() != null) {
                predicates.add(cb.like(cb.lower(root.get("fullName")), "%" + request.getName().toLowerCase() + "%"));
            }

            if (request.getEmail() != null) {
                predicates.add(cb.like(cb.lower(root.get("email")), "%" + request.getEmail().toLowerCase() + "%"));
            }


            if (request.getEmailEnabled() != null) {
                predicates.add(cb.equal(root.get("preferences").get("emailEnabled"), request.getEmailEnabled()));
            }

            if (request.getSmsEnabled() != null) {
                predicates.add(cb.equal(root.get("preferences").get("smsEnabled"), request.getSmsEnabled()));
            }

            if (request.getPromotionalEnabled() != null) {
                predicates.add(cb.equal(root.get("preferences").get("promotionalEnabled"), request.getPromotionalEnabled()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
