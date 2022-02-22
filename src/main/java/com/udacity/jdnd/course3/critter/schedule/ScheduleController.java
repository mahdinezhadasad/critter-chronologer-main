package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetService;
import com.udacity.jdnd.course3.critter.user.employee.Employee;
import com.udacity.jdnd.course3.critter.user.employee.EmployeeService;
import com.udacity.jdnd.course3.critter.util.ConvertEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private PetService petService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = convertDtoToEntity(scheduleDTO);
        List<Employee> employees = getEntityFromId(scheduleDTO.getEmployeeIds(), new Employee());
        List<Pet> pets = getEntityFromId(scheduleDTO.getPetIds(), new Pet());
        schedule.setPets(pets);
        schedule.setEmployees(employees);

        return getScheduleDTO(scheduleService.create(schedule));
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        return scheduleService.findAll().stream()
                .map(schedule -> getScheduleDTO(schedule))
                .collect(Collectors.toList());
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        return scheduleService.findByPet(petId).stream()
                .map(schedule -> getScheduleDTO(schedule))
                .collect(Collectors.toList());
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        return scheduleService.findByEmployee(employeeId).stream()
                .map(schedule -> getScheduleDTO(schedule))
                .collect(Collectors.toList());
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        return scheduleService.findByCustomer(customerId).stream()
                .map(schedule -> getScheduleDTO(schedule))
                .collect(Collectors.toList());
    }

    private <T> List<T> getEntityFromId(List<Long> ids, T entity) {
        List<T> entities = new ArrayList<>();
        ids.forEach(id -> {
            final boolean b = entity.getClass() == Employee.class
                    ? entities.add((T) employeeService.findById(id))
                    : entities.add((T) petService.find(id));
        });
        return entities;
    }

    private ScheduleDTO getScheduleDTO(Schedule schedule) {
        ScheduleDTO sd = convertEntityToDto(schedule);
        sd.setEmployeeIds(schedule.getEmployees().stream()
                .map(e -> e.getId()).collect(Collectors.toList()));
        sd.setPetIds(schedule.getPets().stream()
                .map(p -> p.getId()).collect(Collectors.toList()));
        return sd;
    }

    private static ScheduleDTO convertEntityToDto(Schedule schedule) {
        return new ConvertEntity<Schedule, ScheduleDTO>()
                .toDto(schedule, new ScheduleDTO());
    }

    private static Schedule convertDtoToEntity(ScheduleDTO scheduleDTO) {
        return new ConvertEntity<Schedule, ScheduleDTO>()
                .toEntity(new Schedule(), scheduleDTO);
    }
}
