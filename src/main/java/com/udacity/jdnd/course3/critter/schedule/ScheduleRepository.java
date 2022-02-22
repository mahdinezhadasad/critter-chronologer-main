package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.user.employee.Employee;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends CrudRepository<Schedule, Long> {

    //    @Query("SELECT s FROM Schedule s LEFT JOIN Pet p WHERE p.id = :petId")
    List<Schedule> findByPets_Id(Long petId);

//    @Query("SELECT s FROM Schedule s LEFT JOIN Employee e WHERE e.id = :employeeId")
    List<Schedule> findByEmployees_Id(Long employeeId);


//    @Query("SELECT s FROM Schedule s LEFT JOIN Pet p WHERE p.customer.id = :customerId")
    List<Schedule> findByPets_CustomerId(Long customerId);
}
