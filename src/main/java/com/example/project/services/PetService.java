package com.example.project.services;

import com.example.project.exceptions.UnAuthorizedException;
import com.example.project.models.Pet;
import com.example.project.models.Sex;
import com.example.project.models.User;
import com.example.project.models.helpers.PetUpdateData;
import com.example.project.repositories.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PetService {
    private final PetRepository petRepository;
    private final UserService userService;

    public Pet addPet(String name, Instant birth, Sex sex, String userId) {
        User user = userService.findById(userId).orElse(null);
        return petRepository.save(new Pet(name, birth, sex, user));
    }

    public Pet addPet(Pet pet) {
        return petRepository.save(pet);
    }

    public Collection<Pet> getOwnedPets(String ownerId) {
        return petRepository.findAllByUser_Id(ownerId);
    }

    public void deletePet(String invokerId, String petId) throws UnAuthorizedException {
        Optional<Pet> pet = petRepository.findById(petId);

        if (pet.isEmpty()) {
            return;
        }

        if (!pet.get().getUser().getId().equals(invokerId)) {
            throw new UnAuthorizedException("Zwierzak nie nalezy do Ciebie");
        }

        petRepository.deleteById(petId);
    }

    public Pet updatePet(String invokerId, String petId, PetUpdateData updateData) throws UnAuthorizedException {
        Optional<Pet> optionalPet = petRepository.findById(petId);

        if (optionalPet.isEmpty()) {
            return null;
        }

        Pet pet = optionalPet.get();

        if (!invokerId.equals(pet.getUser().getId())) {
            throw new UnAuthorizedException("Zwierzak nie nalezy do Ciebie");
        }

        BeanUtils.copyProperties(updateData, pet);

        return petRepository.save(pet);
    }
}
