package com.practice.quarkus.user.domain.model;

import com.practice.quarkus.user.domain.ports.incoming.FetchOwnedPetsPort;
import com.practice.quarkus.user.domain.ports.outgoing.AddPetToTheOwner;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class User {

    private final String userId;
    private final FetchOwnedPetsPort fetchOwnedPetsPort;

    private final AddPetToTheOwner addPetToTheOwner;



    public List<Pet> getAllOwnedPets(){
        return fetchOwnedPetsPort.fetchOwnedPets(userId);
    }

    public void addPet(String type , String breed){
        addPetToTheOwner.addPet(userId,type , breed);
    }





}
