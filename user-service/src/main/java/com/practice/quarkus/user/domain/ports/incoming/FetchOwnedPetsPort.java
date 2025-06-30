package com.practice.quarkus.user.domain.ports.incoming;

import com.practice.quarkus.user.domain.model.Pet;

import java.util.List;

public interface FetchOwnedPetsPort {

    List<Pet> fetchOwnedPets(String userId);

}
