package com.practice.quarkus.user.domain.service;

import com.practice.quarkus.user.domain.model.Pet;
import com.practice.quarkus.user.domain.model.User;
import com.practice.quarkus.user.domain.ports.incoming.FetchOwnedPetsPort;
import com.practice.quarkus.user.domain.ports.outgoing.AddPetToTheOwner;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public final class UserDomainService{

    private final FetchOwnedPetsPort fetchOwnedPetsPort;
    private final AddPetToTheOwner addPetToTheOwner;

    public void addPetToUser(String userId ,String petType , String petBreed){
        new User(userId,fetchOwnedPetsPort ,addPetToTheOwner).addPet(petType , petBreed);
    }

    public List<Pet> petsOwnedByUser(String  userId){
        return new User(userId , fetchOwnedPetsPort ,addPetToTheOwner).getAllOwnedPets();
    }




}
