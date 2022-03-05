package com.udacity.jdnd.course3.critter.pet;
import com.udacity.jdnd.course3.critter.user.customer.Customer;
import com.udacity.jdnd.course3.critter.user.customer.CustomerDTO;
import com.udacity.jdnd.course3.critter.user.customer.CustomerService;
import com.udacity.jdnd.course3.critter.util.ConvertEntity;
import org.springframework.beans.BeanUtils;
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
        Pet savedPet = petService.savePet(pet);
        return convertPettoDTO(savedPet);
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
        BeanUtils.copyProperties(petDTO, pet, "ownerId");
        long customerId = petDTO.getOwnerId();
        Customer customer = customerService.getCustomerById(customerId);
        pet.setCustomer(customer);
        return pet;
    }
    public PetDTO convertPettoDTO(Pet pet) {
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO, "customer");
        petDTO.setOwnerId(pet.getCustomer().getId());
        return petDTO;
    }
}