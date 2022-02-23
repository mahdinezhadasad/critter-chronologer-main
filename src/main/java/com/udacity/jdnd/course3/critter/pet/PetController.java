package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.customer.Customer;
import com.udacity.jdnd.course3.critter.user.customer.CustomerDTO;
import com.udacity.jdnd.course3.critter.user.customer.CustomerService;
import com.udacity.jdnd.course3.critter.util.ConvertEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {
    @Autowired
    private PetService petService;

    @Autowired
    private CustomerService customerService;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet pet = convertPetDTOToPet(petDTO);
        Pet petSaved = petService.savePet(pet);

        if(petDTO.getOwnerId() != 0) {
            Customer customer = customerService.getCustomerById(petDTO.getOwnerId());
            customer.getPets().add(petSaved);
            customerService.save(customer);
        }

        petDTO.setId(petSaved.getId());
        return petDTO;
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

        Customer customer = customerService.getCustomerById(ownerId);
        List<Pet> pets = customer.getPets();
        List<PetDTO> petDTOs = new ArrayList<>();
        for (Pet pet: pets) {
            petDTOs.add(convertPettoDTO(pet));
        }
        return petDTOs;

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


    private Pet convertPetDTOToPet(PetDTO petDTO) {
        Pet pet = new Pet();
        pet.setType(petDTO.getType());
        pet.setName(petDTO.getName());
        pet.setBirthDate(petDTO.getBirthDate());
        pet.setNotes(petDTO.getNotes());
        if(petDTO.getOwnerId()!=0) {
            Customer customer = customerService.getCustomerById(petDTO.getOwnerId());
            pet.setCustomer(customer);
        }
        return pet;
    }

    public PetDTO convertPettoDTO(Pet pet) {
        PetDTO petDTO = new PetDTO();
        petDTO.setId(pet.getId());
        petDTO.setType(pet.getType());
        petDTO.setName(pet.getName());
        Long ownerId = pet.getCustomer().getId();
        petDTO.setOwnerId(ownerId);
        petDTO.setBirthDate(pet.getBirthDate());
        petDTO.setNotes(pet.getNotes());
        return petDTO;
    }

}
