package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.customer.Customer;
import com.udacity.jdnd.course3.critter.user.customer.CustomerDTO;
import com.udacity.jdnd.course3.critter.user.customer.CustomerService;
import com.udacity.jdnd.course3.critter.util.ConvertEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pets")
public class PetController {
    @Autowired
    private PetService petService;

    @Autowired
    private CustomerService customerService;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet pet = convertDtoToEntity(petDTO);
        try {
            Customer owner = customerService.find(petDTO.getOwnerId());
            pet.setCustomer(owner);
            Pet newPet = petService.create(pet);

            PetDTO pd = convertEntityToDto(newPet);
            pd.setOwnerId(owner.getId());

            try {
                customerService.addPet(newPet, owner.getId());
            } catch (Exception e) {
                System.out.println("Could not add pet to customer"); //todo Exception handling
            }
            return pd;
        }catch (UnsupportedOperationException e) {
            return null;
        }
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        Pet pet = petService.find(petId);
        PetDTO petDTO = convertEntityToDto(pet);
        petDTO.setOwnerId(pet.getCustomer().getId());
        return petDTO;
    }

    @GetMapping
    public List<PetDTO> getPets(){
       return petService.findAll().stream()
                .map(pet -> getPetDto(pet))
                .collect(Collectors.toList());
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        Customer customer;
        try {
            customer = customerService.find(ownerId);
        } catch (UnsupportedOperationException e) {
            return null;
        }

        return petService.findByOwner(customer).stream()
                .map(pet -> getPetDto(pet))
                .collect(Collectors.toList());
    }

    private PetDTO getPetDto(Pet pet) {
         PetDTO pd = convertEntityToDto(pet);
        pd.setOwnerId(pet.getCustomer().getId());
        return pd;
    }

    private static PetDTO convertEntityToDto(Pet pet) {
        return new ConvertEntity<Pet, PetDTO>()
                .toDto(pet, new PetDTO());
    }

    private static Pet convertDtoToEntity(PetDTO petDTO) {
        return new ConvertEntity<Pet, PetDTO>()
                .toEntity(new Pet(), petDTO);
    }

}
