package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.customer.Customer;
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
}
