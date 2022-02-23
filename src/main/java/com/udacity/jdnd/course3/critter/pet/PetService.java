package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.customer.Customer;
import com.udacity.jdnd.course3.critter.user.customer.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class PetService {
    @Autowired
    private PetRepository petRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public PetService(PetRepository petRepository, CustomerRepository customerRepository) {
        this.petRepository = petRepository;
        this.customerRepository = customerRepository;
    }

    public Pet create(Pet pet) {
        return petRepository.save(pet);
    }

    public List<Pet> findAll() {
        return (List<Pet>) petRepository.findAll();
    }

    public List<Pet> findByOwner(Customer customer) {
        return petRepository.findAllByCustomerEquals(customer);
    }

    public Pet find(Long id) {
        Optional<Pet> optionalPet = petRepository.findById(id);
        return optionalPet.orElseThrow(UnsupportedOperationException::new);
    }

    public Pet savePet(Pet pet) {
        return petRepository.save(pet);
    }

    public List<Pet> getAllPetsByOwnerId(long ownerId) {
        Optional<Customer> customer = customerRepository.findById(ownerId);
        if(customer.isPresent()) {
            List<Pet> pets = customer.get().getPets();
            return pets;
        } else {
            return null;
        }
    }
    public Pet getPetById(Long petId) {

        Optional<Pet> pet = petRepository.findById(petId);

        if (pet.isPresent()) {
            return pet.get();
        } else {
            return null;
        }
    }
    public List<Pet> getAllPetsByIds(List<Long> petIds) {
        return petRepository.findAllById(petIds);
    }
}
