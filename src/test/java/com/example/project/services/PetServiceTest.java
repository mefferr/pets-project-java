package com.example.project.services;

import com.example.project.exceptions.UnAuthorizedException;
import com.example.project.models.Pet;
import com.example.project.models.Sex;
import com.example.project.models.User;
import com.example.project.models.helpers.PetUpdateData;
import com.example.project.repositories.PetRepository;
import com.example.project.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.Instant;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PetServiceTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PetService petService;
    @Autowired
    private PetRepository petRepository;

    @BeforeEach
    void init() {
        petRepository.deleteAll();
        userRepository.deleteAll();

        userRepository.save(new User("test@test.test", "test"));
        userRepository.save(new User("test@test.test", "test"));
    }

    @Test
    void addPet() {
        List<User> users = userRepository.findAll();
        Pet pet = new Pet("Puppy", Instant.now(), Sex.MALE, users.get(0));
        Pet saved = petService.addPet(pet);

        assertEquals(pet.getName(), saved.getName());
    }

    @Test
    void getOwnedPets() {
        List<User> users = userRepository.findAll();
        Pet pet = new Pet("Puppy", Instant.now(), Sex.MALE, users.get(0));

        petService.addPet(pet);

        Collection<Pet> ownedPets = petService.getOwnedPets(users.get(0).getId());

        assertEquals(1, ownedPets.size());
    }

    @Test
    void deletePet() throws UnAuthorizedException {
        List<User> users = userRepository.findAll();
        String ownerId = users.get(0).getId();

        Pet pet = new Pet("Puppy", Instant.now(), Sex.MALE, users.get(0));

        petService.addPet(pet);

        assertEquals(1, petService.getOwnedPets(ownerId).size());

        petService.deletePet(ownerId, pet.getId());

        assertEquals(0, petService.getOwnedPets(ownerId).size());
    }

    @Test
    void updatePet() throws UnAuthorizedException {
        List<User> users = userRepository.findAll();
        String ownerId = users.get(0).getId();

        Pet pet = new Pet("Puppy", Instant.now(), Sex.MALE, users.get(0));

        petService.addPet(pet);

        Pet fluffy = petService.updatePet(ownerId, pet.getId(), new PetUpdateData("Fluffy", Instant.now(), Sex.MALE));

        assertSame("Fluffy", fluffy.getName());
    }

    @Test
    void updatePetByNotOwner() {
        assertThrows(UnAuthorizedException.class, () -> {
            List<User> users = userRepository.findAll();
            Pet pet = new Pet("Puppy", Instant.now(), Sex.MALE, users.get(0));

            petService.addPet(pet);

            petService.updatePet("not-owner-id", pet.getId(), new PetUpdateData("Fluffy", Instant.now(), Sex.MALE));
        });
    }

    @Test
    void deletePetByNowOwner() {
        assertThrows(UnAuthorizedException.class, () -> {
            List<User> users = userRepository.findAll();
            String ownerId = users.get(0).getId();

            Pet pet = new Pet("Puppy", Instant.now(), Sex.MALE, users.get(0));

            petService.addPet(pet);

            assertEquals(1, petService.getOwnedPets(ownerId).size());

            petService.deletePet("not-owner-id", pet.getId());
        });
    }
}