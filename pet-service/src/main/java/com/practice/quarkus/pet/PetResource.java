package com.practice.quarkus.pet;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import java.util.List;

@Path("/pet-service")
public class PetResource {

    @Inject
    PetRepository repository;

    @GET
    public List<Pet> getPets(@QueryParam("userId") String userId) {
        return repository.findByOwner(userId);
    }

    @POST
    @Path("/add-pet")
    public boolean addPet(@QueryParam("userId") String userId,
                          @QueryParam("petType") String petType,
                          @QueryParam("petBreed") String petBreed) {
        repository.addPet(userId, petType, petBreed);
        return true;
    }
}
