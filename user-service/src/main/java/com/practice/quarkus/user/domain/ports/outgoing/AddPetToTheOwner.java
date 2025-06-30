package com.practice.quarkus.user.domain.ports.outgoing;

public interface AddPetToTheOwner {
    void addPet(String userId , String petType , String breed);
}
