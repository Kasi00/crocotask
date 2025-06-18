package com.example.crocotask.repository;

import com.example.crocotask.entity.NotificationStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NotificationStatusRepository extends CrudRepository<NotificationStatus, Long> {
    List<NotificationStatus> findByCustomerId(Long customerId);

    @Query("SELECT s.status AS status, COUNT(s) AS count FROM NotificationStatus s GROUP BY s.status")
    List<Object[]> countStatusGrouped();

    Long countByStatus(String status);


    @Query("SELECT s.status, COUNT(s) FROM NotificationStatus s GROUP BY s.status")
    List<Object[]> countByStatus();

    @Query("SELECT s.channel, COUNT(s) FROM NotificationStatus s GROUP BY s.channel")
    List<Object[]> countByChannel();
}
