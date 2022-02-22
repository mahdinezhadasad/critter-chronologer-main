package com.udacity.jdnd.course3.critter.user.employee;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {
    List<Employee> findAllBySkillsInAndDaysAvailable(Set<EmployeeSkill> skills, DayOfWeek day);
}
