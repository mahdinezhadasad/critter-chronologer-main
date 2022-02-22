package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetService;
import com.udacity.jdnd.course3.critter.user.customer.Customer;
import com.udacity.jdnd.course3.critter.user.customer.CustomerDTO;
import com.udacity.jdnd.course3.critter.user.customer.CustomerService;
import com.udacity.jdnd.course3.critter.user.employee.Employee;
import com.udacity.jdnd.course3.critter.user.employee.EmployeeDTO;
import com.udacity.jdnd.course3.critter.user.employee.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.user.employee.EmployeeService;
import com.udacity.jdnd.course3.critter.util.ConvertEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private PetService petService;

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO) {
      Customer customer = customerService.create(convertDtoToEntity(customerDTO));
      CustomerDTO cDto =  convertEntityToDto(customer);
//      cDto.setPetIds(customer.getPets().stream().map(Pet::getId)
//              .collect(Collectors.toList()));
      return cDto;
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        return customerService.findAll().stream()
                .map(customer -> {
                    CustomerDTO cd = convertEntityToDto(customer);
                    cd.setPetIds(getPetIds(customer));
                    return cd;
                })
                .collect(Collectors.toList());
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
       Customer customer = petService.find(petId).getCustomer();
//        Customer customer = customerService.findByPet(petId);
       CustomerDTO cd =  convertEntityToDto(customer);
       cd.setPetIds(getPetIds(customer));
       return cd;
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = employeeService.create(convertDtoToEntity(employeeDTO));
        return convertEntityToDto(employee);
    }

    @GetMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        return convertEntityToDto(employeeService.findById(employeeId));
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        employeeService.updateAvailability(daysAvailable, employeeId);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        return employeeService.findByServiceAndTime(employeeDTO.getSkills(),
                employeeDTO.getDate())
                .stream().map(employee -> convertEntityToDto(employee)).collect(Collectors.toList());
    }

    private List<Long> getPetIds(Customer customer) {
        List<Pet> pets = customer.getPets();
        if(pets != null)  {
            return customer.getPets().stream()
                    .map(Pet::getId).collect(Collectors.toList());
        } else return null;

    }

    private static EmployeeDTO convertEntityToDto(Employee employee) {
        return new ConvertEntity<Employee, EmployeeDTO>()
                .toDto(employee, new EmployeeDTO());
    }

    private static CustomerDTO convertEntityToDto(Customer customer) {
        return new ConvertEntity<Customer, CustomerDTO>()
                .toDto(customer, new CustomerDTO());
    }

    private static Employee convertDtoToEntity(EmployeeDTO employeeDTO) {
        return new ConvertEntity<Employee, EmployeeDTO>()
                .toEntity(new Employee(), employeeDTO);
    }

    private static Customer convertDtoToEntity(CustomerDTO customerDTO) {
       return new ConvertEntity<Customer, CustomerDTO>()
               .toEntity(new Customer(), customerDTO);
    }

}
