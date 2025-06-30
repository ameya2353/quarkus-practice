package com.practice.quarkus.user.infrastructure.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.quarkus.user.domain.model.Pet;
import com.practice.quarkus.user.domain.ports.incoming.FetchOwnedPetsPort;
import com.practice.quarkus.user.domain.ports.outgoing.AddPetToTheOwner;
import com.practice.quarkus.user.infrastructure.rest.api.PetServiceApiClient;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class UserDomainRepository implements FetchOwnedPetsPort, AddPetToTheOwner {

    private final PetServiceApiClient petServiceApi;
    private ObjectMapper mapper = new ObjectMapper();


    @Override
    public List<Pet> fetchOwnedPets(String userId) {
        return petServiceApi.getAllOwnedPets(userId)
                .parallelStream()
                .map(this::mapJsonStringToPet)
                .toList();
    }

    @Override
    public void addPet(String userId, String petType, String breed) {
        petServiceApi.addPetForUser(userId, petType, breed);
    }

    private Pet mapJsonStringToPet(String jsonString) {
        try {
            return mapper.readValue(jsonString, Pet.class);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
