package com.udacity.jdnd.course3.critter.user.customer;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {

    @Query("SELECT c FROM Customer c LEFT JOIN Pet p WHERE p.id = :petId")
    Customer findByPetId(Long petId);
}
