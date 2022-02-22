package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.customer.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PetRepository extends CrudRepository<Pet, Long> {
//    @Query("select p from pet p")
    List<Pet> findAllByCustomerEquals(Customer customer);
}
