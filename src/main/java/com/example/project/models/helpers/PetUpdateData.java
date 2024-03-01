package com.example.project.models.helpers;

import com.example.project.models.Sex;

import java.time.Instant;

public record PetUpdateData(String name, Instant birth, Sex sex) {
}
