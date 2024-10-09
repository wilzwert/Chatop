package com.openclassrooms.chatop.repository;

import com.openclassrooms.chatop.model.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Wilhelm Zwertvaegher
 */
@Repository
public interface RentalRepository extends JpaRepository<Rental, Integer> {
}
