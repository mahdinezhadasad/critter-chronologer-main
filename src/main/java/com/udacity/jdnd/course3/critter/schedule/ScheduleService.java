package com.udacity.jdnd.course3.critter.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    public Schedule create(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    public List<Schedule> findByEmployee(Long employeeId) {
        return scheduleRepository.findByEmployees_Id(employeeId);
    }

    public List<Schedule> findByPet(Long petId) {
        return scheduleRepository.findByPets_Id(petId);
    }

    public List<Schedule> findByCustomer(Long customerId) {
        return scheduleRepository.findByPets_CustomerId(customerId);
    }

    public Schedule find(Long id) {
        return scheduleRepository.findById(id).
                orElseThrow(UnsupportedOperationException::new);
    }

    public List<Schedule> findAll() {
        return (List<Schedule>) scheduleRepository.findAll();
    }
}
