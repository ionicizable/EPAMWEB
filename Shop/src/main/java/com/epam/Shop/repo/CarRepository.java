package com.epam.Shop.repo;

import com.epam.Shop.entities.Cars;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Cars, Integer> {

}
