package com.example.project.repositories;

import com.example.project.models.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface PetRepository extends JpaRepository<Pet, String> {
    Collection<Pet> findAllByUser_Id(String id);

}
