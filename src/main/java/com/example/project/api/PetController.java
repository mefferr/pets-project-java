package com.example.project.api;

import com.example.project.exceptions.UnAuthorizedException;
import com.example.project.models.Message;
import com.example.project.models.Pet;
import com.example.project.models.Sex;
import com.example.project.models.helpers.PetUpdateData;
import com.example.project.services.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Collection;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/pets")
public class PetController {
    private final PetService petService;

    @GetMapping("/")
    @PreAuthorize("isAuthenticated()")
    public Collection<Pet> pets(Authentication authentication) {
        return petService.getOwnedPets((String) authentication.getPrincipal());
    }

    public record PetPayload(String name, Instant birth, Sex sex) {
    }

    @PostMapping("/")
    @PreAuthorize("isAuthenticated()")
    public Pet create(@RequestBody PetPayload petPayload, Authentication authentication) {
        return petService.addPet(petPayload.name, petPayload.birth, petPayload.sex, (String) authentication.getPrincipal());
    }

    @PatchMapping("/{id}/")
    @PreAuthorize("isAuthenticated()")
    public Pet update(@RequestBody PetUpdateData updateData, Authentication authentication, @PathVariable String id) throws UnAuthorizedException {
        return petService.updatePet((String) authentication.getPrincipal(), id, updateData);
    }

    @DeleteMapping("/{id}/")
    @PreAuthorize("isAuthenticated()")
    public Message delete(Authentication authentication, @PathVariable String id) throws UnAuthorizedException {
        petService.deletePet((String) authentication.getPrincipal(), id);

        return new Message("Success");
    }
}
